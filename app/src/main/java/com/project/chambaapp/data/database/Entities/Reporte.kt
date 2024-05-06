package com.project.chambaapp.data.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//
@Entity(tableName = "reportes", foreignKeys = [
    ForeignKey(entity = Usuario::class, parentColumns = ["idUsuario"], childColumns = ["idUsuario"]),
    ForeignKey(entity = Contratista::class, parentColumns = ["idContratista"], childColumns = ["idContratista"])
])
data class Reporte(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idReporte") val idReporte: Long = 0,
    @ColumnInfo(name = "motivo") val motivo: String,
    @ColumnInfo(name = "descripcion") val descripcion: String,
    @ColumnInfo(name = "idUsuario") val idUsuario: Long,
    @ColumnInfo(name = "idContratista") val idContratista: Long
)