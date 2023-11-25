package kg.ticode.testh.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.ticode.testh.R

class ImageSliderAdapter(
    private val images: List<String>,
) : RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(imageUrl: String) {
            val image = itemView.findViewById<ImageView>(R.id.imageSliderItem)
            Glide.with(image.context)
                .load(imageUrl)
                .error(R.drawable.baseline_highlight_off_24)
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.image_silder_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageUrl = images[position])
    }
}