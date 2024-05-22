package com.project.chambaapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.project.chambaapp.databinding.ActivitySignUpMBinding
import com.project.chambaapp.R
import com.project.chambaapp.activities.UserViews.RegisterUserActivity
import com.project.chambaapp.activities.UserViews.SearchActivity
import com.project.chambaapp.activities.WorkerViews.ProfileJobActivity
import com.project.chambaapp.activities.WorkerViews.RegisterJobActivity
import com.project.chambaapp.activities.WorkerViews.VerifyActivity
import com.project.chambaapp.api_services.WorkerSingleton
import com.project.chambaapp.data.RetrofitClient
import com.project.chambaapp.data.Services.ContratistasService
import com.project.chambaapp.data.Services.IdContratistaRequest
import com.project.chambaapp.data.Services.LoginRequest
import com.project.chambaapp.data.Services.LoginRequestUser
import com.project.chambaapp.data.Services.LoginResponse
import com.project.chambaapp.data.Services.LoginResponseUser
import com.project.chambaapp.data.Services.OficiosResponse
import com.project.chambaapp.data.Services.UsuariosService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivityM : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpMBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_m)

        binding = ActivitySignUpMBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val typeUser = intent.getStringExtra("TypeUser")

        when (typeUser) {
            "usuario" -> setupUsuarioFunctionality()
            "contratista" -> setupContratistaFunctionality()
            else -> {
                Toast.makeText(this, "Tipo de usuario no válido", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun setupUsuarioFunctionality() {
        binding.btnReg.setOnClickListener {
            val intent = Intent(this, VerifyActivity::class.java)
            startActivity(intent)
        }

//        val baseUrl = "http:///login_usuario/"
        val baseUrl = "https://is-chambapp-5bf6977200ac.herokuapp.com/login_usuario/"

        val service = RetrofitClient.createService<UsuariosService>(baseUrl)

        binding.btnLog.setOnClickListener {
            val email = binding.nombre.text.toString()
            val password = binding.contrasenia.text.toString()
            val request = LoginRequestUser(email, password)

            service.loginUsuario(request)
                .enqueue(object : Callback<LoginResponseUser> {
                    override fun onResponse(call: Call<LoginResponseUser>, response: Response<LoginResponseUser>) {
                        if (response.isSuccessful) {
                            val loginResponse = response.body()
                            if (loginResponse != null) {
                                val intent = Intent(this@SignUpActivityM, SearchActivity::class.java).apply {
                                    putExtra("LoggedUser", loginResponse.usuarioId)
                                    putExtra("usuario", loginResponse.username)

                                }
                                startActivity(intent)
                            }
                        } else {
                            Toast.makeText(this@SignUpActivityM, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponseUser>, t: Throwable) {
                        Toast.makeText(this@SignUpActivityM, "Error de conexión", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    private fun obtenerOficios(usuarioId: String) {
//        val baseUrl = "http:///oficios/"
        val baseUrl = "https://is-chambapp-5bf6977200ac.herokuapp.com/oficios/"
        val service = RetrofitClient.createService<ContratistasService>(baseUrl)

        val request = IdContratistaRequest(usuarioId)

        service.obtenerOficios(request)
            .enqueue(object : Callback<OficiosResponse> {
                override fun onResponse(call: Call<OficiosResponse>, response: Response<OficiosResponse>) {
                    if (response.isSuccessful) {
                        val oficiosResponse = response.body()
                        if (oficiosResponse != null) {
//                            Log.d("Oficios", "Oficios obtenidos exitosamente: ${oficiosResponse.oficios.joinToString(", ")}")
                            Log.d("Cuerpo", oficiosResponse.toString())

                            //inicializa instancia global de contratista
                            WorkerSingleton.setAll(this@SignUpActivityM,usuarioId.toLong(), oficiosResponse.oficios)
                        }
                    } else {
                        Log.e("Oficios", "Error al obtener los oficios")
                    }
                }

                override fun onFailure(call: Call<OficiosResponse>, t: Throwable) {
                    Log.e("Oficios", "Error de conexión al obtener los oficios", t)
                }
            })
    }

    private fun setupContratistaFunctionality() {
        binding.btnReg.setOnClickListener {
            val intent = Intent(this, RegisterJobActivity::class.java)
            startActivity(intent)
        }

        val baseUrl = "http://192.168.1.2:5000/login_contratista/"
//        val baseUrl = "https://is-chambapp-5bf6977200ac.herokuapp.com/login_contratista/"
        val service = RetrofitClient.createService<ContratistasService>(baseUrl)

        binding.btnLog.setOnClickListener {
            val email = binding.nombre.text.toString()
            val password = binding.contrasenia.text.toString()
            val request = LoginRequest(email, password)

            service.loginContratista(request)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful) {
                            val loginResponse = response.body()
                            if (loginResponse != null) {
                                val intent = Intent(this@SignUpActivityM, ProfileJobActivity::class.java).apply {
                                    putExtra("LoggedUser", loginResponse.usuarioId)
                                    putExtra("nombre",loginResponse.Nombre + ' ' + loginResponse.Apellidos)
                                    putExtra("oficios",loginResponse.Oficios)
                                    putExtra("contacto",loginResponse.Contacto)
                                    putExtra("correo",loginResponse.Email)
                                    putExtra("descripcion",loginResponse.Presentacion_texto)
                                }
                                startActivity(intent)

                                obtenerOficios(loginResponse.usuarioId)
                            }
                        } else {
                            Toast.makeText(this@SignUpActivityM, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@SignUpActivityM, "Error de conexión", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}