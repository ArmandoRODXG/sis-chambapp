package com.project.chambaapp.activities.UserViews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.chambaapp.R
import com.project.chambaapp.databinding.ActivityProfileUserBinding

class ProfileUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*val usuario = intent.getStringExtra("usuario")
        val idUsuario = intent.getStringExtra("LoggedUser").toString()
        val nombre = intent.getStringExtra("nombre")
        val correo = intent.getStringExtra("correo")
        val password = intent.getStringExtra("password")

        binding.cajaNombreUsuario.text = usuario.toString()
        binding.cajaNombre.text = nombre.toString()
        binding.cajaCorreo.text = usuario.toString()
        binding.cajaPassword.text = password.toString()
        */

        /*

        // Configurar el botón para cambiar la edición de las cajas de texto
        binding.btnEditar.setOnClickListener {//no esta funcionando
            binding.cajaNombreUsuario.isEnabled = true
            binding.cajaNombre.isEnabled = true
            binding.cajaCorreo.isEnabled = true
            binding.cajaPassword.isEnabled = true
            
        }
         */
    }
}