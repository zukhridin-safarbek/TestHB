package kg.ticode.testh.fragment.hotel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.ticode.testh.R
import kg.ticode.testh.databinding.FragmentPayIsSuccesfullBinding
import kg.ticode.testh.viewmodel.HotelViewModel
import java.util.Random
import kotlin.math.pow

@AndroidEntryPoint
class PayIsSuccessfulFragment : Fragment() {
    private lateinit var binding: FragmentPayIsSuccesfullBinding
    private val hotelVM: HotelViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPayIsSuccesfullBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            includedTopBarWithBackBtnTitle.title.text = getString(R.string.order_payed)
            includedTopBarWithBackBtnTitle.back.setOnClickListener {
                findNavController().popBackStack()
            }

            val random = generateRandomLong(6)
            orderInfo.text = getString(R.string.order_info, "№$random")
            superBtn.setOnClickListener {
                findNavController().navigate(R.id.action_payIsSuccessfulFragment_to_feedFragment)
            }
        }
    }


    private  fun generateRandomLong(length: Int): Long {
        require(length > 0) { "Length must be greater than 0" }

        val minValue = 10.0.pow(length - 1).toLong()
        val maxValue = 10.0.pow(length).toLong() - 1

        return kotlin.random.Random.nextLong(minValue, maxValue)
    }
}