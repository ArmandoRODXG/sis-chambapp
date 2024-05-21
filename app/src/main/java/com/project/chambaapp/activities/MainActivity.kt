package com.project.chambaapp.activities

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.project.chambaapp.api_services.LocationServicesManager
import com.project.chambaapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usuario = "usuario"
        val contratista = "contratista"

        binding.cardViewUsuario.setOnClickListener {
            val intent = Intent(this@MainActivity,SignUpActivityM::class.java).apply {
                putExtra("TypeUser",usuario)
            }
            startActivity(intent)
        }

        binding.cardViewContratista.setOnClickListener {
            val intent = Intent(this@MainActivity,SignUpActivityM::class.java).apply {
                putExtra("TypeUser",contratista)
            }
            startActivity(intent)
        }
    }

}

/*
Trabajadores Dummies

User("Juan", "Pérez", "López", "juanpl", "juan.perez.lopez@testmail.com", "password1", "44100", "3312345678", "Albañil, Plomero, Carpintero. Me encanta trabajar con mis manos, construir y reparar cosas."),
        User("Carlos", "González", "Martínez", "carlosgm", "carlos.gonzalez.martinez@testmail.com", "password2", "44330", "3334567890", "Pintor, Fontanero, Jardinero. Disfruto pintar espacios y trabajar en la jardinería, creando paisajes hermosos."),
        User("Miguel", "Ramírez", "Sánchez", "miguelrs", "miguel.ramirez.sanchez@testmail.com", "password3", "44990", "3356789012", "Instalador de Pisos, Techador, Vidriero. Me apasiona trabajar con materiales y crear estructuras y diseños únicos."),
        User("Luis", "Fernández", "Torres", "luisft", "luis.fernandez.torres@testmail.com", "password4", "45050", "3378901234", "Cerrajero, Limpieza y mantenimiento. Soy meticuloso y me gusta asegurarme de que todo esté en perfecto estado y funcionamiento."),
        User("José", "Hernández", "Díaz", "josehd", "jose.hernandez.diaz@testmail.com", "password5", "45670", "3389012345", "Albañil, Plomero, Carpintero. Construyo y arreglo hogares, siempre buscando la mejor solución para mis clientes."),
        User("Javier", "García", "Méndez", "javimg", "javier.garcia.mendez@testmail.com", "password6", "45220", "3301234567", "Pintor, Fontanero, Jardinero. Transformo espacios con colores y detalles, creando ambientes acogedores."),
        User("Raúl", "Rodríguez", "Vargas", "raulrv", "raul.rodriguez.vargas@testmail.com", "password7", "45650", "3323456789", "Instalador de Pisos, Techador, Albañil. Mi pasión es trabajar con cristales y crear estructuras impresionantes."),
        User("Manuel", "Martín", "Gómez", "manuelmg", "manuel.martin.gomez@testmail.com", "password8", "45080", "3345678901", "Cerrajero, Limpieza y mantenimiento, Vidriero. Me dedico a mantener espacios seguros y limpios para todos."),
        User("Francisco", "Jiménez", "Cruz", "franciscojc", "francisco.jimenez.cruz@testmail.com", "password9", "45110", "3367890123", "Albañil, Plomero, Carpintero. Contribuyo al desarrollo y mantenimiento de comunidades."),
        User("Antonio", "Morales", "Ramos", "antoniomr", "antonio.morales.ramos@testmail.com", "password10", "45540", "3390123456", "Pintor, Fontanero, Jardinero. Me dedico a embellecer espacios y mejorar la calidad de vida de las personas.")


 */