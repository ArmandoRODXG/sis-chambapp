package com.project.chambaapp.data.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "contratista_tiene_trabajos",
    primaryKeys = ["idContratista", "idTrabajo"],
    foreignKeys = [
        ForeignKey(entity = Contratista::class,
            parentColumns = ["idContratista"],
            childColumns = ["idContratista"]),
        ForeignKey(entity = Trabajos::class,
            parentColumns = ["idTrabajo"],
            childColumns = ["idTrabajo"])
    ])
data class ContratistaTT(
    @ColumnInfo(name = "idContratista") val idContratista: Long,
    @ColumnInfo(name = "idTrabajo") val idTrabajo: Long
)