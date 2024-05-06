package com.project.chambaapp.data.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "especialidades",
    foreignKeys = [
        ForeignKey(entity = Trabajos::class,
            parentColumns = ["idTrabajo"],
            childColumns = ["idTrabajo"])
    ])
data class Especialidad(
    @PrimaryKey(autoGenerate = true) val idEspecialidad: Long = 0,
    @ColumnInfo(name = "idTrabajo") val idTrabajo: Long
)