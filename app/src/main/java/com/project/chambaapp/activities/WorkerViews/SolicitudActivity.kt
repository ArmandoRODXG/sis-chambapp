package com.project.chambaapp.activities.WorkerViews

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.chambaapp.databinding.ActivitySolicitudBinding

class SolicitudActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySolicitudBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySolicitudBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            val intent = Intent(this@SolicitudActivity, PayActivity::class.java)
            startActivity(intent)  // Esta línea estaba faltando
        }
    }
}
