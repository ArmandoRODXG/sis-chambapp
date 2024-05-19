package com.project.chambaapp.activities.UserViews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.chambaapp.R
import com.project.chambaapp.adapter.ReviewAdapter
import com.project.chambaapp.data.Entities.ResenaItem
import com.project.chambaapp.data.RetrofitClient
import com.project.chambaapp.data.Services.ContratistasService
import com.project.chambaapp.data.Services.UsuariosService
import com.project.chambaapp.databinding.ActivityViewReviewsWorkerBinding
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.security.auth.callback.Callback

class ViewReviewsWorker : AppCompatActivity() {

    private lateinit var binding: ActivityViewReviewsWorkerBinding
    private lateinit var rvMain : RecyclerView
    private lateinit var myAdapter: ReviewAdapter
    private lateinit var service: UsuariosService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewReviewsWorkerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvMain = binding.recyclerReviewList
        rvMain.layoutManager = LinearLayoutManager(this)

        service = initRetrofitService()

        initRecyclerView()

    }

    private fun initRetrofitService(): UsuariosService {
//        val baseUrl = "http:///get_review/"
        val baseUrl = "https://is-chambapp-5bf6977200ac.herokuapp.com/get_review/"

        return RetrofitClient.createService(baseUrl)
    }

    private fun initRecyclerView() {
        val idContratista = intent.getStringExtra("id")?.toIntOrNull()
        if (idContratista != null) {
            val retroData = service.obtenerResenas(idContratista)

            retroData.enqueue(object : retrofit2.Callback<List<ResenaItem>> {
                override fun onResponse(
                    call: Call<List<ResenaItem>>,
                    response: Response<List<ResenaItem>>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body() ?: emptyList()
                        myAdapter = ReviewAdapter(this@ViewReviewsWorker, data)
                        rvMain.adapter = myAdapter
                    } else {
                        // Manejar el caso en el que la respuesta no es exitosa
                        Toast.makeText(this@ViewReviewsWorker, "Error: ${response.message()}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<List<ResenaItem>>, t: Throwable) {
                    // Manejar el caso en el que la llamada falla
                    Toast.makeText(this@ViewReviewsWorker, "Failed to load data: ${t.message}", Toast.LENGTH_LONG).show()
                    t.printStackTrace()
                }
            })
        } else {
            // Manejar el caso en el que idContratista es null
            Toast.makeText(this, "Invalid contractor ID", Toast.LENGTH_LONG).show()
        }
    }
}