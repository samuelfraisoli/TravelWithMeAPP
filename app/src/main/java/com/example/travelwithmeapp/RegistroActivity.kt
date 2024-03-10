package com.example.travelwithmeapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.travelwithmeapp.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth

class RegistroActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var email: Editable
    private lateinit var password: Editable

    private lateinit var binding: ActivityRegistroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_registro)

        inicializar()


    }

    fun inicializar() {
        email = binding.correo.text
        password = binding.contraseA.text
        binding.botonLimpiar.setOnClickListener(this)
        binding.botonRegistrar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            binding.botonLimpiar.id->{limpiar() }
            binding.botonRegistrar.id -> { registrarConCorreo() }
        }
    }

    //todo hacer que guarde todos los datos (por ahora solo guarda correo y contraseña)
    fun registrarConCorreo() {
        if(email.isNotEmpty() && password.isNotEmpty()) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.toString(), password.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        IntentAMainAct(it.result?.user?.email ?: "", ProviderType.BASIC)
                    }

                    else {
                        mostrarAlertaDialog(it.exception?.message ?:"Error")
                    }
                }
        }
    }

    fun limpiar() {
        binding.nombre.setText("")
        binding.apellidos.setText("")
        binding.fechaNacimiento.setText("")
        binding.telefono.setText("")
        binding.correo.setText("")
        binding.contraseA.setText("")
        binding.confirmacionContraseA.setText("")
        binding.terminos.isChecked = false

    }

    fun IntentAMainAct(email: String, provider: ProviderType) {
        var intent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(intent)
    }

    fun mostrarAlertaDialog(datos: String) {
        AlertDialog.Builder(this)
            .setTitle("Error de autenticacion")
            .setMessage(datos)
            .setPositiveButton("Aceptar", null)
            .create()
            .show()
    }
}