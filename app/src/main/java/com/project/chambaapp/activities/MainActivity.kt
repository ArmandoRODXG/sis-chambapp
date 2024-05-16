package com.project.chambaapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.chambaapp.R
import com.project.chambaapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usuario = "usuario"
        val contratista = "contratista"

        binding.cardViewUsuario.setOnClickListener {
            val intent = Intent(this@MainActivity,SignUpActivityM::class.java).apply {
                putExtra("TypeUser",usuario)
            }
            startActivity(intent)
        }

        binding.cardViewContratista.setOnClickListener {
            val intent = Intent(this@MainActivity,SignUpActivityM::class.java).apply {
                putExtra("TypeUser",contratista)
            }
            startActivity(intent)
        }
    }
}