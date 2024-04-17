package com.example.travelwithmeapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.travelwithmeapp.activities.MainActivity
import com.example.travelwithmeapp.databinding.FragmentPerfilBinding
import com.example.travelwithmeapp.utils.FirebaseFirestoreManager
import com.example.travelwithmeapp.utils.Utilities


class PerfilFragment : Fragment() {
    private lateinit var binding: FragmentPerfilBinding
    private lateinit var firebaseFirestoreManager: FirebaseFirestoreManager
    private lateinit var utilities: Utilities

    private lateinit var uid: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilBinding.inflate(inflater, container, false)

        inicializar()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    fun inicializar() {
        firebaseFirestoreManager = FirebaseFirestoreManager(requireContext(), binding.root)
        utilities = Utilities()

        recogerUidActMain()
        recogerDatosUsuario()
        utilities.crearToolbarFragmSecundario(binding.toolbar.toolbarLayout, "Información personal", binding.toolbar.toolbarLayoutTitle, activity as AppCompatActivity)


    }


    /**
     * Accede a activity main, que es donde está guardado el usuario que ha iniciado sesión, y coge su uid. Con ella puede realizar operaciones en la bd
     */

    fun recogerUidActMain() {
        if(activity != null && activity is MainActivity) {
            uid = (activity as MainActivity).user.uid
        }
    }


    /**
     * Recoge los datos del usuario de la base de datos
     * Si los coge, actualiza el User creado
     * Si no los recoge, hace un intent a la pantalla de Login para que el usuario se vuelva a logear
     */
    fun recogerDatosUsuario() {
        firebaseFirestoreManager.recogerDatosUsuario(uid) {
                userRecogido ->
            if(userRecogido != null) {
                binding.nombre.text = userRecogido.name
                binding.apellido.text = userRecogido.surname
                binding.correo.text = userRecogido.email
                binding.fechaNacimiento.text = userRecogido.birthdate
            }
        }
    }
}