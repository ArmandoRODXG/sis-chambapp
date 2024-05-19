package com.project.chambaapp.activities.WorkerViews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.project.chambaapp.R
import com.project.chambaapp.data.Entities.ContratistaItem
import com.project.chambaapp.data.RetrofitClient
import com.project.chambaapp.data.Services.ContratistasService
import com.project.chambaapp.databinding.ActivityRegisterJob2Binding
import com.project.chambaapp.databinding.ActivityRegisterJobBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterJob2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterJob2Binding
    private val oficiosList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterJob2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var selectedStatus: String? = null
        var selectedOficio: String? = null

        val statusItems = listOf(
            "Guadalajara"
        )
        val statusAdapter = ArrayAdapter(this, R.layout.list_job_select, statusItems)
        binding.dropdownFieldStatus.setAdapter(statusAdapter)

        binding.dropdownFieldStatus.setOnItemClickListener { parent, view, position, id ->
            selectedStatus = parent.getItemAtPosition(position).toString()
//            Log.d("Estado", "$selectedStatus")
        }

        val oficioItems = listOf(
            "Albañil","Plomero","Carpintero",
            "Pintor","Fontanero","Jardinero",
            "Instalador de Pisos","Techador","Vidriero",
            "Cerrajero","Limpieza y mantenimiento"
        )
        val oficioAdapter = ArrayAdapter(this, R.layout.list_job_select, oficioItems)
        binding.dropdownFieldOficios.setAdapter(oficioAdapter)

        binding.dropdownFieldOficios.setOnItemClickListener { parent, view, position, id ->
            selectedOficio = parent.getItemAtPosition(position).toString()
//            Log.d("Oficio", "$selectedOficio")
        }

        binding.btnAddOficio.setOnClickListener {

            selectedOficio?.let { oficio ->

                oficiosList.add(oficio)
                addOficioView(oficio)
//                Toast.makeText(this, "Oficio añadido: $it", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRegisterJob.setOnClickListener {
            registrarUsuario(selectedStatus!!)
        }
    }

    private fun addOficioView(oficio: String) {

        val linearLayout = LinearLayout(this)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        linearLayout.layoutParams = layoutParams
        linearLayout.orientation = LinearLayout.HORIZONTAL

        val textView = TextView(this)
        val textViewParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        textView.layoutParams = textViewParams
        textView.text = oficio

        val deleteButton = Button(this)
        deleteButton.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        deleteButton.text = "Eliminar"
        deleteButton.setOnClickListener {
            oficiosList.remove(oficio)
            binding.llOficiosContainer.removeView(linearLayout)
        }

        linearLayout.addView(textView)
        linearLayout.addView(deleteButton)

        binding.llOficiosContainer.addView(linearLayout)
    }

    private fun registrarUsuario(selectedItem: String) {

        val nombre = intent.getStringExtra("nombre").toString()
        val apellidos = intent.getStringExtra("apellidos").toString()
        val usuario = intent.getStringExtra("usuario").toString()
        val email = intent.getStringExtra("correo").toString()
        val contrasena = intent.getStringExtra("contrasena").toString()

        val codigo_postal = binding.inputRegisterCodPJob.text.toString()
        val num_celular = binding.inputRegisterNumJob.text.toString()
        val presentacion = binding.inputDescriptionJob.text.toString()

        val oficiosString = oficiosList.joinToString(", ")

        val contratista = ContratistaItem("",nombre,apellidos,usuario,contrasena,email,
            selectedItem,presentacion,num_celular,"123",
            codigo_postal,0.0.toFloat(),"false",oficiosString)

        val baseUrl = "http://192.168.1.3:5000/contratistas/"
//        val baseUrl = "https://is-chambapp-5bf6977200ac.herokuapp.com/contratistas/"
        val service = RetrofitClient.createService<ContratistasService>(baseUrl)

        service.agregarContratista(contratista).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    showToast("Contratista registrado correctamente")
                    val gson = Gson()
                    val jsonContratista = gson.toJson(contratista)
                    Log.d("JSON_CONTRATISTA", jsonContratista)
                } else {
                    showToast("Error al agregar persona: ${response.code()}")
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