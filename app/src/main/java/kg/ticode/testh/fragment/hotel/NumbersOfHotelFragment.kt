package kg.ticode.testh.fragment.hotel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kg.ticode.testh.R
import kg.ticode.testh.adapter.HotelNumbersAdapter
import kg.ticode.testh.databinding.FragmentNumbersOfHotelBinding
import kg.ticode.testh.viewmodel.HotelViewModel


@AndroidEntryPoint
class NumbersOfHotelFragment : Fragment(), HotelNumbersAdapter.HotelNumbersOnClickListener {
    private lateinit var binding: FragmentNumbersOfHotelBinding
    private val hotelVM: HotelViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNumbersOfHotelBinding.inflate(layoutInflater)
        hotelVM.getHotelRooms()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hotelVM.hotelRoomsState.observe(viewLifecycleOwner) { rooms ->
            binding.progressBar.isVisible = rooms.isLoading
            binding.scrollView.isVisible = rooms.isSuccess
            binding.includedTopBarWithBackBtnTitle.apply {
                hotelVM.getHotelName().observeForever {
                    title.text = it
                }
                back.setOnClickListener { findNavController().popBackStack() }
            }
            binding.rcView.apply {
                layoutManager =
                    LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
                adapter = rooms.data?.let { HotelNumbersAdapter(it, this@NumbersOfHotelFragment) }
            }
        }
    }

    override fun onClickChooseNumber() {
        findNavController().navigate(R.id.action_numbersOfHotelFragment_to_bookingFragment)
    }
}