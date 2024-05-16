package com.project.chambaapp.activities.UserViews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.project.chambaapp.databinding.ActivityRegisterUserBinding

class RegisterUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinueUser.setOnClickListener{
            registrarUsuario()
        }
    }

    private fun registrarUsuario(){
        val nombre = binding.inputRegisterNames.text.toString()
        val apellidos = binding.inputRegisterApellido1.text.toString()
        val correo = binding.inputRegisterMail.text.toString()
        val domicilio = binding.inputRegisterDomicilio.text.toString()
        val codigo_postal = binding.inputRegisterPostal.text.toString()
        val num_telefono = binding.inputRegisterTelefono.text.toString()

        if (nombre.isEmpty() || apellidos.isEmpty() || domicilio.isEmpty() || correo.isEmpty() || codigo_postal.isEmpty() || num_telefono.isEmpty()) {
            showToast("Por favor completa todos los campos")
            return
        }

        val intent = Intent(this@RegisterUserActivity,RegisterUser2Activity::class.java).apply {
            putExtra("nombre",nombre)
            putExtra("apellidos",apellidos)
            putExtra("correo",correo)
            putExtra("domicilio",domicilio)
            putExtra("codigo_postal",codigo_postal)
            putExtra("num_telefono",num_telefono)
        }
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}