package com.project.chambaapp.activities.WorkerViews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.chambaapp.R
import com.project.chambaapp.adapter.FavoriteAdapter
import com.project.chambaapp.adapter.ListJobAdapter
import com.project.chambaapp.data.Entities.TrabajoItem
import com.project.chambaapp.data.RetrofitClient
import com.project.chambaapp.data.Services.ContratistasService
import com.project.chambaapp.data.Services.UsuariosService
import com.project.chambaapp.databinding.ActivityViewMyJobsBinding
import retrofit2.Call
import retrofit2.Response

class ViewMyJobsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewMyJobsBinding
    private lateinit var service: ContratistasService
    private lateinit var rvMain : RecyclerView
    private lateinit var myAdapter: ListJobAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewMyJobsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val idContratista = intent.getStringExtra("LoggedUser")
        val oficios = intent.getStringExtra("oficios")
        Log.d("id",idContratista.toString())



        binding.buttonAddJob.setOnClickListener {
            val intent = Intent(this@ViewMyJobsActivity,RegisterOfficeJob::class.java).apply{
                putExtra("LoggedUser",idContratista)
                putExtra("oficios",oficios)
            }
            startActivity(intent)
        }

        rvMain = binding.recyclerViewMyJobs
        rvMain.layoutManager = LinearLayoutManager(this)

        service = initRetrofitService()

        cargarTrabajos()
    }

    private fun initRetrofitService(): ContratistasService {
//        val baseUrl = "http:///trabajos/"
        val baseUrl = "https://is-chambapp-5bf6977200ac.herokuapp.com/trabajos/"

        return RetrofitClient.createService(baseUrl)
    }

    private fun cargarTrabajos(){
        val idContratista = intent.getStringExtra("LoggedUser")?.toIntOrNull()

        val retroData = service.obtenerTrabajos(idContratista!!)

        retroData.enqueue(object : retrofit2.Callback<List<TrabajoItem>>{
            override fun onResponse(
                call: Call<List<TrabajoItem>>,
                response: Response<List<TrabajoItem>>
            ) {
                if(response.isSuccessful){
                    val data = response.body() ?: emptyList()
                    Log.d("data", data.toString())
                    myAdapter = ListJobAdapter(this@ViewMyJobsActivity, data)
                    rvMain.adapter = myAdapter
                }else {
                    // Manejar el caso en el que la respuesta no es exitosa
                    Toast.makeText(this@ViewMyJobsActivity, "Error: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<TrabajoItem>>, t: Throwable) {
                Log.d("Churros", "xd call: $call  excepción: $t")
            }

        })
    }
}