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
import com.project.chambaapp.activities.WorkerViews.RegisterJobActivity
import com.project.chambaapp.api_services.LocationServicesManager
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
            val intent = Intent(this, RegisterUserActivity::class.java)
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

//        val baseUrl = "http:///login_contratista/"
        val baseUrl = "https://is-chambapp-5bf6977200ac.herokuapp.com/login_contratista/"
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
                                obtenerOficios(loginResponse.usuarioId)

                                val intent = Intent(this@SignUpActivityM, SearchActivity::class.java).apply {
                                    putExtra("LoggedUser", loginResponse.usuarioId)
                                }
                                startActivity(intent)

                                //Inicia Servicio de GPS continuo cada minuto y medio
                                LocationServicesManager.kickstartLocationService(this@SignUpActivityM)
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