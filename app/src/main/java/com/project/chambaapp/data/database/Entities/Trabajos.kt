package com.project.chambaapp.data.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trabajos")
data class Trabajos(
    @PrimaryKey(autoGenerate = true) val idTrabajo: Long = 0,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "descripcion") val descripcion: String,
    @ColumnInfo(name = "precio") val precio: Double
)
