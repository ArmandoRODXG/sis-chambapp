package com.project.chambaapp.data.Services

import com.project.chambaapp.data.Entities.UsuarioItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequestUser(
    val username: String,
    val contrasena: String
)

data class LoginResponseUser(
    val message: String,
    val usuarioId: String
)

interface UsuariosService{
    @POST("/usuarios")
    fun agregarUsuario(@Body usuario: UsuarioItem): Call<Void>

    @POST("/login_usuario")
    fun loginUsuario(@Body body: LoginRequestUser): Call<LoginResponseUser>
}