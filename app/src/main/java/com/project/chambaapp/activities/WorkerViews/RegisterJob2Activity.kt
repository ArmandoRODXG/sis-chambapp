package com.project.chambaapp.activities.WorkerViews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterJob2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegisterJob.setOnClickListener{
            registrarUsuario()
        }

//        Log.d("nombre",nombre.toString())
//        Log.d("apellidos",apellidos.toString())
//        Log.d("usuario",usuario.toString())
//        Log.d("correo",email.toString())
//        Log.d("contraseña",contrasena.toString())



    }

    private fun registrarUsuario() {

        val nombre = intent.getStringExtra("nombre").toString()
        val apellidos = intent.getStringExtra("apellidos").toString()
        val usuario = intent.getStringExtra("usuario").toString()
        val email = intent.getStringExtra("correo").toString()
        val contrasena = intent.getStringExtra("contrasena").toString()

        val codigo_postal = binding.inputRegisterCodPJob.text.toString()
        val num_celular = binding.inputRegisterNumJob.text.toString()


        val contratista = ContratistaItem("",nombre,apellidos,usuario,contrasena,email,
            "Guadalajara","aa",num_celular,"123",
            codigo_postal,0.0.toFloat(),"false")

//        val baseUrl = "http:///contratistas/"
        val baseUrl = "https://is-chambapp-5bf6977200ac.herokuapp.com/contratistas/"
        val service = RetrofitClient.createService<ContratistasService>(baseUrl)

        service.agregarContratista(contratista).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    showToast("Contratista registrado correctamente")
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