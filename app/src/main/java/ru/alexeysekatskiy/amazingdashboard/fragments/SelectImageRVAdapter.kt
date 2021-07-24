package ru.alexeysekatskiy.amazingdashboard.fragments

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.alexeysekatskiy.amazingdashboard.R
import ru.alexeysekatskiy.amazingdashboard.activities.EditAdsActivity
import ru.alexeysekatskiy.amazingdashboard.utils.ImagePicker
import ru.alexeysekatskiy.amazingdashboard.utils.ImagePicker.REQUEST_CODE_GET_SINGLE_IMAGE
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

    inner class ImageHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val image: ImageView = itemView.findViewById(R.id.image_content)
        val remainImage: ImageButton = itemView.findViewById(R.id.im_edit_image)
        val deleteImage: ImageButton = itemView.findViewById(R.id.im_delete_image)

        fun setData(item: String) {
            image.setImageURI(Uri.parse(item))
            tvTitle.text = context.resources.getStringArray(R.array.title_image_array)[adapterPosition]

            remainImage.setOnClickListener {
                ImagePicker.getImages(context as EditAdsActivity, 1, REQUEST_CODE_GET_SINGLE_IMAGE)
                context.editImagePos = adapterPosition
            }
            deleteImage.setOnClickListener {
                imageList.removeAt(adapterPosition)
                this@SelectImageRVAdapter.notifyItemRemoved(adapterPosition)

                for (n in 0 until imageList.size) this@SelectImageRVAdapter.notifyItemChanged(n)
            }
        }
    }

    fun updateAdapter(newList: List<String>, clear: Boolean = true) {
        if (clear) imageList.clear()

        imageList.addAll(newList)
        notifyDataSetChanged()
    }
}