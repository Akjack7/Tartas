package com.example.cakeslist.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cakeslist.R
import com.example.cakeslist.data.models.Cake

class HomeListAdapter(
    private val listener: CakeAdapterListener,

    ) :
    RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {

    interface CakeAdapterListener {
        fun onClickCake(description: String)
    }

    var items: List<Cake> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cake_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var imageView: de.hdodenhof.circleimageview.CircleImageView =
            view.findViewById(R.id.cakeImage)
        private var text: TextView = view.findViewById(R.id.cakeTitle)
        private var item: CardView = view.findViewById(R.id.cakeCard)

        fun bind(cake: Cake) {
            text.text = cake.title
            Glide
                .with(itemView.context)
                .load(cake.url)
                .centerCrop()
                .placeholder(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_launcher_foreground
                    )
                )
                .into(imageView)

            item.setOnClickListener {
                listener.onClickCake(cake.description)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}