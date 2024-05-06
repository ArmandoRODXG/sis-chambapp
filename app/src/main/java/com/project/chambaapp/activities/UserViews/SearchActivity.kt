package com.project.chambaapp.activities.UserViews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.project.chambaapp.R
import com.project.chambaapp.adapter.JostListAdapter
import com.project.chambaapp.data.database.DBPrueba
import com.project.chambaapp.data.database.Entities.Contratista
import com.project.chambaapp.data.database.Entities.Usuario
import com.project.chambaapp.data.ui.JobList
import com.project.chambaapp.data.ui.JobListProvider
import com.project.chambaapp.databinding.ActivitySearchBinding
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    var lista_contratistas: MutableList<Contratista> = mutableListOf()
    lateinit var room: DBPrueba

    lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
    }

    fun initRecyclerView(){
        binding.recyberJobList.layoutManager = LinearLayoutManager(this)

        room = Room.databaseBuilder(this,DBPrueba::class.java,"ChambaaaaaApp").build()
        obtenerContratistas(room)

        //binding.recyberJobList.adapter = JostListAdapter(JobListProvider.jobLists)
    }

    fun obtenerContratistas(room: DBPrueba) {
        lifecycleScope.launch {
            lista_contratistas = room.daoContratista().obtenerContratistas()
            binding.recyberJobList.adapter = JostListAdapter(convertirAJobList(lista_contratistas))
        }
    }

    fun convertirAJobList(contratistas: List<Contratista>): List<JobList> {
        val jobLists = mutableListOf<JobList>()
        contratistas.forEach { contratista ->
            val jobList = JobList(
                contratista.nombre,
                contratista.username,
                "https://cursokotlin.com/wp-content/uploads/2017/07/batman.jpg"
            )
            jobLists.add(jobList)
        }
        return jobLists
    }
}
