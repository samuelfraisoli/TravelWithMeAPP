package com.example.travelwithmeapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.travelwithmeapp.activities.MainActivity
import com.example.travelwithmeapp.databinding.FragmentPreferenciasBinding
import com.example.travelwithmeapp.utils.FirebaseAuthManager
import com.example.travelwithmeapp.utils.FirebaseFirestoreManager
import com.example.travelwithmeapp.utils.Utilities

/**
 * Fragment for managing user preferences.
 *
 * @author Javier Cuesta
 */

class PreferenciasFragment : Fragment(){

    private lateinit var binding: FragmentPreferenciasBinding
    private lateinit var utilities: Utilities
    private lateinit var firebaseAuthManager: FirebaseAuthManager
    private lateinit var firebaseFirestoreManager: FirebaseFirestoreManager

    private var uid: String = ""

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
        firebaseAuthManager = FirebaseAuthManager(requireContext())
        firebaseFirestoreManager = FirebaseFirestoreManager(requireContext(), binding.root)
        utilities = Utilities()
        utilities.crearToolbarFragmSecundario(binding.toolbar.toolbarLayout, "Preferencias", binding.toolbar.toolbarLayoutTitle, activity as AppCompatActivity)

        binding.cambiarContrasena.setOnClickListener {
           cambiarContrasena()
        }
    }




    fun cambiarContrasena() {
        recogerUidActMain()
        if(uid.isEmpty()) {
            utilities.mostrarAlertaDialog("Error al procesar la solicitud", requireContext())
            return
        }
        firebaseFirestoreManager.recogerDatosUsuario(uid) {user ->
            if(user != null && user.email != null) {
                firebaseAuthManager.enviarMailRestablecerContraseña(user.email, binding.root) {}
            }
            else {
                utilities.mostrarAlertaDialog("Error al procesar la solicitud", requireContext())
            }
        }
    }

    fun recogerUidActMain() {
        if(activity != null && activity is MainActivity) {
            uid = (activity as MainActivity).user.uid
        }
    }
}

