package com.project.chambaapp.data.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "reseñas",
    foreignKeys = [
        ForeignKey(entity = Usuario::class,
            parentColumns = ["idUsuario"],
            childColumns = ["idUsuario"]),
        ForeignKey(entity = Contratista::class,
            parentColumns = ["idContratista"],
            childColumns = ["idContratista"])
    ])
data class Reseña(
    @PrimaryKey(autoGenerate = true) val idResena: Long = 0,
    @ColumnInfo(name = "fecha") val fecha: String,
    @ColumnInfo(name = "descripcion") val descripcion: String,
    @ColumnInfo(name = "trabajoHecho") val trabajoHecho: String,
    @ColumnInfo(name = "idUsuario") val idUsuario: Long,
    @ColumnInfo(name = "idContratista") val idContratista: Long
)