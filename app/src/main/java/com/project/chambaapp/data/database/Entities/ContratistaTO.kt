package com.project.chambaapp.data.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "contratista_tiene_oficios",
    primaryKeys = ["idContratista", "idOficio"],
    foreignKeys = [
        ForeignKey(entity = Contratista::class,
            parentColumns = ["idContratista"],
            childColumns = ["idContratista"]),
        ForeignKey(entity = Oficio::class,
            parentColumns = ["idOficio"],
            childColumns = ["idOficio"])
    ])
data class ContratistaTO(
    @ColumnInfo(name = "idContratista") val idContratista: Long,
    @ColumnInfo(name = "idOficio") val idOficio: Long
)
