package com.example.travelwithmeapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.FragmentPlanificarBinding
import com.example.travelwithmeapp.databinding.FragmentPreferenciasBinding
import com.example.travelwithmeapp.utils.Utilities

class PreferenciasFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentPreferenciasBinding
    private lateinit var utilities: Utilities

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPreferenciasBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    fun inicializar() {
        utilities = Utilities()
        binding.botonLimpiar.setOnClickListener(this)
        utilities.crearToolbarFragmSecundario(binding.toolbar.toolbarLayout, "Preferencias", binding.toolbar.toolbarLayoutTitle, activity as AppCompatActivity)

    }

    //TODO rellenar con métodos del backend (autenticación)
    override fun onClick(v: View?) {
        when (v?.id) {
            binding.botonGuardar.id -> {
                fun cambiarContraseña(){
                    // Función Samu
                }
            }
            binding.botonLimpiar.id -> {
                limpiar()
            }
        }
    }

    fun limpiar() {
        binding.contraseA.setText("")
        binding.contraseA2.setText("")
        binding.restContraseA.setText("")
        binding.restContraseA2.setText("")
    }
}