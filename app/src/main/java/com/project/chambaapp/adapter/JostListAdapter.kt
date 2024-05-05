package com.project.chambaapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.chambaapp.R
import com.project.chambaapp.data.JobList

class JostListAdapter(private val joblist:List<JobList>) : RecyclerView.Adapter<JobListViewHolder>() {

    var onItemClick : ((JobList) -> Unit)? =  null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobListViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return JobListViewHolder(layoutInflater.inflate(R.layout.item_job,parent,false))
    }

    override fun onBindViewHolder(holder: JobListViewHolder, position: Int) {
        val item = joblist[position]
        holder.render(item)


    }

    override fun getItemCount(): Int = joblist.size
}