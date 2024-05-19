package com.project.chambaapp.api_services

import com.google.firebase.firestore.GeoPoint
import kotlin.math.*

class CoordinatesManager
    () {
    private val latitudes: ArrayList<Double> = ArrayList()
    private val longitudes: ArrayList<Double> = ArrayList()

    companion object { // limites del plano cartesiano de la zona metropolitana de guadalajara
        val zmg_max_lat = 20.778
        val zmg_min_lat = 20.541
        val zmg_max_lon =  -103.198
        val zmg_min_lon =  -103.481


        fun distanceBetweenGeoPoints(point1: GeoPoint, point2: GeoPoint): Double {
            val earthRadius = 6371.0 // Radio de la Tierra en kilómetros

            val lat1 = Math.toRadians(point1.latitude)
            val lon1 = Math.toRadians(point1.longitude)
            val lat2 = Math.toRadians(point2.latitude)
            val lon2 = Math.toRadians(point2.longitude)

            val dLat = lat2 - lat1
            val dLon = lon2 - lon1

            val a = sin(dLat / 2).pow(2.0) + cos(lat1) * cos(lat2) * sin(dLon / 2).pow(2.0)
            val c = 2 * atan2(sqrt(a), sqrt(1 - a))

            return earthRadius * c
        }
    }

    init {//inicializa las listas
        latitudes.add(zmg_min_lat)
        for (i in 0 until 13) {
            latitudes.add(latitudes.last()+0.018)
        }
        longitudes.add(zmg_min_lon)
        for (i in 0 until 16) {
            longitudes.add(longitudes.last()+0.018)
        }
    }

    public fun getLongitudes() : ArrayList<Double>{
        return this.longitudes
    }

    public fun getLatitudes() : ArrayList<Double>{
        return this.latitudes
    }

    public fun distanciaEuclidiana(puntoA: Pair<Double, Double>, puntoB: Pair<Double, Double>): Double {
        val difx = puntoA.first - puntoB.first
        val dify = puntoA.second - puntoB.second

        return sqrt(difx.pow(2) + dify.pow(2))
    }

    private fun getQuadrants(intersection: Pair<Double, Double>) : ArrayList<String>{
        val cuadrantes : ArrayList<String> = ArrayList()

        cuadrantes.add(intersection.first.toString()+ " : " +intersection.second.toString())

        if(intersection.first > zmg_min_lon){
            cuadrantes.add((intersection.first-0.018).toString()+ " : " +intersection.second.toString())
        }
        if(intersection.second > zmg_min_lat){
            cuadrantes.add(intersection.first.toString()+ " : " +(intersection.second-0.018).toString())
        }
        if (intersection.first > zmg_min_lon && intersection.second > zmg_min_lat){
            cuadrantes.add((intersection.first-0.018).toString()+ " : " +(intersection.second-0.018).toString())
        }

        return cuadrantes
    }
        //busqueda binaria tuneada
    private fun binaryAllocation(array :ArrayList<Double>, element  : Double):Double{
        var inicio = 0
        var fin = array.size - 2

        while (inicio <= fin) {
            val medio = inicio + (fin - inicio) / 2

            // Si el elemento está entre 2 puntos del mapa en el eje correspondiente, devuelve el punto de inicio
            if ((array[medio] < element && array[medio+1] > element) || array[medio] == element) {
                return array[medio]
            }

            // Si el elemento es mayor que el valor del medio, buscar en la mitad derecha del array
            if (array[medio] < element) {
                inicio = medio + 1
            }
            // Si el elemento es menor que el valor del medio, buscar en la mitad izquierda del array
            else {
                fin = medio - 1
            }
        }

        // Si el elemento no se encuentra en el array, devuelve -1
        return 0.0
    }

    public fun allocateQuadrant(longitude : Double, latitude : Double) : String{
        var quadrant = ""

        val quadrant_lon = binaryAllocation(this.longitudes, longitude)
        if (quadrant_lon == 0.0){
            return ""
        }
        quadrant += quadrant_lon.toString() + " : "

        val quadrant_lat = binaryAllocation(this.latitudes, latitude)
        if(quadrant_lat == 0.0){
            return ""
        }

        quadrant += quadrant_lat.toString()

        return quadrant
    }


    public fun allocateIntersection(longitude : Double, latitude : Double) : ArrayList<String>{
        val quadrant_lat = binaryAllocation(this.latitudes, latitude)
        val quadrant_lon = binaryAllocation(this.longitudes, longitude)

        val distance_1 = distanciaEuclidiana(Pair(longitude,latitude), Pair(quadrant_lon,quadrant_lat))
        val distance_2 = distanciaEuclidiana(Pair(longitude,latitude), Pair(quadrant_lon+0.018,quadrant_lat))
        val distance_3 = distanciaEuclidiana(Pair(longitude,latitude), Pair(quadrant_lon,quadrant_lat+0.018))
        val distance_4 = distanciaEuclidiana(Pair(longitude,latitude), Pair(quadrant_lon+0.018,quadrant_lat+0.018))

        val min1 = min(distance_1,distance_2)
        val min2 = min(distance_3,distance_4)
        val min_real = min(min1,min2)

        val intersection = when(min_real){
            distance_1 -> Pair(quadrant_lon,quadrant_lat)
            distance_2 -> Pair(quadrant_lon+0.018,quadrant_lat)
            distance_3 -> Pair(quadrant_lon,quadrant_lat+0.018)
            else -> Pair(quadrant_lon+0.018,quadrant_lat+0.018)
        }

        return getQuadrants(intersection)

    }
    }
/*
    fun printers(){

        println("\nLatitudes :")
        latitudes.forEach(){
            print(it.toString() + ", ")
        }
        println("\nLongitudes :")
        longitudes.forEach(){
            print(it.toString() + ", ")
        }


        print("\n")
        println(allocateQuadrant(-103.300,20.590))
        print("\n")
        println(allocateQuadrant(-103.199, 20.690))
        print("\n")
        println(allocateQuadrant(-103.445, 20.566))
        print("\n")
        println(allocateQuadrant(-103.45000001, 20.57700001))
        print("\n")
        println(allocateQuadrant(-103.45000001, 20.558999999))
        print("Asignaciones: \n")

        print("\n")
        println(allocateIntersection(-103.486+0.019, 20.541))
        print("\n")
        println(allocateIntersection(-103.45000001, 20.558999999))

    }
}

fun main(){
    val manager = CoordinatesManager()
    manager.printers()
}
*/