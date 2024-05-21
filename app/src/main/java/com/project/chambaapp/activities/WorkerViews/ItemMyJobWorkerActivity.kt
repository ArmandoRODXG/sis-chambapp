package com.project.chambaapp.activities.WorkerViews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.project.chambaapp.databinding.ActivityItemMyJobsWorkerBinding

class ItemMyJobWorkerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemMyJobsWorkerBinding
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                binding = ActivityItemMyJobsWorkerBinding.inflate(layoutInflater)
                setContentView(binding.root)

                binding.btnWiewEdit.setOnClickListener {
                    val intent = Intent(this@ItemMyJobWorkerActivity,EditMyJobWorkerActivity::class.java).apply {

                    }
                    startActivity(intent)
                }

            }
}