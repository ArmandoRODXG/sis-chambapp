package com.project.chambaapp.data.Entities

data class TrabajoItem(
    val NombreCompleto: String,
    val OficioNombre: String,
    val Trabajo_descripcion: String,
    val Trabajo_especialidad: Any,
    val Trabajo_nombre: String,
    val Trabajo_precio: Double,
    val idCont_Oficios: Int,
    val idContratista: Int,
    val idOficio: Int,
    val idTrabajo: Int
)