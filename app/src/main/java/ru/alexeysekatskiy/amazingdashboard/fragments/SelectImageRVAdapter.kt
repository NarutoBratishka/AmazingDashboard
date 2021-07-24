package ru.alexeysekatskiy.amazingdashboard.fragments

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.alexeysekatskiy.amazingdashboard.R
import ru.alexeysekatskiy.amazingdashboard.utils.ItemTouchMoveCallback

class SelectImageRVAdapter: RecyclerView.Adapter<SelectImageRVAdapter.ImageHolder>(), ItemTouchMoveCallback.ItemTouchCallbackInterface {
    var imageList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.select_image_frag_item, parent, false)

        return ImageHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.setData(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onMove(startPos: Int, targetPos: Int) {
        imageList.apply { add(targetPos, removeAt(startPos)) }
        notifyItemMoved(startPos, targetPos)
    }

    override fun onClear() {
        notifyDataSetChanged()
    }

    class ImageHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val image: ImageView = itemView.findViewById(R.id.image_content)

        fun setData(item: String) {
            image.setImageURI(Uri.parse(item))
            tvTitle.text = context.resources.getStringArray(R.array.title_image_array)[adapterPosition]
        }
    }

    fun updateAdapter(newList: List<String>, clear: Boolean = true) {
        if (clear) imageList.clear()

        imageList.addAll(newList)
        notifyDataSetChanged()
    }
}