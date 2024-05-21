package com.project.chambaapp.data.Services

import com.project.chambaapp.api_services.WorkerLocation
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

data class OficioRequest(
    val oficio: String,
)

data class GPSSearchRequest(
    val set: String
)

data class LoginResponse(
    val message: String,
    val usuarioId: String,
    val Nombre: String,
    val Apellidos: String,
    val Usuario: String,
    val Oficios: String
)

data class ReviewRequest(
    val Descripcion: String,
    val idContratista: String,
    val idUsuario: String,
    val Valoracion: Float,
    val Fecha: String
)

data class OficiosResponse(
    val usuarioId: Long,
    val message: String,
    val oficios: Array<Long>
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

    @POST("/buscar_contratista")
    fun buscarContratista(@Body oficio: OficioRequest): Call<List<ContratistaItem>>
    @POST("/buscar_contratista_por_gps")
    fun buscarContratistaPorGPS(@Body set: GPSSearchRequest): Call<List<ContratistaItem>>
}