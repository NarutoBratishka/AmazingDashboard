package ru.alexeysekatskiy.amazingdashboard.fragments

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.alexeysekatskiy.amazingdashboard.R
import ru.alexeysekatskiy.amazingdashboard.activities.EditAdsActivity
import ru.alexeysekatskiy.amazingdashboard.databinding.SelectImageFragItemBinding
import ru.alexeysekatskiy.amazingdashboard.utils.AdapterCallback
import ru.alexeysekatskiy.amazingdashboard.utils.ImageManager
import ru.alexeysekatskiy.amazingdashboard.utils.ImagePicker
import ru.alexeysekatskiy.amazingdashboard.utils.ImagePicker.REQUEST_CODE_GET_SINGLE_IMAGE
import ru.alexeysekatskiy.amazingdashboard.utils.ItemTouchMoveCallback

class SelectImageRVAdapter(var adapterCallback: AdapterCallback): RecyclerView.Adapter<SelectImageRVAdapter.ImageHolder>(), ItemTouchMoveCallback.ItemTouchCallbackInterface {
    var imageList = mutableListOf<Bitmap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val viewBinding = SelectImageFragItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ImageHolder(viewBinding, parent.context)
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

    inner class ImageHolder(val viewBinding: SelectImageFragItemBinding, val context: Context) : RecyclerView.ViewHolder(viewBinding.root) {

        fun setData(bitmap: Bitmap) {
            viewBinding.imageContent.setImageBitmap(bitmap)
            viewBinding.tvTitle.text = context.resources.getStringArray(R.array.title_image_array)[adapterPosition]

            viewBinding.imEditImage.setOnClickListener {
                ImagePicker.getImages(context as EditAdsActivity, 1, REQUEST_CODE_GET_SINGLE_IMAGE)
                context.editImagePos = adapterPosition
            }
            viewBinding.imDeleteImage.setOnClickListener {
                adapterCallback.onItemDelete()
                imageList.removeAt(adapterPosition)
                this@SelectImageRVAdapter.notifyItemRemoved(adapterPosition)

                for (n in 0 until imageList.size) this@SelectImageRVAdapter.notifyItemChanged(n)
            }
        }
    }

    fun updateAdapter(newList: List<Bitmap>, clear: Boolean = true) {
        if (clear) imageList.clear()

        imageList.addAll(newList)
        notifyDataSetChanged()
    }
}