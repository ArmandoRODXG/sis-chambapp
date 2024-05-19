package com.project.chambaapp.data.Services

import com.project.chambaapp.data.Entities.ResenaItem
import com.project.chambaapp.data.Entities.UsuarioItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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

    @GET("/get_review/{idContratista}")
    fun obtenerResenas(@Path("idContratista") idContratista: Int): Call<List<ResenaItem>>
}