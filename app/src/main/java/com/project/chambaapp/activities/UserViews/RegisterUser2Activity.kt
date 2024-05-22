package com.project.chambaapp.activities.UserViews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.project.chambaapp.activities.SignUpActivityM
import com.project.chambaapp.activities.WorkerViews.ViewMyJobsActivity
import com.project.chambaapp.data.Entities.UsuarioItem
import com.project.chambaapp.data.RetrofitClient
import com.project.chambaapp.data.Services.ContratistasService
import com.project.chambaapp.data.Services.UsuariosService
import com.project.chambaapp.databinding.ActivityRegisterUser2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterUser2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterUser2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterUser2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegisterUser.setOnClickListener{
            registrarUsuario()
        }
    }

    private fun registrarUsuario(){
        val nombre = intent.getStringExtra("nombre").toString()
        val apellidos = intent.getStringExtra("apellidos").toString()
        val correo = intent.getStringExtra("correo").toString()
        val domicilio = intent.getStringExtra("domicilio").toString()
        val numero_telefono = intent.getStringExtra("num_telefono").toString()
        val codigo_postal = intent.getStringExtra("codigo_postal").toString()

        val username = binding.inputRegisterNickname.text.toString()
        val contrasena = binding.inputRegisterPassword.text.toString()
        val conf_contrasena = binding.inputRegisterPasswordConfirm.text.toString()

        if (contrasena != conf_contrasena) {
            showToast("Las contraseñas no coinciden")
            return
        }

        if(username.isEmpty() || contrasena.isEmpty() || conf_contrasena.isEmpty()){
            showToast("Por favor completa todos los campos")
            return
        }

        val usuario = UsuarioItem(nombre,apellidos,username,correo,numero_telefono,domicilio,codigo_postal,contrasena)

//        val baseUrl = "http:///usuarios/"
        val baseUrl = "https://is-chambapp-5bf6977200ac.herokuapp.com/usuarios/"
        val service = RetrofitClient.createService<UsuariosService>(baseUrl)

        service.agregarUsuario(usuario).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    showToast("Usuario registrado correctamente")
                    val intent = Intent(this@RegisterUser2Activity, SignUpActivityM::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    }
                    startActivity(intent)
                    finish()
                } else {
                    showToast("Error al registrar usuario: ${response.code()}")
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