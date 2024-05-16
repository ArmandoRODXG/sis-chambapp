package com.project.chambaapp.activities.WorkerViews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.project.chambaapp.data.Entities.ContratistaItem
import com.project.chambaapp.data.RetrofitClient
import com.project.chambaapp.data.Services.ContratistasService
import com.project.chambaapp.databinding.ActivityRegisterJobBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterJobActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterJobBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegisterJob.setOnClickListener {
            registrarUsuario()
        }
    }

    private fun registrarUsuario() {
        val nombre = binding.inputRegisterNameJob.text.toString()
        val apellidos = binding.inputRegisterApellidoJob.text.toString()
        val usuario = binding.inputRegisterUsernameJob.text.toString()
        val correo = binding.inputRegisterMailJob.text.toString()
        val contraseña = binding.inputRegisterPasswordJob.text.toString()
        val confirmarContraseña = binding.inputRegisterPasswordCJob.text.toString()

        if (contraseña != confirmarContraseña) {
            showToast("Las contraseñas no coinciden")
            return
        }

        if (nombre.isEmpty() || apellidos.isEmpty() || usuario.isEmpty() || correo.isEmpty() || contraseña.isEmpty()) {
            showToast("Por favor completa todos los campos")
            return
        }

        val intent = Intent(this@RegisterJobActivity,RegisterJob2Activity::class.java).apply {
            putExtra("nombre",nombre)
            putExtra("apellidos",apellidos)
            putExtra("usuario", usuario)
            putExtra("correo",correo)
            putExtra("contrasena",contraseña)
        }
        startActivity(intent)

//        val contratista = ContratistaItem(apellidos,contraseña,correo,"",nombre,usuario)
//        val baseUrl = "http://192.168.1.3:5000/contratistas/"
//        val service = RetrofitClient.createService<ContratistasService>(baseUrl)
//
//        service.agregarContratista(contratista).enqueue(object : Callback<Void> {
//            override fun onResponse(call: Call<Void>, response: Response<Void>) {
//                if (response.isSuccessful) {
//                    showToast("Contratista registrado correctamente")
//                } else {
//                    showToast("Error al agregar persona: ${response.code()}")
//                }
//            }
//
//            override fun onFailure(call: Call<Void>, t: Throwable) {
//                showToast("Error: ${t.message}")
//            }
//        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
