package kg.ticode.testh.fragment.hotel

import NumberConverter
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kg.ticode.data.error_model.FieldType.CITIZENSHIP
import kg.ticode.data.error_model.FieldType.DATE_OF_BIRTH
import kg.ticode.data.error_model.FieldType.EMAIL
import kg.ticode.data.error_model.FieldType.FIRST_NAME
import kg.ticode.data.error_model.FieldType.LAST_NAME
import kg.ticode.data.error_model.FieldType.NULL
import kg.ticode.data.error_model.FieldType.PASSPORT_NUMBER
import kg.ticode.data.error_model.FieldType.PASSPORT_VALID_DATE
import kg.ticode.data.error_model.FieldType.PHONE_NUMBER
import kg.ticode.domain.network.dto.InfoAboutBuyer
import kg.ticode.domain.network.dto.Tourist
import kg.ticode.testh.R
import kg.ticode.testh.databinding.FragmentBookingBinding
import kg.ticode.testh.viewmodel.HotelViewModel
import kg.ticode.zukhridin_safarbek.phonemasker.Mask
import kg.ticode.zukhridin_safarbek.phonemasker.MaskChangedListener
import kg.ticode.zukhridin_safarbek.phonemasker.MaskStyle

@AndroidEntryPoint
class BookingFragment : Fragment() {
    private lateinit var binding: FragmentBookingBinding
    private val hotelVM: HotelViewModel by viewModels()
    private val phoneMasker = Mask(
        value = "+7 (***) ***-**-**",
        character = '*',
        style = MaskStyle.PERSISTENT
    )
    private val listener = MaskChangedListener(phoneMasker)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBookingBinding.inflate(layoutInflater)
        hotelVM.getBookingDetail()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.includedTopBarWithBackBtnTitle.title.text = getString(R.string.booking)
        binding.includedTopBarWithBackBtnTitle.back.setOnClickListener {
            findNavController().popBackStack()
        }
        hotelVM.bookingDetailState.observe(viewLifecycleOwner) { bookingDetail ->
            val amount = ((bookingDetail.data?.tour_price ?: 0) * (bookingDetail.data?.fuel_charge
                ?: 0) * (bookingDetail.data?.service_charge ?: 0))
            with(binding) {
                includedContainerRatingNameAddress.hotelName.text = bookingDetail.data?.hotel_name
                includedContainerRatingNameAddress.hostelAddressBtn.text =
                    bookingDetail.data?.hotel_adress
                includedContainerRatingNameAddress.ratingText.text =
                    "${bookingDetail.data?.horating} ${bookingDetail.data?.rating_name}"
                departureFrom.text = bookingDetail.data?.departure
                arrivalCountry.text = bookingDetail.data?.arrival_country
                date.text =
                    "${bookingDetail.data?.tour_date_start}-${bookingDetail.data?.tour_date_stop}"
                numberOfNights.text =
                    "${bookingDetail.data?.number_of_nights} ${getString(R.string.nights)}"
                hotel.text = bookingDetail.data?.hotel_name
                room.text = bookingDetail.data?.room
                nutrition.text = bookingDetail.data?.nutrition
                tourPrice.text = "${bookingDetail.data?.tour_price} ${getString(R.string.code_rub)}"
                fuelCollection.text =
                    "${bookingDetail.data?.fuel_charge} ${getString(R.string.code_rub)}"
                serviceFee.text =
                    "${bookingDetail.data?.service_charge} ${getString(R.string.code_rub)}"
                forPayment.text = "${amount} ${getString(R.string.code_rub)}"
                payBtn.text = "${getString(R.string.pay)} $amount ${getString(R.string.code_rub)}"
                phoneNumber.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
                    if (hasFocus) {
                        phoneNumber.hint = phoneMasker.value
                        phoneNumber.setHintTextColor(Color.BLACK)
                    }else{
                        phoneNumber.hint = ""
                    }
                }

                phoneNumber.addTextChangedListener(listener)
                addTourist.setOnClickListener {
                    addNewTourist()
                }
                if (newLayoutPlaceholder.childCount == 0) {
                    addNewTourist()
                }
                firstTouristAllFieldsFilled()
            }
        }
    }

    private fun firstTouristAllFieldsFilled() {
        val touristItem = binding.newLayoutPlaceholder.getChildAt(0)
        val firstName = touristItem.findViewById<TextInputEditText>(R.id.touristFirstName)
        val lastName = touristItem.findViewById<TextInputEditText>(R.id.touristLastName)
        val dateOfBirth = touristItem.findViewById<TextInputEditText>(R.id.dateOfBirth)
        val citizenship = touristItem.findViewById<TextInputEditText>(R.id.citizenship)
        val passportNumber =
            touristItem.findViewById<TextInputEditText>(R.id.passportNumber)
        val passportValidPeriod =
            touristItem.findViewById<TextInputEditText>(R.id.passportValidPeriod)
        hotelVM.buyerInfoState.observe(viewLifecycleOwner) { b ->
            clearError(binding.phoneNumber, binding.email, context = requireContext())
            when (b.errorType) {
                PHONE_NUMBER -> {
                    polyError(
                        binding.phoneNumber,
                        b.error.toString(),
                        requireContext()
                    )
                }

                EMAIL -> {
                    polyError(binding.email, b.error.toString(), requireContext())
                }

                else -> {
                }
            }
            if (b.error != null) {
                binding.error.text = b.error
                binding.error.isVisible = true
            }
            hotelVM.touristState.observe(viewLifecycleOwner) { t ->
                if (b.errorType == NULL) {
                    clearError(
                        binding.phoneNumber,
                        binding.email,
                        context = requireContext()
                    )
                }
                if (!b.isSuccess && !b.isLoading) {
                    binding.payBtn.isVisible = true
                    binding.btnProgress.isVisible = false

                }
                clearError(
                    firstName,
                    lastName,
                    dateOfBirth,
                    citizenship,
                    passportNumber,
                    passportValidPeriod,
                    context = requireContext()
                )
                when {
                    t.isSuccess -> {
                        binding.error.isVisible = false
                        binding.btnProgress.isVisible = false
                        binding.payBtn.isVisible = true

                        hotelVM.clearBookingState()
                        findNavController().navigate(R.id.action_bookingFragment_to_payIsSuccessfulFragment)
                    }

                    else -> {

                        binding.payBtn.isVisible = true
                        binding.btnProgress.isVisible = false


                        when (t.errorType) {
                            FIRST_NAME -> {
                                polyError(firstName, t.error.toString(), requireContext())
                            }

                            LAST_NAME -> {
                                polyError(lastName, t.error.toString(), requireContext())
                            }

                            DATE_OF_BIRTH -> {
                                polyError(dateOfBirth, t.error.toString(), requireContext())
                            }

                            CITIZENSHIP -> {
                                polyError(citizenship, t.error.toString(), requireContext())
                            }

                            PASSPORT_NUMBER -> {
                                polyError(passportNumber, t.error.toString(), requireContext())
                            }

                            PASSPORT_VALID_DATE -> {
                                polyError(
                                    passportValidPeriod,
                                    t.error.toString(),
                                    requireContext()
                                )
                            }

                            else -> {
                                clearError(
                                    firstName,
                                    lastName,
                                    dateOfBirth,
                                    citizenship,
                                    passportNumber,
                                    passportValidPeriod,
                                    context = requireContext()
                                )
                            }
                        }

                    }
                }
                if (t.error != null) {
                    binding.error.text = t.error
                    binding.error.isVisible = true
                }

            }

        }
        binding.payBtn.setOnClickListener {
            binding.payBtn.isVisible = false
            binding.btnProgress.isVisible = true
            val tourist = Tourist(
                firstName.text.toString(),
                lastName.text.toString(),
                dateOfBirth.text.toString(),
                citizenship.text.toString(),
                passportNumber.text.toString(),
                passportValidPeriod.text.toString()
            )
            val buyer =
                InfoAboutBuyer(binding.phoneNumber.text.toString(), binding.email.text.toString())

            hotelVM.pay(tourist, buyer)
        }
    }

    private fun addNewTourist() {
        val numberConverter = NumberConverter()
        val newLayout = layoutInflater.inflate(R.layout.item_tourist, null)
        val newParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        val touristCountText = newLayout.findViewById<TextView>(R.id.countTourist)
        touristCountText.text =
            "${numberConverter.getNumberNameWithFinalChars(binding.newLayoutPlaceholder.childCount + 1)} ${
                getString(R.string.tourist)
            }"
        newParams.setMargins(0, 22, 0, 0)
        newLayout.layoutParams = newParams
        val btn = newLayout.findViewById<MaterialButton>(R.id.showAll)
        val fields = newLayout.findViewById<LinearLayout>(R.id.fields)
        btnIconChange(fields.isVisible, btn)
        btn.setOnClickListener {
            fields.isVisible = !fields.isVisible
            btnIconChange(fields.isVisible, btn)
        }
        binding.newLayoutPlaceholder.addView(newLayout)

    }

    private fun btnIconChange(isVisible: Boolean, btn: MaterialButton) {
        if (isVisible) {
            btn.setIconResource(R.drawable.bottom)
        } else {
            btn.setIconResource(R.drawable.top)
        }
    }
}

private fun polyError(view: TextInputEditText, error: String, context: Context) {
    view.error = error
    view.backgroundTintList = ContextCompat.getColorStateList(context, R.color.errorColor)
}

private fun clearError(vararg view: TextInputEditText, context: Context) {
    view.forEach {
        it.backgroundTintList = ContextCompat.getColorStateList(context, R.color.fieldDefaultColor)
    }
}