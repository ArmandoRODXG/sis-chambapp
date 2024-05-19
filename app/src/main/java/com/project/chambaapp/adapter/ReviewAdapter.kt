package com.project.chambaapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.chambaapp.R
import com.project.chambaapp.data.Entities.ResenaItem

class ReviewAdapter (
    private val context: Context,
    private val list: List<ResenaItem>
) : RecyclerView.Adapter<ReviewViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_review,parent,false)
        return ReviewViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val item = list[position]
        holder.render(item)
    }

}