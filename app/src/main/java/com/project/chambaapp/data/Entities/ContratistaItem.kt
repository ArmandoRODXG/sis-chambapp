package com.project.chambaapp.data.Entities

//data class ContratistaItem(
//    val apellidos: String,
//    val contrasena: String,
//    val email: String,
//    val id: String,
//    val nombre: String,
//    val usuario: String,
//    val estado: String,
//    val numero_celular: String,
//    val direccion: String,
//    val codigo_postal: String,
//    val presentacion_texto: String,
//    val rating: Float,
//    val verificado: String
//)

data class ContratistaItem(
    val id: String,
    val nombre: String,
    val apellidos: String,
    val usuario: String,
    val contrasena: String,
    val email: String,
    val estado: String,
    val presentacion_texto: String,
    val numero_celular: String,
    val direccion: String,
    val codigo_postal: String,
    val rating: Float,
    val verificado: String,
    val oficios: String
)
