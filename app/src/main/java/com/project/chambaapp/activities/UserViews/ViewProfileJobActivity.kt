package com.project.chambaapp.activities.UserViews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.project.chambaapp.activities.WorkerViews.ReviewActivity
import com.project.chambaapp.databinding.ActivitySearchBinding
import com.project.chambaapp.databinding.ActivityViewProfileJobBinding


class ViewProfileJobActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewProfileJobBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewProfileJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombre = intent.getStringExtra("nombre")
        val usuario = intent.getStringExtra("usuario")
        val id = intent.getStringExtra("id")
        val idUsuario = intent.getStringExtra("LoggedUser")

        binding.tvProfileViewJobUsername.text = nombre.toString()
        binding.tvProfileViewJobUser.text = usuario.toString()

        binding.buttonFormReview.setOnClickListener {
            val intent = Intent(this@ViewProfileJobActivity,ReviewActivity::class.java).apply {
                putExtra("nombre",nombre)
                putExtra("usuario",usuario)
                putExtra("id",id)
                putExtra("LoggedUser",idUsuario)
            }
            startActivity(intent)
        }

        binding.buttonViewReviews.setOnClickListener {
            val intent = Intent(this@ViewProfileJobActivity,ViewReviewsWorker::class.java).apply {
                putExtra("nombre",nombre)
                putExtra("usuario",usuario)
                putExtra("id",id)
                putExtra("LoggedUser",idUsuario)
            }
            startActivity(intent)
        }

        Log.d("nombre",nombre.toString())
        Log.d("usuario",usuario.toString())
    }
}