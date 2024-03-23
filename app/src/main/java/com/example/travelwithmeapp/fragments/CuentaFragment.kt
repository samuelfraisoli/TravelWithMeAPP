package com.example.travelwithmeapp.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.travelwithmeapp.LoginActivity
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.FragmentCuentaBinding
import com.google.firebase.auth.FirebaseAuth


class CuentaFragment : Fragment() {
        private lateinit var binding: FragmentCuentaBinding

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentCuentaBinding.inflate(inflater, container, false)

            binding.cerrarSesion.setOnClickListener { cerrarSesion() }

            return binding.root
        }


    //borra los datos guardados en sharedpreferences, te deslogea de firebase y te devuelve a la pantalla de login
    fun cerrarSesion() {
        var sharedPrefEditor = requireContext().getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        sharedPrefEditor.clear()
        sharedPrefEditor.apply()

        FirebaseAuth.getInstance().signOut()

        val intentALogin = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intentALogin)
        }
    }