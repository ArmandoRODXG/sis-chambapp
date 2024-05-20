package com.project.chambaapp.activities.UserViews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.GeoPoint
import com.project.chambaapp.R
import com.project.chambaapp.api_services.CoordinatesManager
import com.project.chambaapp.api_services.FirebaseManager
import com.project.chambaapp.api_services.LocationTriggeredAPI
import com.project.chambaapp.api_services.UserLocation
import com.project.chambaapp.api_services.WorkerLocation
import com.project.chambaapp.data.Entities.ContratistaItem
import com.project.chambaapp.data.RetrofitClient
import com.project.chambaapp.data.Services.ContratistasService
import com.project.chambaapp.data.Services.GPSSearchRequest
import com.project.chambaapp.data.Services.OficioRequest
import com.project.chambaapp.databinding.ActivitySearchBinding
import com.project.getexample.JobAdapter
import retrofit2.Call
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var rvMain : RecyclerView
    private lateinit var myAdapter: JobAdapter
    private lateinit var service: ContratistasService
    private lateinit var serviceGPS: ContratistasService
    private lateinit var locationTriggered: LocationTriggeredAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvMain = binding.recyclerJobList
        rvMain.layoutManager = LinearLayoutManager(this)

        locationTriggered = LocationTriggeredAPI(this)

        var selectedItem: String? = null
        var selectedFilter: String? = null

        val items = listOf(
            "Albañil","Plomero","Carpintero",
            "Pintor","Fontanero","Jardinero",
            "Instalador de Pisos","Techador","Vidriero",
            "Cerrajero","Limpieza y mantenimiento"
        )

        val filters = listOf(
            "Ninguna",
            "Más de 3 Estrellas",
            "Menos de 3 Estrellas",
            "Solo Disponibles",
            "Solo Verificados"
        )

        val ddAdapter = ArrayAdapter(this, R.layout.list_job_select, items)
        binding.dropdownFieldSearchjob.setAdapter(ddAdapter)

        val eeAdapter = ArrayAdapter(this, R.layout.list_job_select, filters)
        binding.dropdownFieldFilter.setAdapter(eeAdapter)

        binding.dropdownFieldSearchjob.setOnItemClickListener { parent, view, position, id ->
            selectedItem = parent.getItemAtPosition(position).toString()
            Log.d("Trabajo seleccionado", "$selectedItem")
        }

        binding.dropdownFieldFilter.setOnItemClickListener { parent, view, position, id ->
            selectedFilter = parent.getItemAtPosition(position).toString()
            Log.d("Trabajo seleccionado", "$selectedItem")
        }

        service = initRetrofitService()
        serviceGPS = initRetrofitServiceGPS()

        myAdapter = JobAdapter(baseContext, emptyList()) { contratista ->
            val intent = Intent(this@SearchActivity, ViewProfileJobActivity::class.java).apply {
                putExtra("nombre", contratista.nombre)
                putExtra("usuario", contratista.usuario)
                putExtra("id",contratista.id)
                putExtra("LoggedUser", intent.getStringExtra("LoggedUser"))
            }
            startActivity(intent)
        }
        rvMain.adapter = myAdapter

        binding.buttonSearch.setOnClickListener {
            if (binding.switch1.isChecked){
                getLocationAndProcess(selectedFilter!!)
            } else {
                initRecyclerView(selectedItem!!, selectedFilter!!)
            }
        }
    }

    private fun initRetrofitService(): ContratistasService {
//        val baseUrl = "http:///buscar_contratista/"
        val baseUrl = "http://is-chambapp-5bf6977200ac.herokuapp.com/buscar_contratista/"

        return RetrofitClient.createService(baseUrl)
    }

    private fun initRetrofitServiceGPS(): ContratistasService {
//        val baseUrl = "http:///buscar_contratista/"
        val baseUrl = "http://is-chambapp-5bf6977200ac.herokuapp.com/buscar_contratista_por_gps/"

        return RetrofitClient.createService(baseUrl)
    }

    private fun initRecyclerView(selectedItem: String, selectedFilter: String) {
        val idUsuario = intent.getStringExtra("LoggedUser")
        val request = OficioRequest(selectedItem)
        val retroData = service.buscarContratista(request)

        retroData.enqueue(object : retrofit2.Callback<List<ContratistaItem>> {
            override fun onResponse(
                call: Call<List<ContratistaItem>>,
                response: Response<List<ContratistaItem>>
            ) {
                val data = response.body() ?: emptyList()

                myAdapter.updateData(data)

                Log.d("data", data.toString())
            }

            override fun onFailure(call: Call<List<ContratistaItem>>, t: Throwable) {
                //
            }
        })
    }

    private fun initRecyclerViewGPS(set: String, sortedWorkers: List<WorkerLocation>,selectedFilter: String) {
        val request = GPSSearchRequest(set)

        println(request)

        val retroData = serviceGPS.buscarContratistaPorGPS(request)

        println(retroData)

        retroData.enqueue(object : retrofit2.Callback<List<ContratistaItem>> {
            override fun onResponse(
                call: Call<List<ContratistaItem>>,
                response: Response<List<ContratistaItem>>
            ) {
                Log.d("Churros", response.toString())
                var data = response.body() ?: emptyList()

                data = mergeAndFilter(data, sortedWorkers, selectedFilter)

                Log.d("Churros", data.toString())
                myAdapter.updateData(data)
            }

            override fun onFailure(call: Call<List<ContratistaItem>>, t: Throwable) {
                Log.d("Churros", "xd call: $call  excepción: $t")
            }
        })
    }

    private fun getLocationAndProcess(selectedFilter: String) {
        val id = intent.getStringExtra("LoggedUser")?.toLong() ?: return

        locationTriggered.getUserLocation(id) { userLocation ->
            userLocation?.let {
                processLocationBasedSearch(it, selectedFilter)
            } ?: run {
                Log.d("MainActivity", "No se pudo obtener la ubicación.")
            }
        }
    }

    private fun processLocationBasedSearch(it: UserLocation, selectedFilter: String) {
        val latitude = it.latitude
        val longitude = it.longitude
        val selected_job = 2L

        if (latitude != null && longitude != null){
            val bd = FirebaseManager()

            bd.searchWorkers(latitude, longitude, selected_job)
                .addOnSuccessListener { documents ->
                    val userLocationGeoPoint = GeoPoint(latitude, longitude)
                    val workersRetrieved = mutableListOf<WorkerLocation>()
                    for (document in documents){
                        val documentGeoPoint = document.getGeoPoint("current_location") ?: continue
                        workersRetrieved.add(
                            WorkerLocation(
                                CoordinatesManager.distanceBetweenGeoPoints(userLocationGeoPoint,documentGeoPoint),
                                document.id.toLong(),
                                document.getLong("state")?.toInt()
                            )
                        )
                    }

                    //Ordenamos los trabajadores segun su distancia respecto al usuario de menor a mayor
                    val sortedWorkers = workersRetrieved.sortedBy { it.distance_from_user }

                    println("Recibe set de trabajadores $sortedWorkers")

                    val workerIds = sortedWorkers.map { it.worker_id.toString() }
                    val workerIdsString = workerIds.joinToString(separator = ",")

                    //Ahora si lo mandamos al recycler
                    initRecyclerViewGPS(workerIdsString ,sortedWorkers, selectedFilter)

                }
                .addOnFailureListener { exception ->
                    Log.e("MainActivity", "No se validan los permisos")
                }
        }

        Log.d("MainActivity", "User ID: ${it.user_id}, Lat: $latitude, Lon: $longitude")
    }

    private fun mergeAndFilter(listWorkers: List<ContratistaItem>, listFirebase: List<WorkerLocation>, selectedFilter: String) : List<ContratistaItem>{
        val combinedList = listWorkers.zip(listFirebase) { a, b ->
            ContratistaItem(a.id, a.nombre, a.apellidos,a.usuario, a.contrasena, a.email, a.estado, a.presentacion_texto, a.numero_celular, a.codigo_postal, a.direccion, a.rating, a.verificado, a.oficios,
                b.distance_from_user, b.worker_state)
        }

        return  combinedList
    }

    override fun onResume() {
        super.onResume()
        if (myAdapter.itemCount > 0) {
            myAdapter.clearData()
//            initRecyclerView()
        }
    }
}
