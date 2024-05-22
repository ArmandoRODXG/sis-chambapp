package com.project.chambaapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.chambaapp.R
import com.project.chambaapp.data.Entities.TrabajoItem

class ListJobAdapter (
    private val context: Context,
    private val list: List<TrabajoItem>,
//    private val onItemClick: (TrabajoItem) -> Unit
) : RecyclerView.Adapter<ListJobViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListJobViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_myjobs,parent,false)
        return ListJobViewHolder(view)

    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ListJobViewHolder, position: Int) {
        val item = list[position]
        holder.render(item)
    }

}