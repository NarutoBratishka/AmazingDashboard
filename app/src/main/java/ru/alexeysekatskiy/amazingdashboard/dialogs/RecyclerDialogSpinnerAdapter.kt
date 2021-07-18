package ru.alexeysekatskiy.amazingdashboard.dialogs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.alexeysekatskiy.amazingdashboard.R

class RecyclerDialogSpinnerAdapter:
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

    class SpinnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvSpElement = itemView.findViewById<TextView>(R.id.tv_sp_element)
        fun setData(text: String) {
            tvSpElement.text = text
        }
    }

    fun updateAdapterWith(newList: List<String>) {
        itemList.clear()
        itemList.addAll(newList)

        notifyDataSetChanged()
    }

}