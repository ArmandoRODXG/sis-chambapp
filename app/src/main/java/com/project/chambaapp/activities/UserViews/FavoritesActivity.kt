package com.project.chambaapp.activities.UserViews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.chambaapp.R
import com.project.chambaapp.adapter.FavoriteAdapter
import com.project.chambaapp.adapter.ReviewAdapter
import com.project.chambaapp.data.Entities.FavoritoItem
import com.project.chambaapp.data.RetrofitClient
import com.project.chambaapp.data.Services.UsuariosService
import com.project.chambaapp.databinding.ActivityFavoritesBinding
import retrofit2.Call
import retrofit2.Response

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var service: UsuariosService
    private lateinit var rvMain : RecyclerView
    private lateinit var myAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvMain = binding.recyclerFavoritesList
        rvMain.layoutManager = LinearLayoutManager(this)

        service = initRetrofitService()

        cargarFavoritos()

    }

    private fun initRetrofitService(): UsuariosService {
//        val baseUrl = "http://192.168.1.4:5000/favoritos/"
        val baseUrl = "https://is-chambapp-5bf6977200ac.herokuapp.com/favoritos/"

        return RetrofitClient.createService(baseUrl)
    }

        private fun cargarFavoritos(){
        val idUsuario = intent.getStringExtra("LoggedUser")?.toIntOrNull()
        Log.d("idUsuario",idUsuario.toString())

        if (idUsuario != null) {
            val retroData = service.obtenerFavoritos(idUsuario)

            retroData.enqueue(object : retrofit2.Callback<List<FavoritoItem>>{
                override fun onResponse(
                    call: Call<List<FavoritoItem>>,
                    response: Response<List<FavoritoItem>>
                ) {
                    if(response.isSuccessful){
                        val data = response.body() ?: emptyList()
                        Log.d("data", data.toString())
                        myAdapter = FavoriteAdapter(this@FavoritesActivity, data)
                        rvMain.adapter = myAdapter
                    }else {
                        // Manejar el caso en el que la respuesta no es exitosa
                        Toast.makeText(this@FavoritesActivity, "Error: ${response.message()}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<List<FavoritoItem>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}