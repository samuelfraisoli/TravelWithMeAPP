package com.example.travelwithmeapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.ActivityMainBinding

import com.example.travelwithmeapp.models.User
import com.example.travelwithmeapp.utils.FirebaseFirestoreManager
import com.example.travelwithmeapp.utils.FirebaseAuthManager

/**
 * Main activity of the application
 *
 * @author Samuel Fraisolí
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuthManager: FirebaseAuthManager
    private lateinit var firebaseFirestoreManager: FirebaseFirestoreManager
    var user = User()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inicializar()
        recogerIntent()
        //recogerDatosUsuario()
        guardarSesion()
        iniciarBottomNavView()
    }

    fun inicializar() {
        firebaseAuthManager = FirebaseAuthManager(this)
        firebaseFirestoreManager = FirebaseFirestoreManager(this, binding.root)
    }

    /**
     * Retrieves user data from an Intent
     */
    fun recogerIntent() {
        val bundle = intent.extras
        user = bundle?.getSerializable("user") as? User ?: User()
    }

    fun guardarSesion() {
        firebaseAuthManager.guardarSesion(user)
    }

    /**
     * Starts the bottom navbar and links it with the navController
     */
    fun iniciarBottomNavView() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer_act_main) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}

