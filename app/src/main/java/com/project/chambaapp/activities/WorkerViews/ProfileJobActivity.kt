package com.project.chambaapp.activities.WorkerViews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.chambaapp.R
import com.project.chambaapp.databinding.ActivityProfileJobBinding


class ProfileJobActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileJobBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileJobBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    /* 
    val usuarioContratista = intent.getStringExtra("nombreContratista")
    val correoContratista = intent.getStringExtra("correoContratista")
    val contraseñaContratista = intent.getStringExtra("contraseñaContratista")
    val especialidadContratista = intent.getStringExtra("especialidadContratista")
    val numeroContratista = intent.getStringExtra("numeroContratista")
    val cajaServicios = intent.getStringExtra("cajaServicios")

    binding.nombreUsuario.text = usuarioContratista.toString()
    binding.correoContratista.text = correoContratista.toString()
    binding.contraseñaContratista.text = contraseñaContratista.toString()
    binding.especialidadContratista.text = especialidadContratista.toString()
    binding.numeroContratista.text = numeroContratista.toString()
    binding.cajaServicios.text = cajaServicios.toString()
    */


}
