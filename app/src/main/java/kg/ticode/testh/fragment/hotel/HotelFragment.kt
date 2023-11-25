package kg.ticode.testh.fragment.hotel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kg.ticode.testh.R
import kg.ticode.testh.adapter.HotelPeculiaritiesAdapter
import kg.ticode.testh.adapter.ImageSliderAdapter
import kg.ticode.testh.databinding.FragmentHotelBinding
import kg.ticode.testh.viewmodel.HotelViewModel
import java.util.Random

@AndroidEntryPoint
class HotelFragment : Fragment() {
    private lateinit var binding: FragmentHotelBinding

    private val hotelVM: HotelViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHotelBinding.inflate(inflater, container, false)
        hotelVM.getHotel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hotelVM.hotelState.observe(viewLifecycleOwner) { result ->
            binding.progressBar.isVisible = result.isLoading
            binding.scrollView.isVisible = result.isSuccess
            binding.reload.isVisible = result.error != null
            if (result.isSuccess) {
                val hotel = result.data
                initRCView(
                    hotel?.image_urls.orEmpty(),
                    hotel?.about_the_hotel?.peculiarities.orEmpty()
                )
                with(binding) {

                    imageSliderPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            circleIndicator.setSelectedPosition(position)
                        }
                    })
                    circleIndicator.setIndicators(hotel?.image_urls?.size ?: 0)
                    includedContainerRatingNameAddress.hotelName.text = hotel?.name
                    includedContainerRatingNameAddress.hostelAddressBtn.text = hotel?.adress
                    price.text =
                        "от ${hotel?.minimal_price} ${getString(R.string.code_rub)}"
                    priceForIt.text = hotel?.price_for_it
                    includedContainerRatingNameAddress.ratingText.text =
                        "${hotel?.rating} ${hotel?.rating_name}"
                    hostelDescription.text = hotel?.about_the_hotel?.description
                    includedContainerRatingNameAddress.hostelAddressBtn.setOnClickListener {
                        Toast.makeText(context, "Click address", Toast.LENGTH_SHORT).show()
                    }
                    toChooseNumberBtn.setOnClickListener {
                        hotelVM.setHotelName(includedContainerRatingNameAddress.hotelName.text.toString())
                        findNavController().navigate(R.id.action_feedFragment_to_numbersOfHotelFragment)
                    }

                }
            }
            binding.reload.setOnClickListener {
                hotelVM.getHotel()
            }


        }
    }

    private fun initRCView(images: List<String>, peculiarities: List<String>) = with(binding) {
        val adapter = ImageSliderAdapter(images)
        val peculiaritiesAdapter =
            HotelPeculiaritiesAdapter(peculiarities)
        imageSliderPager.adapter = adapter
        peculiaritiesRcView.adapter = peculiaritiesAdapter
        peculiaritiesRcView.layoutManager = FlexboxLayoutManager(this.root.context)
    }
}
