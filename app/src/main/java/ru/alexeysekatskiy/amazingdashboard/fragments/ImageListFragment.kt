package ru.alexeysekatskiy.amazingdashboard.fragments

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.alexeysekatskiy.amazingdashboard.R
import ru.alexeysekatskiy.amazingdashboard.databinding.FragmentImageListBinding
import ru.alexeysekatskiy.amazingdashboard.dialogs.ProgressDialog
import ru.alexeysekatskiy.amazingdashboard.utils.AdapterCallback
import ru.alexeysekatskiy.amazingdashboard.utils.ImageManager
import ru.alexeysekatskiy.amazingdashboard.utils.ImagePicker
import ru.alexeysekatskiy.amazingdashboard.utils.ItemTouchMoveCallback

class ImageListFragment(private val fragCloseInterface: FragCloseInterface, private val images: List<String>?): Fragment(), AdapterCallback {
    lateinit var rootElement: FragmentImageListBinding
    val adapter = SelectImageRVAdapter(this)
    val dragCallback = ItemTouchMoveCallback(adapter)
    val touchHelper = ItemTouchHelper(dragCallback)
    private var addItem: MenuItem? = null
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootElement = FragmentImageListBinding.inflate(inflater)
        return rootElement.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()

        val recyclerView = rootElement.rcViewSelectImage
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        touchHelper.attachToRecyclerView(recyclerView)

        if (images != null) resizeSelectedImages(images)
    }

    fun updateAdapterFromEdit(bitmapList: List<Bitmap>) {
        adapter.updateAdapter(bitmapList)
    }

    override fun onDetach() {
        super.onDetach()
        fragCloseInterface.onFragClose(adapter.imageList)
        job?.cancel()
    }

    private fun resizeSelectedImages(images: List<String>, clear: Boolean = true) {
        job = CoroutineScope(Dispatchers.Main).launch {
            val dialog = ProgressDialog.createProgressDialog(activity as Activity)
            val bitmapList = ImageManager.imageResize(images)
            dialog.dismiss()
            adapter.updateAdapter(bitmapList, clear)

            addItem?.isVisible = adapter.imageList.size <= 2
        }
    }

    private fun setUpToolbar() {
        rootElement.tbImageList.inflateMenu(R.menu.menu_select_image)
        val deleteItem = rootElement.tbImageList.menu.findItem(R.id.id_delete_image)
        addItem = rootElement.tbImageList.menu.findItem(R.id.id_add_image)

        rootElement.tbImageList.setNavigationOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.remove(this@ImageListFragment)?.commit()
        }

        deleteItem.setOnMenuItemClickListener {
            adapter.updateAdapter(listOf())
            addItem?.isVisible = true
            true
        }

        addItem?.setOnMenuItemClickListener {
            val count = ImagePicker.MAX_IMAGE_COUNT - adapter.imageList.size
            if (count > 0) {
                ImagePicker.getImages(activity as AppCompatActivity, count)
                Log.d("qwe: menuImageList", "Add item")
                true
            } else false
        }
    }

    fun updateAdapter(newList: List<String>) {
        resizeSelectedImages(newList, false)
    }

    fun setSingleImage(uri: String, pos: Int) {
        val prBar = rootElement.rcViewSelectImage[pos].findViewById<ProgressBar>(R.id.pr_bar_image_item)
        prBar.visibility = View.VISIBLE
        job = CoroutineScope(Dispatchers.Main).launch {
            val bitmapList = ImageManager.imageResize(listOf(uri))
            prBar.visibility = View.GONE

            adapter.imageList[pos] = bitmapList[0]
            adapter.notifyItemChanged(pos)
        }
    }

    override fun onItemDelete() {
        addItem?.isVisible = true
    }
}