package ru.alexeysekatskiy.amazingdashboard.fragments

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
    var imageList = mutableListOf<SelectImageItem>()

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

    override fun onMove(startPos: Int, targetPos: Int) {
        /*imageList.apply { add(targetPos, removeAt(startPos)) }*/

        /*imageList[startPos] = imageList[targetPos].also { imageList[targetPos] = imageList[startPos] }
            imageList.apply {
                imageList[startPos].title = imageList[targetPos].title.also { imageList[targetPos].title = imageList[startPos].title }
            }*/

        val targetItem = imageList[targetPos]
        imageList[targetPos] = imageList[startPos]
        val startTitle = imageList[startPos].title
        imageList[targetPos].title = targetItem.title
        imageList[startPos] = targetItem
        imageList[startPos].title = startTitle

        notifyItemMoved(startPos, targetPos)
    }

    override fun onClear() {
        notifyDataSetChanged()
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

    fun updateAdapter(newList: List<SelectImageItem>, clear: Boolean = true) {
        if (clear) imageList.clear()

        imageList.addAll(newList)
        notifyDataSetChanged()
    }
}