package com.project.chambaapp.data.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "contratistas")
data class Contratista(
    @PrimaryKey(autoGenerate = true) val idContratista: Long = 0,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "apellidos") val apellidos: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "correo") val correo: String,
    @ColumnInfo(name = "contrasena") val contrasena: String,
)
