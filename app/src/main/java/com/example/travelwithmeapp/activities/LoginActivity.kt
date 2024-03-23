package com.example.travelwithmeapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var email: Editable
    private lateinit var password: Editable

    private val CODIGO_GOOGLE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        comprobarSesion()


    }

    fun comprobarSesion() {
        val sharedPreferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", null)
        val provider = sharedPreferences.getString("provider", null)

        //todo hacer que pase el uid también
        if(email != null && provider != null) {
            intentAMainAct(email, ProviderType.valueOf(provider))
        }
    }


    fun intentAMainAct(email: String, provider: ProviderType) {
        var intent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(intent)
    }
}