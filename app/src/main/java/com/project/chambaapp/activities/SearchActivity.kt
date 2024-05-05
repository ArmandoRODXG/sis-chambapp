package com.project.chambaapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.chambaapp.R
import com.project.chambaapp.adapter.JostListAdapter
import com.project.chambaapp.data.JobList
import com.project.chambaapp.data.JobListProvider
import com.project.chambaapp.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
    }

    fun initRecyclerView(){
        binding.recyberJobList.layoutManager = LinearLayoutManager(this)
        binding.recyberJobList.adapter = JostListAdapter(JobListProvider.jobLists)
    }
}