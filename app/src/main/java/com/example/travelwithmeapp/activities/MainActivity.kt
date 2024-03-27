package com.example.travelwithmeapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.ActivityMainBinding
import com.example.travelwithmeapp.models.ProviderType
import com.example.travelwithmeapp.models.User
import com.example.travelwithmeapp.utils.FirebaseFirestoreManager
import com.example.travelwithmeapp.utils.FirebaseAuthManager


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var user = User()


    private lateinit var firebaseAuthManager: FirebaseAuthManager
    private lateinit var firebaseFirestoreManager: FirebaseFirestoreManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        inicializar()

        recogerIntent()
        recogerDatosUsuario()
        guardarSesion()
        iniciarBottonNavView()
    }

    fun inicializar() {
        firebaseAuthManager = FirebaseAuthManager(this)
        firebaseFirestoreManager = FirebaseFirestoreManager(this)
    }

    /**
     * Recoge datos del intent
     * Actualiza la variable User (variable global que representa al usuario en esta actividad)
     * con los datos que ha recogido del intent
     */
    fun recogerIntent() {
        val bundle = intent.extras
        user.uid = bundle?.getString("uid") ?: ""
        user.email = bundle?.getString("email") ?: ""
        var provider = bundle?.getString("provider") ?: ""
        user.provider = ProviderType.valueOf(provider)
    }

    /**
     * Recoge los datos del usuario de la base de datos
     * Si los coge, actualiza el User creado
     * Si no los recoge, hace un intent a la pantalla de Login para que el usuario se vuelva a logear
     */
    fun recogerDatosUsuario() {
       firebaseFirestoreManager.recogerDatosUsuario(user.uid) {
           userRecogido ->
           if(userRecogido != null) {
               user = userRecogido
           }
           else {
               var intent = Intent(this, LoginActivity::class.java)
           }
       }
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

