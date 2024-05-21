package com.project.chambaapp.activities.UserViews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.chambaapp.R
import com.project.chambaapp.databinding.ActivityProfileUserBinding

class ProfileUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usuario = intent.getStringExtra("usuario")
        Log.d("data",usuario.toString())




        val bottomNavigationView: BottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeUser -> {
                    startActivity(Intent(this@ProfileUserActivity,SearchActivity::class.java).apply {
                        putExtra("LoggedUser",intent.getStringExtra("LoggedUser"))
                    })
                    true
                }
                R.id.profileUser -> {

                    true

                }

                else -> false
            }
        }
    }
}
