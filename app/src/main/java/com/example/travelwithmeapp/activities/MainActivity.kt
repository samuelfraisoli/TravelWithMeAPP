package com.example.travelwithmeapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
    var user = User()


    private lateinit var firebaseAuthManager: FirebaseAuthManager
    private lateinit var firebaseFirestoreManager: FirebaseFirestoreManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        inicializar()

        recogerIntent()
        //recogerDatosUsuario()
        guardarSesion()
        iniciarBottonNavView()
    }

    fun inicializar() {
        firebaseAuthManager = FirebaseAuthManager(this)
        firebaseFirestoreManager = FirebaseFirestoreManager(this, binding.root)
    }

    /**
     * Recoge datos del intent
     * Actualiza la variable User (variable global que representa al usuario en esta actividad)
     * con los datos que ha recogido del intent
     */
    fun recogerIntent() {
        val bundle = intent.extras
        user = bundle?.getSerializable("user") as? User ?: User()
    }

    fun guardarSesion() {
        firebaseAuthManager.guardarSesion(user)
    }

    /**
     * Inicia la barra de navegación inferior y la enlaza con el navController
     */
    fun iniciarBottonNavView() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer_act_main) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}

