package com.project.chambaapp.data.Entities

object WorkersFilter {
    val filters = listOf(
        "Ninguna",
        "Más de 3 Estrellas",
        "Más de 4 Estrellas",
        "Menos de 3 Estrellas",
        "Solo Verificados",
        "Solo Disponibles",
        "Filtrar No Disponibles",
        "A menos de 500 metros de distancia",
        "A menos de 1 km de distancia",
    )
    fun filterWorkers(selectedFilter: String, listWorkers: List<ContratistaItem>) :  List<ContratistaItem>{
        return when(filters.indexOf(selectedFilter)){
            1-> listWorkers.filter { it.rating  > 60.0 }
            2-> listWorkers.filter { it.rating  > 80.0 }
            3-> listWorkers.filter { it.rating  < 60.01 }
            4-> listWorkers.filter { it.verificado == "Verificado" }
            5-> listWorkers.filter { it.disponibilidad == 1 }
            6-> listWorkers.filter { it.disponibilidad!! > 0 }
            7-> listWorkers.filter { it.distancia!! < 500 }
            8-> listWorkers.filter { it.distancia!! < 1000 }
            else -> listWorkers
        }
    }


}