package com.project.chambaapp.activities.WorkerViews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.project.chambaapp.data.database.DBPrueba
import com.project.chambaapp.data.database.Entities.Contratista
import com.project.chambaapp.databinding.ActivityRegisterJobBinding
import kotlinx.coroutines.launch

class RegisterJobActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterJobBinding
    private lateinit var room: DBPrueba

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        room = Room.databaseBuilder(this, DBPrueba::class.java,"ChambaaaaaApp").build()

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

        val nuevoContratista = Contratista(0,nombre,apellidos,usuario,correo,contraseña)

        lifecycleScope.launch {
            room.daoContratista().agregarUsuario(nuevoContratista)

            showToast("Contratista registrado correctamente")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
