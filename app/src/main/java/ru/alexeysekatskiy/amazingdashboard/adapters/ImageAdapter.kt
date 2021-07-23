package ru.alexeysekatskiy.amazingdashboard.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.alexeysekatskiy.amazingdashboard.R

class ImageAdapter: RecyclerView.Adapter<ImageAdapter.ImageHolder>() {
    private val mainList = mutableListOf<String>()

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

        fun setData(uri: String) {
            imItem.setImageURI(Uri.parse(uri))
        }
    }

    fun update(newList: List<String>) {
        mainList.clear()
        mainList.addAll(newList)
        notifyDataSetChanged()
    }
}