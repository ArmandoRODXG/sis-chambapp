package com.project.chambaapp.activities.WorkerViews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.project.chambaapp.activities.SignUpActivityM
import com.project.chambaapp.databinding.ActivityPayBinding

class PayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnPay.setOnClickListener {
            val intent = Intent(this@PayActivity,PayMethodActivity::class.java).apply {

            }
            startActivity(intent)
        }

        binding.btnSkip.setOnClickListener {
             val intent = Intent(this@PayActivity, ProfileJobActivity::class.java).apply {

            }
            startActivity(intent)
        }

    }
}
