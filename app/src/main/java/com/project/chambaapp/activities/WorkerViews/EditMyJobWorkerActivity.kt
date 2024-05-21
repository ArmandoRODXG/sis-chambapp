package com.project.chambaapp.activities.WorkerViews

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.project.chambaapp.R
import com.project.chambaapp.databinding.ActivityEditMyJobWorkerBinding
class EditMyJobWorkerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditMyJobWorkerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditMyJobWorkerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.confirmButton.setOnClickListener {
            val intent = Intent(this@EditMyJobWorkerActivity, ViewMyJobsWorkerActivity::class.java).apply {

            }
            startActivity(intent)
        }
    }
}