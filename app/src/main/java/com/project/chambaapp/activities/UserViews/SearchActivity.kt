package com.project.chambaapp.activities.UserViews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.chambaapp.R
import com.project.chambaapp.data.Entities.ContratistaItem
import com.project.chambaapp.data.RetrofitClient
import com.project.chambaapp.data.Services.ContratistasService
import com.project.chambaapp.data.Services.OficioRequest
import com.project.chambaapp.databinding.ActivitySearchBinding
import com.project.getexample.JobAdapter
import retrofit2.Call
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var rvMain : RecyclerView
    private lateinit var myAdapter: JobAdapter
    private lateinit var service: ContratistasService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvMain = binding.recyclerJobList
        rvMain.layoutManager = LinearLayoutManager(this)

        var selectedItem: String? = null

        val items = listOf(
            "Albañil","Plomero","Carpintero",
            "Pintor","Fontanero","Jardinero",
            "Instalador de Pisos","Techador","Vidriero",
            "Cerrajero","Limpieza y mantenimiento"
        )
        val ddAdapter = ArrayAdapter(this, R.layout.list_job_select, items)
        binding.dropdownFieldSearchjob.setAdapter(ddAdapter)

        binding.dropdownFieldSearchjob.setOnItemClickListener { parent, view, position, id ->
            selectedItem = parent.getItemAtPosition(position).toString()
            Log.d("Trabajo seleccionado", "$selectedItem")
        }

        service = initRetrofitService()

        myAdapter = JobAdapter(baseContext, emptyList()) { contratista ->
            val intent = Intent(this@SearchActivity, ViewProfileJobActivity::class.java).apply {
                putExtra("nombre", contratista.nombre)
                putExtra("usuario", contratista.usuario)
                putExtra("id",contratista.id)
                putExtra("LoggedUser", intent.getStringExtra("LoggedUser"))
            }
            startActivity(intent)
        }
        rvMain.adapter = myAdapter


        binding.buttonSearch.setOnClickListener {
            initRecyclerView(selectedItem!!)
        }
    }

    private fun initRetrofitService(): ContratistasService {
//        val baseUrl = "http://192.168.1.3:5000/buscar_contratista/"
        val baseUrl = "http://is-chambapp-5bf6977200ac.herokuapp.com/buscar_contratista/"

        return RetrofitClient.createService(baseUrl)
    }

    private fun initRecyclerView(selectedItem: String) {
        val idUsuario = intent.getStringExtra("LoggedUser")
        val request = OficioRequest(selectedItem)
        val retroData = service.buscarContratista(request)

        retroData.enqueue(object : retrofit2.Callback<List<ContratistaItem>> {
            override fun onResponse(
                call: Call<List<ContratistaItem>>,
                response: Response<List<ContratistaItem>>
            ) {
                val data = response.body() ?: emptyList()

                myAdapter.updateData(data)

                Log.d("data", data.toString())
            }

            override fun onFailure(call: Call<List<ContratistaItem>>, t: Throwable) {
                //
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (myAdapter.itemCount > 0) {
            myAdapter.clearData()
//            initRecyclerView()
        }
    }
}
