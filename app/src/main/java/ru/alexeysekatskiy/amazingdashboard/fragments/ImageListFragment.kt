package ru.alexeysekatskiy.amazingdashboard.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import ru.alexeysekatskiy.amazingdashboard.R
import ru.alexeysekatskiy.amazingdashboard.databinding.FragmentImageListBinding
import ru.alexeysekatskiy.amazingdashboard.utils.ImagePicker
import ru.alexeysekatskiy.amazingdashboard.utils.ItemTouchMoveCallback

class ImageListFragment(private val fragCloseInterface: FragCloseInterface, private val images: List<String>): Fragment() {
    lateinit var rootElement: FragmentImageListBinding
    val adapter = SelectImageRVAdapter()
    val dragCallback = ItemTouchMoveCallback(adapter)
    val touchHelper = ItemTouchHelper(dragCallback)

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

        val newList = mutableListOf<SelectImageItem>()
        for (i in images.indices) {
            newList.add(SelectImageItem("${i+1}", images[i])) //todo fix naming
        }
        adapter.updateAdapter(newList)

    }

    override fun onDetach() {
        super.onDetach()
        fragCloseInterface.onFragClose(adapter.imageList.map { it.uri })
    }

    private fun setUpToolbar() {
        rootElement.tbImageList.inflateMenu(R.menu.menu_select_image)
        val deleteItem = rootElement.tbImageList.menu.findItem(R.id.id_delete_image)
        val addItem = rootElement.tbImageList.menu.findItem(R.id.id_add_image)

        rootElement.tbImageList.setNavigationOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.remove(this@ImageListFragment)?.commit()
        }

        deleteItem.setOnMenuItemClickListener {
            adapter.updateAdapter(listOf())
            true
        }

        addItem.setOnMenuItemClickListener {
            val count = ImagePicker.MAX_IMAGE_COUNT - adapter.imageList.size
//            if (count > 0) {
                ImagePicker.getImages(activity as AppCompatActivity, count)
                Log.d("qwe: menuImageList", "Add item")
                true
//            } else false
        }
    }

    fun updateAdapter(newList: List<String>) {
        val toAddList = mutableListOf<SelectImageItem>()
        for (i in adapter.imageList.size until (adapter.imageList.size + newList.size)) {
            toAddList.add(SelectImageItem("${i+1}", newList[i - adapter.imageList.size])) //todo fix naming
        }
        adapter.updateAdapter(toAddList, false)
    }
}