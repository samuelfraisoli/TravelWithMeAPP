package com.example.travelwithmeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.travelwithmeapp.databinding.ActivityRegistroBinding

class RegistroActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegistroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_registro)

        binding.botonLimpiar.setOnClickListener(this)
        binding.botonRegistrar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            binding.botonLimpiar.id->{
                binding.nombre.setText("")
                binding.apellidos.setText("")
                binding.fechaNacimiento.setText("")
                binding.telefono.setText("")
                binding.correo.setText("")
                binding.contraseA.setText("")
                binding.confirmacionContraseA.setText("")
                binding.terminos.isChecked = false
            }
        }
    }
}