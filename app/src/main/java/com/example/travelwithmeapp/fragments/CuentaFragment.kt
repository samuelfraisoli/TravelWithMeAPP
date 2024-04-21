package com.example.travelwithmeapp.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.travelwithmeapp.activities.LoginActivity
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.FragmentCuentaBinding
import com.example.travelwithmeapp.utils.FirebaseAuthManager
import com.google.firebase.auth.FirebaseAuth


class CuentaFragment : Fragment() {
    private lateinit var binding: FragmentCuentaBinding
    private lateinit var firebaseAuthManager: FirebaseAuthManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCuentaBinding.inflate(inflater, container, false)

        inicializar()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textPerfil.setOnClickListener{
            findNavController().navigate(R.id.action_cuentaFragment_to_perfilFragment)
        }
        binding.textPreferencias.setOnClickListener{
            findNavController().navigate(R.id.action_cuentaFragment_to_preferenciasFragment)
        }
    }

    fun inicializar() {
        firebaseAuthManager = FirebaseAuthManager(requireContext())
        binding.cerrarSesion.setOnClickListener { cerrarSesion() }
    }

    fun cerrarSesion() {
        firebaseAuthManager.cerrarSesion()

        val intentALogin = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intentALogin)
    }
}