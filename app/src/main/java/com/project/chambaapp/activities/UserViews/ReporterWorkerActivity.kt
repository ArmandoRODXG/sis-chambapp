package com.project.chambaapp.activities.UserViews

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project.chambaapp.databinding.ActivityReporterWorkerBinding

class ReporterWorkerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReporterWorkerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReporterWorkerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se obtienen los datos que se enviaron desde la vista anterior
        val nombre = intent.getStringExtra("nombre")
        val usuario = intent.getStringExtra("usuario")
        val id = intent.getStringExtra("id")
        val idUsuario = intent.getStringExtra("LoggedUser")

        // Se asignan los valores a los componentes de la vista
        binding.tvReviewUsername.text = nombre.toString()

        binding.buttonPostReview.setOnClickListener {
            val descripcion = binding.ptPostDescReview.text.toString()
            if (descripcion.isEmpty()) {
                Toast.makeText(this, "La descripción no puede estar vacía", Toast.LENGTH_SHORT).show()
            } else {
                // Lógica para enviar el reporte
                reportarTrabajador()
            }
        }
    }

    private fun reportarTrabajador() {
        // Lógica para reportar al trabajador
        // Aquí puedes agregar el código necesario para enviar el reporte
    }
}
