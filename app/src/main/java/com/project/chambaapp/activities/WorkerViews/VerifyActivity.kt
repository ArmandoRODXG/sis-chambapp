package com.project.chambaapp.activities.WorkerViews

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.chambaapp.R
import com.project.chambaapp.databinding.ActivityVerifyBinding

class VerifyActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private lateinit var binding: ActivityVerifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicialización de los componentes de la vista
        val imageViewUpload = binding.imageViewUpload
        val btnUploadImage = binding.btnUploadImage
        val btnRegisterJob2 = binding.btnRegisterJob2

        // Evento Click para el botón de Subir Imagen
        binding.btnUploadImage.setOnClickListener {
            openImageChooser()
        }

        // Evento Click para el botón de Registro
        binding.btnRegisterJob2.setOnClickListener {
            val intent = Intent(this@VerifyActivity, SolicitudActivity::class.java).apply {

            }
            startActivity(intent)

        }
    }

    // Método para Abrir el Selector de Imágenes
    private fun openImageChooser() {
        val intent = Intent()
        intent.type = "image/*" // Tipo de archivo
        intent.action = Intent.ACTION_GET_CONTENT // Acción de obtener contenido
        // Se inicia la actividad para obtener una imagen
        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), PICK_IMAGE_REQUEST) // Esto se muestra en el selector de imágenes
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val uri: Uri? = data.data
            binding.imageViewUpload.setImageURI(uri)
        }
    }
}
