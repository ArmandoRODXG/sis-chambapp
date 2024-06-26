package com.project.chambaapp.activities.WorkerViews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.Gson
import com.project.chambaapp.R
import com.project.chambaapp.data.Entities.TrabajoItem
import com.project.chambaapp.data.RetrofitClient
import com.project.chambaapp.data.Services.ContratistasService
import com.project.chambaapp.data.Services.IdContratistaRequest
import com.project.chambaapp.data.Services.OficiosResponse
import com.project.chambaapp.data.Services.TrabajoRequest
import com.project.chambaapp.databinding.ActivityRegisterOfficeJobBinding
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class RegisterOfficeJob : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterOfficeJobBinding
    private val oficiosList = mutableListOf<String>()
    private lateinit var service: ContratistasService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterOfficeJobBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var selectedOficio: String? = null

        Log.d("Oficios",intent.getStringExtra("oficios").toString())

        val oficiosString = intent.getStringExtra("oficios")
        val oficioItems = if (!oficiosString.isNullOrEmpty()) {
            oficiosString.split(",\\s*".toRegex())
        } else {
            emptyList()
        }

        val oficioAdapter = ArrayAdapter(this, R.layout.list_job_select, oficioItems)

        binding.dropdownFieldOffices.setAdapter(oficioAdapter)

        binding.dropdownFieldOffices.setOnItemClickListener { parent, view, position, id ->

            selectedOficio = parent.getItemAtPosition(position).toString()
            Log.d("Oficio", "$selectedOficio")
        }

        service = initRetrofitService()

        binding.registerButton.setOnClickListener {
            registrarTrabajo(selectedOficio!!)
        }
    }

    private fun initRetrofitService(): ContratistasService {
//        val baseUrl = "http:///trabajos/"
        val baseUrl = "https://is-chambapp-5bf6977200ac.herokuapp.com/trabajos/"

        return RetrofitClient.createService(baseUrl)
    }

    private fun registrarTrabajo(selectedOficio: String){
        val nombre = binding.serviceNameInput.text.toString()
        val cotizacion = binding.quoteInput.text.toString()
        val descripcion = binding.descriptionInput.text.toString()

        val idContratistaString = intent.getStringExtra("LoggedUser")
        val idContratista = idContratistaString?.toIntOrNull() ?: 0

        val trabajo = TrabajoRequest(
            idContratista = idContratista,
            Oficio_nombre = binding.dropdownFieldOffices.text.toString(),
            Trabajo_nombre = nombre,
            Trabajo_descripcion = descripcion,
            Trabajo_precio = cotizacion.toFloatOrNull() ?: 0f
        )

        service.registrarTrabajo(trabajo).enqueue(object : retrofit2.Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    showToast("Trabajo registrado correctamente")
                    val intent = Intent(this@RegisterOfficeJob, ViewMyJobsActivity::class.java).apply {
                        putExtra("LoggedUser", idContratistaString)
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    }
                    startActivity(intent)
                    finish()
                } else {
                    showToast("Error al agregar trabajo: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                showToast("Error: ${t.message}")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}