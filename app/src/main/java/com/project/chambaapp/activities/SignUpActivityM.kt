package com.project.chambaapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.project.chambaapp.databinding.ActivitySignUpMBinding
import com.project.chambaapp.R
import com.project.chambaapp.activities.UserViews.SearchActivity
import com.project.chambaapp.activities.WorkerViews.RegisterJobActivity
import kotlinx.coroutines.launch

class SignUpActivityM : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpMBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_m)

        binding = ActivitySignUpMBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLog.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        binding.btnReg.setOnClickListener {
            val intent = Intent(this, RegisterJobActivity::class.java)
            startActivity(intent)
        }

    }
}