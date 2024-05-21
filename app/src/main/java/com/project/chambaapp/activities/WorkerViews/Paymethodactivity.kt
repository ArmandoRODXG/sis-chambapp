package com.project.chambaapp.activities.WorkerViews

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import com.project.chambaapp.databinding.ActivityPayMethodBinding

class PayMethodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPayMethodBinding
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                binding = ActivityPayMethodBinding.inflate(layoutInflater)
                setContentView(binding.root)

                binding.button2.setOnClickListener {
                    val intent = Intent(this@PayMethodActivity, PaidActivity::class.java).apply {

                    }
                    startActivity(intent)
                }

            }
}