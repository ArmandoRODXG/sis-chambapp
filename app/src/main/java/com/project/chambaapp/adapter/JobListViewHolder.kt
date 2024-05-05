package com.project.chambaapp.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.chambaapp.R
import com.project.chambaapp.data.JobList
import com.project.chambaapp.databinding.ItemJobBinding

class JobListViewHolder (view: View):RecyclerView.ViewHolder(view) {

    val binding = ItemJobBinding.bind(view)

    fun render(jobListModel: JobList){
        binding.tvName.text = jobListModel.name
        binding.tvReview.text = jobListModel.review
        Glide.with(binding.iVuser.context).load(jobListModel.photo).into(binding.iVuser)
    }
}