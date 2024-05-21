package com.project.chambaapp.data.Services

import com.project.chambaapp.data.Entities.FavoritoItem
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
    val usuarioId: String,
    val nombre: String,
    val apellidos: String,
    val username: String,
    val email: String,
    val numero_celular: String,
    val direccion: String,
    val codigo_postal: String,
)

interface UsuariosService{
    @POST("/usuarios")
    fun agregarUsuario(@Body usuario: UsuarioItem): Call<Void>

    @POST("/login_usuario")
    fun loginUsuario(@Body body: LoginRequestUser): Call<LoginResponseUser>

    @GET("/get_review/{idContratista}")
    fun obtenerResenas(@Path("idContratista") idContratista: Int): Call<List<ResenaItem>>

    @GET("/favoritos/{idUsuario}")
    fun obtenerFavoritos(@Path("idUsuario") idUsuario: Int): Call<List<FavoritoItem>>
}