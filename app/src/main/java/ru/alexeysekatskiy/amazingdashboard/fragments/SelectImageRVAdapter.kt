package ru.alexeysekatskiy.amazingdashboard.fragments

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.alexeysekatskiy.amazingdashboard.R

class SelectImageRVAdapter: RecyclerView.Adapter<SelectImageRVAdapter.ImageHolder>() {
    private val imageList = mutableListOf<SelectImageItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.select_image_frag_item, parent, false)

        return ImageHolder(view)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.setData(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val image: ImageView = itemView.findViewById(R.id.image_content)

        fun setData(item: SelectImageItem) {
            item.apply {
                tvTitle.text = title
                image.setImageURI(Uri.parse(uri))
            }
        }
    }

    fun updateAdapter(newList: List<SelectImageItem>) {
        imageList.clear()
        imageList.addAll(newList)
        notifyDataSetChanged()
    }
}