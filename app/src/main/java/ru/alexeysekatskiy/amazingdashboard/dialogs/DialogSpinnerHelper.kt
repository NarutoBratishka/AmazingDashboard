package ru.alexeysekatskiy.amazingdashboard.dialogs

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.alexeysekatskiy.amazingdashboard.R

class DialogSpinnerHelper {

    fun showSpinnerDialog(context: Context, counties: List<String>) {
        val builder = AlertDialog.Builder(context)
        val rootView = LayoutInflater.from(context).inflate(R.layout.spinner_layout, null)
        val adapter = RecyclerDialogSpinnerAdapter()

        val recycler = rootView.findViewById<RecyclerView>(R.id.rc_sp_view)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter
        adapter.updateAdapterWith(counties)

        builder.setView(rootView)
        builder.show()
    }
}