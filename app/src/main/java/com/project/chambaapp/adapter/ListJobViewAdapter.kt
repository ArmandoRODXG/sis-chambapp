package com.project.chambaapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.chambaapp.R
import com.project.chambaapp.data.Entities.TrabajoItem

class ListJobViewAdapter (
    private val context: Context,
    private val list: List<TrabajoItem>,
) : RecyclerView.Adapter<ListJobViewViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListJobViewViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_theirjobs,parent,false)
        return ListJobViewViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ListJobViewViewHolder, position: Int) {
        val item = list[position]
        holder.render(item)
    }

}