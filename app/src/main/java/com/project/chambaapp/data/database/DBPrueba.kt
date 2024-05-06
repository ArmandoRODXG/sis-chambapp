package com.project.chambaapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.chambaapp.data.database.DAO.DaoContratista
import com.project.chambaapp.data.database.DAO.DaoContratistaTO
import com.project.chambaapp.data.database.DAO.DaoContratistaTT
import com.project.chambaapp.data.database.DAO.DaoEspecialidad
import com.project.chambaapp.data.database.DAO.DaoOficio
import com.project.chambaapp.data.database.DAO.DaoReporte
import com.project.chambaapp.data.database.DAO.DaoReseña
import com.project.chambaapp.data.database.DAO.DaoTrabajo
import com.project.chambaapp.data.database.DAO.DaoUsuario
import com.project.chambaapp.data.database.Entities.Contratista
import com.project.chambaapp.data.database.Entities.ContratistaTO
import com.project.chambaapp.data.database.Entities.ContratistaTT
import com.project.chambaapp.data.database.Entities.Especialidad
import com.project.chambaapp.data.database.Entities.Oficio
import com.project.chambaapp.data.database.Entities.Reporte
import com.project.chambaapp.data.database.Entities.Reseña
import com.project.chambaapp.data.database.Entities.Trabajos
import com.project.chambaapp.data.database.Entities.Usuario

@Database(
    entities = [Contratista::class, ContratistaTO::class,ContratistaTT::class,
        Especialidad::class,Oficio::class,Reporte::class,
        Reseña::class,Trabajos::class,Usuario::class],
    version = 1
)
abstract class DBPrueba: RoomDatabase() {
    abstract fun daoContratista(): DaoContratista
    abstract fun daoContratistaTO(): DaoContratistaTO
    abstract fun daoContratistaTT(): DaoContratistaTT
    abstract fun daoEspecialidad(): DaoEspecialidad
    abstract fun daoOficio(): DaoOficio
    abstract fun daoReporte(): DaoReporte
    abstract fun daoResena(): DaoReseña
    abstract fun daoTrabajo(): DaoTrabajo
    abstract fun daoUsuario(): DaoUsuario
}