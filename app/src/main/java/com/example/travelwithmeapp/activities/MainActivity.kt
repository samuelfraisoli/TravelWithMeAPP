package com.example.travelwithmeapp.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.ActivityMainBinding

enum class ProviderType {
    BASIC,
    GOOGLE
}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPrefEditor: SharedPreferences.Editor
    private lateinit var email: String
    private lateinit var provider: String
    //private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        recogerIntent()
        abrirEditorSharedPref()
        iniciarBottonNavView()
    }

    //Recoge datos del intent
    // - Si no existieran mete un string vacío (pero siempre van a existir en esta pantalla)
    fun recogerIntent() {
        //todo añaddir ID usuario
        val bundle = intent.extras
        email = bundle?.getString("email") ?: ""
        provider = bundle?.getString("provider") ?: ""
        //uid = bundle?.getString("uid") ?: ""
    }

    //Guarda los datos del usuario en un archivo txt en su teléfono
    //- La ruta del archivo está guardado en la carpeta strings, accedemos a él en modo privado
   fun abrirEditorSharedPref() {
       sharedPrefEditor = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
       sharedPrefEditor.putString("email", email)
       sharedPrefEditor.putString("provider", provider)
       //sharedPrefEditor.putString("uid", uid)
       sharedPrefEditor.apply()
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

