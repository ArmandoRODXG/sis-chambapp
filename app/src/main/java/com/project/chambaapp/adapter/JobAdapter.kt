package com.project.getexample

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.project.chambaapp.R
import com.project.chambaapp.data.Entities.ContratistaItem

class JobAdapter(
    private val context: Context,
    private var list: List<ContratistaItem>,
    private val onItemClick: (ContratistaItem) -> Unit
) : RecyclerView.Adapter<JobViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_job, parent, false)
        return JobViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val item = list[position]
        holder.render(item)
        holder.itemView.findViewById<Button>(R.id.ButtonViewProfileJob).setOnClickListener {
            onItemClick(item)
        }
    }

    fun updateData(newData: List<ContratistaItem>) {
        list = newData
        notifyDataSetChanged()
    }

    fun clearData() {
        list = emptyList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size
}