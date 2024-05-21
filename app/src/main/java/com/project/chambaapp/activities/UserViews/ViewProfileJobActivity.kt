package com.project.chambaapp.activities.UserViews

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.chambaapp.R
import com.project.chambaapp.activities.WorkerViews.ReviewActivity
import com.project.chambaapp.data.Entities.FavoritoItem
import com.project.chambaapp.data.RetrofitClient
import com.project.chambaapp.data.Services.UsuariosService
import com.project.chambaapp.databinding.ActivitySearchBinding
import com.project.chambaapp.databinding.ActivityViewProfileJobBinding
import retrofit2.Call
import retrofit2.Response


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
        val rating_value = intent.getFloatExtra("rating_bar", 0.0f)

        binding.tvProfileViewJobUsername.text = nombre.toString()
        binding.tvProfileViewJobUser.text = usuario.toString()
        binding.rbProfileViewJob.rating = rating_value

        binding.buttonFormReview.setOnClickListener {
            val intent = Intent(this@ViewProfileJobActivity, ReviewActivity::class.java).apply {
                putExtra("nombre", nombre)
                putExtra("usuario", usuario)
                putExtra("id", id)
                putExtra("LoggedUser", idUsuario)
            }
            startActivity(intent)
        }

        binding.buttonViewReviews.setOnClickListener {
            val intent = Intent(this@ViewProfileJobActivity, ViewReviewsWorker::class.java).apply {
                putExtra("nombre", nombre)
                putExtra("usuario", usuario)
                putExtra("id", id)
                putExtra("LoggedUser", idUsuario)
            }
            startActivity(intent)
        }

        binding.buttonReportWorker.setOnClickListener {
//            val numeroTelefono = "+523322485961" // Remplaza con el número real
//            val mensajePrecompuesto = "Hola, me pongo en contacto a través de ChabApp y estoy interesado en contratar sus servicios." // Personaliza el mensaje
//
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$numeroTelefono&text=$mensajePrecompuesto")
//            try {
//                startActivity(intent)
//            } catch (e: ActivityNotFoundException) {
//                // WhatsApp no está instalado, informa al usuario
//                Toast.makeText(this, "No tienes instalado WhatsApp en tu dispositivo.", Toast.LENGTH_SHORT).show()
//            }

            val url = "https://t.me/SuperMaistroBot" // URL del bot de Telegram
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)

        }
    }

}