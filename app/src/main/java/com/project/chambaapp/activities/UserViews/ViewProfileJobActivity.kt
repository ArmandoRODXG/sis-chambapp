package com.project.chambaapp.activities.UserViews

import android.content.Intent
import android.health.connect.datatypes.ExerciseRoute.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ListenerRegistration
import com.project.chambaapp.activities.WorkerViews.ReviewActivity
import com.project.chambaapp.api_services.CoordinatesManager
import com.project.chambaapp.api_services.LocationTriggeredAPI
import com.project.chambaapp.api_services.UserLocation
import com.project.chambaapp.databinding.ActivityViewProfileJobBinding


class ViewProfileJobActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewProfileJobBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var documentListener: ListenerRegistration
    private var idUsuario : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewProfileJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombre = intent.getStringExtra("nombre")
        val usuario = intent.getStringExtra("usuario")
        val id = intent.getStringExtra("id")

        idUsuario = intent.getStringExtra("LoggedUser")?.toLong()!!
        val rating_value = intent.getFloatExtra("rating_bar", 0.0f)

        binding.tvProfileViewJobUsername.text = nombre.toString()
        binding.tvProfileViewJobUser.text = usuario.toString()
        binding.rbProfileViewJob.rating = rating_value
        binding.tvProfileViewJobZona.text = intent.getStringExtra("worker_area")
        binding.tvProfileViewJobInfomation.text = intent.getStringExtra("worker_description")
        binding.tvProfileViewJobTelephon.text = intent.getStringExtra("worker_telephone")

        setFirebaseChannels(id.toString())
        
        binding.buttonFormReview.setOnClickListener {
            val intent = Intent(this@ViewProfileJobActivity, ReviewActivity::class.java).apply {
                putExtra("nombre", nombre)
                putExtra("usuario", usuario)
                putExtra("id", id)
                putExtra("LoggedUser", idUsuario)
            }
            startActivity(intent)
        }

        binding.buttonViewReviews.setOnClickListener {
            val intent = Intent(this@ViewProfileJobActivity, ViewReviewsWorker::class.java).apply {
                putExtra("nombre", nombre)
                putExtra("usuario", usuario)
                putExtra("id", id)
                putExtra("LoggedUser", idUsuario)
            }
            startActivity(intent)
        }

        binding.buttonViewJobs.setOnClickListener {
            val intent = Intent(this@ViewProfileJobActivity,ViewJobsWorkerActivity::class.java).apply {
                putExtra("id",id)
            }
            startActivity(intent)
        }

        binding.buttonReportWorker.setOnClickListener {
//            val numeroTelefono = "+523322485961" // Remplaza con el número real
//            val mensajePrecompuesto = "Hola, me pongo en contacto a través de ChabApp y estoy interesado en contratar sus servicios." // Personaliza el mensaje
//
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$numeroTelefono&text=$mensajePrecompuesto")
//            try {
//                startActivity(intent)
//            } catch (e: ActivityNotFoundException) {
//                // WhatsApp no está instalado, informa al usuario
//                Toast.makeText(this, "No tienes instalado WhatsApp en tu dispositivo.", Toast.LENGTH_SHORT).show()
//            }

            val url = "https://t.me/SuperMaistroBot" // URL del bot de Telegram
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    private fun setFirebaseChannels(worker_id: String){
        firestore = FirebaseFirestore.getInstance()
        val documentReference = firestore.collection("user_locations").document(worker_id)

        // Escucha en tiempo real los cambios en el documento desde Firebase Firestore
        documentListener = documentReference.addSnapshotListener { snapshot, e ->
            if (e != null) {
                println("Error al escuchar cambios en Firestore: $e")
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                snapshot.getGeoPoint("current_location")?.let {workerGeoPoint ->
                    val api = LocationTriggeredAPI(this@ViewProfileJobActivity)

                    api.getUserLocation(idUsuario){ userLocation ->
                        userLocation?.let {userLocation ->
                            val userGeoPoint = GeoPoint(userLocation.latitude ?: 0.0, userLocation.longitude ?: 0.0)
                            val distancia = CoordinatesManager.distanceBetweenGeoPoints(workerGeoPoint,userGeoPoint)
                            binding.distanciaField.text = "A " + CoordinatesManager.distanceCategory(distancia) + " de tu ubicación actual"
                        } ?: run {
                            Log.d("MainActivity", "No se pudo obtener la ubicación.")
                            binding.distanciaField.text = "Distancia Desconocida"
                        }
                    }
                }

                snapshot.getLong("state")?.let {
                    binding.disponibilidadField.text = getAvailabilityCode(it)
                }

                val numbers = snapshot.get("jobs") as? List<Long>
                if (numbers != null) {
                    var jobs = ""
                    for (number in numbers) {
                        jobs += occupationTranslator(number)+"\n"
                    }

                    binding.tvProfileViewJobOficios.text = jobs
                } else {
                    println("No se encontró el array de números en el documento")
                }

            } else {
                binding.distanciaField.text = "Distancia Desconocida"
                binding.disponibilidadField.text = "Disponibilidad Desconocida"
                binding.tvProfileViewJobOficios.text = "Oficios Desconocidos"
            }


        }
    }

    private fun occupationTranslator(id_oficio: Long): String{
        return when(id_oficio){
            1L-> "Albañil"
            2L-> "Plomero"
            3L-> "Carpintero"
            4L-> "Pintor"
            5L-> "Fontanero"
            6L-> "Jardinero"
            7L-> "Instalador de Pisos"
            8L-> "Techador"
            9L-> "Vidriero"
            10L-> "Cerrajero"
            11L-> "Limpieza y mantenimiento"
            else -> "Oficio Desconocido"
        }
    }

    fun getAvailabilityCode(code: Long) : String{
        return when(code){
            0L-> "No Disponible"
            1L-> "Disponible"
            2L-> "En Servicio"
            else-> "En Servicio y Disponible"
        }
    }

}