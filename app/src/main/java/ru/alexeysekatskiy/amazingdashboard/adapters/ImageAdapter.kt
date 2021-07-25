package ru.alexeysekatskiy.amazingdashboard.adapters

import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.alexeysekatskiy.amazingdashboard.R

class ImageAdapter: RecyclerView.Adapter<ImageAdapter.ImageHolder>() {
    val mainList = mutableListOf<Bitmap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_adapter_item, parent, false)
        return ImageHolder(view)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.setData(mainList[position])
    }

    override fun getItemCount(): Int {
        return mainList.size
    }

    class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imItem: ImageView = itemView.findViewById(R.id.im_item)

        fun setData(bitmap: Bitmap) {
            imItem.setImageBitmap(bitmap)
        }
    }

    fun update(newList: List<Bitmap>) {
        mainList.clear()
        mainList.addAll(newList)
        notifyDataSetChanged()
    }
}