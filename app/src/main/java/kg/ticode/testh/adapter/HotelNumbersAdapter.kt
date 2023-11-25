package kg.ticode.testh.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.flexbox.FlexboxLayoutManager
import kg.ticode.domain.network.dto.HotelNumbers
import kg.ticode.domain.network.dto.HotelRoom
import kg.ticode.testh.R
import kg.ticode.testh.databinding.ItemNumbersOfHotelBinding

class HotelNumbersAdapter(
    private val rooms: HotelNumbers,
    private val hotelNumbersOnClickListener: HotelNumbersOnClickListener,
) :
    RecyclerView.Adapter<HotelNumbersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemNumbersOfHotelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, hotelNumbersOnClickListener)
    }

    override fun getItemCount(): Int = rooms.rooms.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder
            .bind(rooms.rooms[position])
    }

    class ViewHolder(
        private val binding: ItemNumbersOfHotelBinding,
        private val hotelNumbersOnClickListener: HotelNumbersOnClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(room: HotelRoom) = with(binding) {
            initRCView(
                room
                    .image_urls, room.peculiarities
            )
            numberName.text = room.name
            price.text = "${room.price} ${price.context.getString(R.string.code_rub)}"
            priceForIt.text = room.price_per
            chooseNumberBtn.setOnClickListener {
                hotelNumbersOnClickListener.onClickChooseNumber()
            }
        }

        private fun initRCView(images: List<String>, peculiarities: List<String>) = with(binding) {
            val adapter = ImageSliderAdapter(images)
            val peculiaritiesAdapter =
                HotelPeculiaritiesAdapter(peculiarities)
            imageSliderPager.adapter = adapter
            peculiaritiesRcView.adapter = peculiaritiesAdapter
            peculiaritiesRcView.layoutManager = FlexboxLayoutManager(this.root.context)
            imageSliderPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    circleIndicator.setSelectedPosition(position)
                }
            })
            circleIndicator.setIndicators(images.size)
        }
    }

    interface HotelNumbersOnClickListener {
        fun onClickChooseNumber()
    }
}

