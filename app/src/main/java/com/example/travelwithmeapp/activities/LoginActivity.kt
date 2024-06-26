package com.example.travelwithmeapp.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import com.example.travelwithmeapp.databinding.ActivityLoginBinding
import com.example.travelwithmeapp.models.User
import com.example.travelwithmeapp.utils.FirebaseAuthManager

/**
 * Activity for handling user login
 *
 * @author Samuel Fraisolí
 */

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuthManager: FirebaseAuthManager

    private val CODIGO_GOOGLE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inicializar()
        comprobarSesion()
    }

    fun inicializar() {
        firebaseAuthManager = FirebaseAuthManager(this)

    }

    fun comprobarSesion() {
        val user = firebaseAuthManager.comprobarSesion()
        if(user != null) {
            intentAMainAct(user)
        }
    }


    fun intentAMainAct(user: User) {
        var intent = Intent(this, MainActivity::class.java).apply {
            putExtra("user", user)
        }
        startActivity(intent)
    }
}