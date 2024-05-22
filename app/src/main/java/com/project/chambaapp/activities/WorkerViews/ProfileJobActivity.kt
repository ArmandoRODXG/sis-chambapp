package com.project.chambaapp.activities.WorkerViews

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.project.chambaapp.R
import com.project.chambaapp.api_services.FirebaseManager
import com.project.chambaapp.api_services.LocationServicesManager
import com.project.chambaapp.api_services.WorkerSingleton
import com.project.chambaapp.databinding.ActivityProfileJobBinding


class ProfileJobActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileJobBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombre = intent.getStringExtra("nombre")
        val oficios = intent.getStringExtra("oficios")
        val correo = intent.getStringExtra("correo")
        val descripcion = intent.getStringExtra("descripcion")
        val contacto = intent.getStringExtra("contacto")

        binding.correoContratista.text = correo.toString()
        binding.numeroContratista.text = contacto.toString()
        binding.cajaServicios.text = descripcion.toString()
        binding.nombreContratista.text = nombre.toString()
        binding.especialidadContratista.text = oficios.toString()

        binding.chipDisponible.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked){
                //Inicia Servicio de Geolocalizacion continua
                LocationServicesManager.kickstartLocationService(this@ProfileJobActivity)
                binding.chipDisponible.text = "Compartiendo"
            }else{
                //Detiene el servicio de Geolocalizacion continua
                LocationServicesManager.stopLocationService(this@ProfileJobActivity)
                binding.chipDisponible.text = "Desactivado"
            }
        }

        val items: List<String> = listOf("No Dsiponible", "Disponible", "En Servicio", "En Servicio y Disponible")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

         // Aplicar el adaptador al Spinner
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                WorkerSingleton.setWorkerState(this@ProfileJobActivity, position.toShort())

                val fb = FirebaseManager()

                fb.updateState(this@ProfileJobActivity)
                Toast.makeText(this@ProfileJobActivity, "Disponibilidad Actualizada", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                WorkerSingleton.setWorkerState(this@ProfileJobActivity, 0)

                val fb = FirebaseManager()

                fb.updateState(this@ProfileJobActivity)
            }
        }

        binding.btnAddTrabajo.setOnClickListener {
            val intent = Intent(this@ProfileJobActivity,ViewMyJobsActivity::class.java).apply {
                putExtra("LoggedUser",intent.getStringExtra("LoggedUser"))
                putExtra("oficios",oficios)
            }
            startActivity(intent)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LocationServicesManager.LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                LocationServicesManager.kickstartLocationService(this)
                Log.e("onRequestPermissionsResult", "Se validan los permisos")
            } else {
                Log.e("onRequestPermissionsResult", "No se validan los permisos")
            }
        }
    }
}
