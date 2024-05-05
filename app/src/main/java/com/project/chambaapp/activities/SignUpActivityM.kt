package com.project.chambaapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.chambaapp.databinding.ActivitySignUpMBinding
import com.project.chambaapp.R

class SignUpActivityM : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpMBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_m)

        binding = ActivitySignUpMBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLog.setOnClickListener {
            val intent = Intent(this,SearchActivity::class.java)
            startActivity(intent)
        }


    }
}