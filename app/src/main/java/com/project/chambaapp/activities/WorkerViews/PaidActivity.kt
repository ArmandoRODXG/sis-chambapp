package com.project.chambaapp.activities.WorkerViews

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.project.chambaapp.R
import android.content.Intent
import com.project.chambaapp.databinding.ActivityPaidBinding

class PaidActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaidBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaidBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            val intent = Intent(this@PaidActivity, ProfileJobActivity::class.java).apply {
            }
            startActivity(intent)
        }

    }
}