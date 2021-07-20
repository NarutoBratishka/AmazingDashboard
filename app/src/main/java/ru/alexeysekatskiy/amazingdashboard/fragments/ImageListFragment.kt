package ru.alexeysekatskiy.amazingdashboard.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import ru.alexeysekatskiy.amazingdashboard.R

class ImageListFragment: Fragment() {
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
        bBack.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                this.remove(this@ImageListFragment)
                commit()
            }
        }
    }
}