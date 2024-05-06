package com.project.chambaapp.data.database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.project.chambaapp.data.database.Entities.Contratista


@Dao
interface DaoContratista {
    @Query("SELECT * FROM contratistas")
    suspend fun obtenerContratistas(): MutableList<Contratista>

    @Insert
    suspend fun agregarUsuario(contratista: Contratista)


}
