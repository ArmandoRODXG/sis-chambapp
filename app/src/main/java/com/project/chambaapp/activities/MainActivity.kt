package com.project.chambaapp.activities

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.project.chambaapp.api_services.LocationServicesManager
import com.project.chambaapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usuario = "usuario"
        val contratista = "contratista"

        binding.cardViewUsuario.setOnClickListener {
            val intent = Intent(this@MainActivity,SignUpActivityM::class.java).apply {
                putExtra("TypeUser",usuario)
            }
            startActivity(intent)
        }

        binding.cardViewContratista.setOnClickListener {
            val intent = Intent(this@MainActivity,SignUpActivityM::class.java).apply {
                putExtra("TypeUser",contratista)
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
                Log.e("onRequestPermissionsResult", "Se validan los permisos")
            } else {
                Log.e("onRequestPermissionsResult", "No se validan los permisos")
            }
        }
    }
}