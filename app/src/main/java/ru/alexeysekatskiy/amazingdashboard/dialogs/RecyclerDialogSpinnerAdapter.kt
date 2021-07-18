package ru.alexeysekatskiy.amazingdashboard.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import ru.alexeysekatskiy.amazingdashboard.R
import ru.alexeysekatskiy.amazingdashboard.activities.EditAdsActivity

class RecyclerDialogSpinnerAdapter(private val dialog: AlertDialog, private val tvSelection: TextView):
    RecyclerView.Adapter<RecyclerDialogSpinnerAdapter.SpinnerViewHolder>() {
    private val itemList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpinnerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.spinner_element, parent, false)
        return SpinnerViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpinnerViewHolder, position: Int) {
        holder.setData(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class SpinnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val tvSpElement = itemView.findViewById<TextView>(R.id.tv_sp_element)
        fun setData(text: String) {
            tvSpElement.text = text
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            tvSelection.text = tvSpElement.text
            dialog.dismiss()
        }
    }

    fun updateAdapterWith(newList: List<String>) {
        itemList.clear()
        itemList.addAll(newList)

        notifyDataSetChanged()
    }

}