package kg.ticode.testh.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.ticode.data.ApiResult
import kg.ticode.data.error_model.FieldResult
import kg.ticode.data.usecase.GetBookingDetailUseCase
import kg.ticode.data.usecase.GetHotelRoomsUseCase
import kg.ticode.data.usecase.GetHotelUseCase
import kg.ticode.data.usecase.InfoAboutBuyerUseCase
import kg.ticode.data.usecase.TouristUseCase
import kg.ticode.domain.network.dto.BookingDetail
import kg.ticode.domain.network.dto.Hotel
import kg.ticode.domain.network.dto.HotelNumbers
import kg.ticode.domain.network.dto.InfoAboutBuyer
import kg.ticode.domain.network.dto.Tourist
import kg.ticode.testh.state.hotel.BookingDetailState
import kg.ticode.testh.state.hotel.HotelRoomsState
import kg.ticode.testh.state.hotel.HotelState
import kg.ticode.testh.state.hotel.PayingState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelViewModel @Inject constructor(
    private val hotelUseCase: GetHotelUseCase,
    private val hotelRoomsUseCase: GetHotelRoomsUseCase,
    private val bookingDetailUseCase: GetBookingDetailUseCase,
    private val touristUseCase: TouristUseCase,
    private val infoAboutBuyerUseCase: InfoAboutBuyerUseCase,
) : ViewModel() {
    private val _hotelState = MutableLiveData(HotelState())
    val hotelState = _hotelState

    private val _hotelRoomsState = MutableLiveData(HotelRoomsState())
    val hotelRoomsState = _hotelRoomsState

    private val _bookingDetailState = MutableLiveData(BookingDetailState())
    val bookingDetailState = _bookingDetailState
    private val _buyerInfoState = MutableLiveData(PayingState())
    val buyerInfoState = _buyerInfoState

    private val _touristState = MutableLiveData(PayingState())
    val touristState = _touristState

    private val _hotelName = MutableLiveData("")

    fun getHotelName(): LiveData<String> {
        return _hotelName
    }

    init {
        getHotel()
        getHotelRooms()
        getBookingDetail()
    }

    fun setHotelName(name: String) {
        _hotelName.value = name

    }


     fun getBookingDetail() {
        viewModelScope.launch {
            bookingDetailUseCase.invoke().collectLatest { result: ApiResult<BookingDetail> ->
                when (result) {
                    is ApiResult.Error -> _bookingDetailState.value =
                        BookingDetailState(error = result.error)

                    is ApiResult.Loading -> _bookingDetailState.value =
                        BookingDetailState(isLoading = true)

                    is ApiResult.Success -> {
                        _bookingDetailState.value =
                            BookingDetailState(data = result.data, isSuccess = true)
                        _bookingDetailState.value =
                            BookingDetailState(data = result.data, isSuccess = false)
                    }
                }
            }
        }
    }

     fun getHotelRooms() {
        viewModelScope.launch {
            hotelRoomsUseCase.invoke().collectLatest { result: ApiResult<HotelNumbers> ->
                when (result) {
                    is ApiResult.Error -> _hotelRoomsState.value =
                        HotelRoomsState(error = result.error)

                    is ApiResult.Loading -> _hotelRoomsState.value =
                        HotelRoomsState(isLoading = true)

                    is ApiResult.Success -> _hotelRoomsState.value =
                        HotelRoomsState(data = result.data, isSuccess = true)
                }
            }
        }
    }

    fun getHotel() {
        viewModelScope.launch {
            hotelUseCase.invoke().collectLatest { result: ApiResult<Hotel> ->
                when (result) {
                    is ApiResult.Error -> _hotelState.value = HotelState(error = result.error)
                    is ApiResult.Loading -> _hotelState.value = HotelState(isLoading = true)
                    is ApiResult.Success -> _hotelState.value =
                        HotelState(data = result.data, isSuccess = true)
                }
            }
        }
    }

    private suspend fun usePhoneUC(phone: String): FieldResult =
        infoAboutBuyerUseCase.phoneUC(phone).first()


    private suspend fun useEmailUC(email: String): FieldResult =
        infoAboutBuyerUseCase.emailUC(email).first()


    fun pay(tourist: Tourist, infoAboutBuyer: InfoAboutBuyer) {
        viewModelScope.launch {
            try {
                _touristState.value = PayingState(
                    isLoading = true
                )
                val phoneDeferred = async { usePhoneUC(infoAboutBuyer.phoneNumber) }
                val emailDeferred = async { useEmailUC(infoAboutBuyer.email) }
                val touristDeferred = async { touristUseCase.invoke(tourist) }
                val phoneResult = phoneDeferred.await()
                if (phoneResult is FieldResult.IsSuccess) {
                    val emailResult = emailDeferred.await()
                    if (emailResult is FieldResult.IsSuccess) {
                        val touristResult = touristDeferred.await()
                        when (val t = touristResult.first()) {
                            is FieldResult.Error -> _touristState.value =
                                PayingState(error = t.error, errorType = t.fieldType)

                            FieldResult.IsSuccess -> {
                                _touristState.value = PayingState(
                                    isSuccess = true
                                )
                            }
                        }

                    } else {
                        if (emailResult is FieldResult.Error) {
                            _buyerInfoState.value =
                                PayingState(
                                    error = emailResult.error,
                                    errorType = emailResult.fieldType
                                )
                        }
                    }
                } else {
                    if (phoneResult is FieldResult.Error) {
                        _buyerInfoState.value =
                            PayingState(
                                error = phoneResult.error,
                                errorType = phoneResult.fieldType
                            )
                    }
                }
            } catch (e: Exception) {
                throw Exception("Something went wrong! $e")
            }
        }
    }

    fun clearBookingState() {
        _buyerInfoState.value = PayingState()
        _touristState.value = PayingState()
        _bookingDetailState.value = BookingDetailState()
        _hotelRoomsState.value = HotelRoomsState()
        _hotelState.value = HotelState()
    }

}