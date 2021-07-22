package ru.alexeysekatskiy.amazingdashboard.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.alexeysekatskiy.amazingdashboard.R
import ru.alexeysekatskiy.amazingdashboard.utils.ItemTouchMoveCallback

class ImageListFragment(private val fragCloseInterface: FragCloseInterface, private val images: List<String>): Fragment() {
    val adapter = SelectImageRVAdapter()
    val dragCallback = ItemTouchMoveCallback(adapter)
    val touchHelper = ItemTouchHelper(dragCallback)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bBack = view.findViewById<Button>(R.id.b_back)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rc_view_select_image)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        touchHelper.attachToRecyclerView(recyclerView)

        val newList = mutableListOf<SelectImageItem>()
        for (i in images.indices) {
            newList.add(SelectImageItem("${i+1}", images[i])) //todo fix naming
        }
        adapter.updateAdapter(newList)


        bBack.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                this.remove(this@ImageListFragment)
                commit()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        fragCloseInterface.onFragClose()
        adapter.imageList.forEachIndexed { index, selectImageItem ->
            Log.d("qwe", "Title $index: ${selectImageItem.title}")
        }
    }
}