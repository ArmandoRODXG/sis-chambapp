package com.project.chambaapp.data.Services

import com.project.chambaapp.data.Entities.ContratistaItem
import retrofit2.Call
import retrofit2.http.Body

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.Date

data class LoginRequest(
    val Usuario: String,
    val Contrasena: String
)

data class IdContratistaRequest(
    val idContratista: String
)

data class LoginResponse(
    val message: String,
    val usuarioId: String
)
data class ReviewRequest(
    val Descripcion: String,
    val idContratista: String,
    val idUsuario: String,
    val Valoracion: Float,
    val Fecha: String
)
data class OficiosResponse(
    val oficios: Array<String>
)

interface ContratistasService {
    @GET("/contratistas")
    fun obtenerContratistas(): Call<List<ContratistaItem>>

    @POST("/contratistas")
    fun agregarContratista(@Body contratista: ContratistaItem): Call<Void>

    @POST("/login_contratista")
    fun loginContratista(@Body body: LoginRequest): Call<LoginResponse>

    @POST("/post_review")
    fun enviarResena(@Body body: ReviewRequest): Call<Void>

    @POST("/oficios")
    fun obtenerOficios(@Body body: IdContratistaRequest): Call<OficiosResponse>
}