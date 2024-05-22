package com.project.chambaapp.activities.UserViews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.chambaapp.R
import com.project.chambaapp.adapter.ListJobAdapter
import com.project.chambaapp.adapter.ListJobViewAdapter
import com.project.chambaapp.data.Entities.TrabajoItem
import com.project.chambaapp.data.RetrofitClient
import com.project.chambaapp.data.Services.ContratistasService
import com.project.chambaapp.databinding.ActivityViewJobsWorkerBinding
import retrofit2.Call
import retrofit2.Response

class ViewJobsWorkerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewJobsWorkerBinding
    private lateinit var service: ContratistasService
    private lateinit var rvMain : RecyclerView
    private lateinit var myAdapter: ListJobViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewJobsWorkerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        service = initRetrofitService()

        rvMain = binding.recyclerViewTheirJobs
        rvMain.layoutManager = LinearLayoutManager(this)

        cargarTrabajos()
    }

    private fun initRetrofitService(): ContratistasService {
//        val baseUrl = "http:///trabajos/"
        val baseUrl = "https://is-chambapp-5bf6977200ac.herokuapp.com/trabajos/"

        return RetrofitClient.createService(baseUrl)
    }

    private fun cargarTrabajos(){
        val idContratista = intent.getStringExtra("id")?.toIntOrNull()
        Log.d("idContratista",idContratista.toString())

        val retroData = service.obtenerTrabajos(idContratista!!)

        retroData.enqueue(object : retrofit2.Callback<List<TrabajoItem>>{
            override fun onResponse(
                call: Call<List<TrabajoItem>>,
                response: Response<List<TrabajoItem>>
            ) {
                if(response.isSuccessful){
                    val data = response.body() ?: emptyList()
                    Log.d("data", data.toString())
                    myAdapter = ListJobViewAdapter(this@ViewJobsWorkerActivity, data)
                    rvMain.adapter = myAdapter
                }else {
                    // Manejar el caso en el que la respuesta no es exitosa
                    Toast.makeText(this@ViewJobsWorkerActivity, "Error: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<TrabajoItem>>, t: Throwable) {
                Log.d("Churros", "xd call: $call  excepción: $t")
            }

        })
    }
}