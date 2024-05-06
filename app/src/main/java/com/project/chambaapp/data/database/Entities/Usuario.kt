package com.project.chambaapp.data.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val idUsuario: Long = 0,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "apellidos") val apellidos: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "numero_celular") val numeroCelular: String,
    @ColumnInfo(name = "direccion") val direccion: String,
    @ColumnInfo(name = "codigo_postal") val codigoPostal: String,
    @ColumnInfo(name = "contraseña") val contraseña: String
)
