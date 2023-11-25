package kg.ticode.testh.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kg.ticode.testh.R

class HotelPeculiaritiesAdapter(private val items: List<String>) :
    RecyclerView.Adapter<HotelPeculiaritiesAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: String) {
            val itemView = itemView.findViewById<TextView>(R.id.item_peculiarities)
            itemView.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_peculiarities, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}