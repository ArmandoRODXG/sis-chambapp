package com.project.chambaapp.activities.WorkerViews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.project.chambaapp.activities.UserViews.SearchActivity
import com.project.chambaapp.data.RetrofitClient
import com.project.chambaapp.data.Services.ContratistasService
import com.project.chambaapp.data.Services.ReviewRequest
import com.project.chambaapp.databinding.ActivityReviewBinding
import okio.Buffer
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date

class ReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usuario = intent.getStringExtra("usuario")
        val idContratista = intent.getStringExtra("id").toString()
        val idUsuario = intent.getStringExtra("LoggedUser").toString()

        binding.tvReviewUsername.text = usuario

        binding.buttonPostReview.setOnClickListener {
            val descripcion = binding.ptPostDescReview.text.toString()
            val valoracion = binding.rbReviewJob.rating
            val fecha = obtenerFechaActual()


            if (descripcion.isEmpty() || valoracion.toDouble() == 0.0) {
                showToast("Por favor, ingresa una descripción y califica al usuario para tu reseña.")
            } else {
                registrarResena(descripcion, idContratista, idUsuario, valoracion, fecha)
            }
        }

    }

    private fun obtenerFechaActual(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = Date()
        return sdf.format(currentDate)
    }

    private fun registrarResena(descripcion: String, idContratista: String, idUsuario: String, valoracion: Float, fecha: String) {
//        val baseUrl = "http:///post_review/"
        val baseUrl = "https://is-chambapp-5bf6977200ac.herokuapp.com/post_review/"
        val service = RetrofitClient.createService<ContratistasService>(baseUrl)

        val request = ReviewRequest(descripcion, idContratista, idUsuario, valoracion, fecha)

        service.enviarResena(request).enqueue(object : retrofit2.Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    showToast("Reseña enviada correctamente")

                    val intent = Intent(this@ReviewActivity,SearchActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                    finish()
                } else {
                    val requestBody = call.request().body()
                    var bodyString = ""
                    if (requestBody != null) {
                        val buffer = Buffer()
                        requestBody.writeTo(buffer)
                        bodyString = buffer.readUtf8()
                    }
                    Log.e("Error en la solicitud", "Cuerpo de la solicitud: $bodyString")
                    showToast("Ocurrió un error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                showToast("Error: ${t.message}")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}



