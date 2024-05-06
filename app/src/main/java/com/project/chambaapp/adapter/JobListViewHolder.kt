package com.project.chambaapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.chambaapp.data.ui.JobList
import com.project.chambaapp.databinding.ItemJobBinding

class JobListViewHolder (view: View):RecyclerView.ViewHolder(view) {

    val binding = ItemJobBinding.bind(view)

    fun render(jobListModel: JobList){
        binding.tvName.text = jobListModel.name
        binding.tvUsername.text = jobListModel.username
        Glide.with(binding.iVuser.context).load(jobListModel.photo).into(binding.iVuser)
    }
}