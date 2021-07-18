package ru.alexeysekatskiy.amazingdashboard.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.alexeysekatskiy.amazingdashboard.R
import ru.alexeysekatskiy.amazingdashboard.activities.EditAdsActivity
import ru.alexeysekatskiy.amazingdashboard.utils.LocalityHelper

class DialogSpinnerHelper {

    fun showSpinnerDialog(context: Context, counties: List<String>, tvSelection: TextView) {
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        val rootView = LayoutInflater.from(context).inflate(R.layout.spinner_layout, null)
        val adapter = RecyclerDialogSpinnerAdapter(dialog, tvSelection)

        val recycler = rootView.findViewById<RecyclerView>(R.id.rec_view_spinner)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter
        adapter.updateAdapterWith(counties)
        val searchView = rootView.findViewById<SearchView>(R.id.search_view_spinner)

        dialog.setView(rootView)
        setSearchViewListener(adapter, counties, searchView)
        dialog.show()
    }

    private fun setSearchViewListener(adapter: RecyclerDialogSpinnerAdapter, localities: List<String>, searchView: SearchView) {
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val tempList = LocalityHelper.filterListData(localities, newText)
                adapter.updateAdapterWith(tempList)

                return true
            }
        })
    }
}