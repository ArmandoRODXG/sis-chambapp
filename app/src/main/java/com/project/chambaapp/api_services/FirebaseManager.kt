package com.project.chambaapp.api_services

import android.content.ContentValues.TAG
import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions

class FirebaseManager (){
    private val db = FirebaseFirestore.getInstance()

    private val collectionName = "user_locations"

    companion object { //atributos estaticos, se comparten en todas las instancias de esta clase
        var last_quadrant: String = ""
        var last_jobs: List<Long> = listOf(0,0,0)
    }

    fun updateQuadrant (context: Context, current_location: Location) {

        //Acceso a los trabajos del trabajador por patron singleton
        val worker_jobs = WorkerSingleton.getWorkerJobs(context).toList()
        val worker_id = WorkerSingleton.getWorkerId(context)

        val data = hashMapOf(
            "current_location" to GeoPoint(current_location.latitude, current_location.longitude),
            "updated_at" to FieldValue.serverTimestamp()
        )

        val cordManager = CoordinatesManager() //inicializa gestor de coordenadas
        val quadrant = cordManager.allocateQuadrant(current_location.longitude, current_location.latitude)

        if (quadrant.isBlank()){ //si está fuera de la zmg, no sube nada a Firestore
            Log.e("FirebaseManager", "No se registra por managaer")
            return
        }

        if( quadrant != last_quadrant){ //si el nuevo cuadrante es diferente, se añade al map
            data["quadrant"] = quadrant
            last_quadrant = quadrant
        }

        if(worker_jobs.toSet() != last_jobs.toSet()){ //si el array es diferente, se añade al map
            data["jobs"] = worker_jobs
            last_jobs = worker_jobs
        }

        val documentRef = db.collection(collectionName).document(worker_id.toString())
        //crea o actualiza el registro6
        documentRef.set(data, SetOptions.merge())
            .addOnSuccessListener  { Log.d("FirebaseManager", "Se registra por managaer") }
            . addOnFailureListener {  Log.e("FirebaseManager", "No se registra por managaer") }
    }

    fun searchWorkers (latitude : Double, longitude : Double, job_needed : Long ) : Task<QuerySnapshot> {
        val cordManager = CoordinatesManager() //inicializa gestor de coordenadas
        val quadrantsForSearch = cordManager.allocateIntersection(longitude,latitude)

        val query = db.collection(collectionName)
            .whereIn("quadrant", quadrantsForSearch)
            .whereArrayContains("jobs", job_needed)

        return query.get()
    }

    fun getRealtimeLocation(worker_id : Long, listener: FirestoreDataListener){

        db.collection(collectionName).document(worker_id.toString()).addSnapshotListener{ snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val data =
                    listener.onDataReceived()
            } else {
                Log.d(TAG, "Current data: null")
            }
        }
    }

        //al desloggearse el trabajador puede eliminar su instancia en firestore
    fun deleteLocation (worker_id : Long,
                        onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val documentRef = db.collection(collectionName).document(worker_id.toString())

        documentRef.delete()
            . addOnSuccessListener  { onSuccess.invoke() }
            . addOnFailureListener { onFailure.invoke(it) }
    }
}

interface FirestoreDataListener {
    fun onDataReceived()
}