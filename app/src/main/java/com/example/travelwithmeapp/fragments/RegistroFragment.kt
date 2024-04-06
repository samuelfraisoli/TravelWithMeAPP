package com.example.travelwithmeapp.fragments


import android.content.Intent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.travelwithmeapp.activities.MainActivity
import com.example.travelwithmeapp.databinding.FragmentRegistroBinding
import com.example.travelwithmeapp.models.User
import com.example.travelwithmeapp.utils.FirebaseAuthManager
import com.example.travelwithmeapp.utils.FirebaseFirestoreManager
import com.example.travelwithmeapp.utils.Utilities


class RegistroFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentRegistroBinding

    private lateinit var firebaseAuthManager: FirebaseAuthManager
    private lateinit var firebaseFirestoreManager: FirebaseFirestoreManager
    private var utilities = Utilities()

    private lateinit var user: User


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistroBinding.inflate(inflater, container, false)

        inicializar()

        return binding.root
    }

    fun inicializar() {
        firebaseAuthManager = FirebaseAuthManager(requireContext())
        firebaseFirestoreManager = FirebaseFirestoreManager(requireContext())

        binding.botonLimpiar.setOnClickListener(this)
        binding.botonRegistrar.setOnClickListener(this)
        binding.fechaNacimiento.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                utilities.lanzarDatePickerDialog(view, requireContext())
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.botonLimpiar.id -> {
                limpiar()
            }

            binding.botonRegistrar.id -> {
                realizarRegistro()
            }
        }
    }

    /**
     * Función que llama a las demás funciones para realizar el registro
     * - Primero comprueba si los datos son correctos, y si sí lo son, llamará a las otras funciones*/
    fun realizarRegistro() {
        if (verificarDatosCorrectos()) {
            var correo = binding.correo.toString()
            var password = binding.contraseA.toString()
            firebaseAuthManager.registrarConCorreo(correo, password) { userRegistrado ->
                if (userRegistrado != null) {
                    user = userRegistrado
                    if (recogerDatosYSubirlosADB(user)) {
                        IntentAMainAct(user)
                    }
                }
            }
        }
    }

    /**
     * Función que verifica si los datos se han introducido correctamente */
    fun verificarDatosCorrectos(): Boolean {
        if (binding.nombre.text.isEmpty() ||
            binding.apellidos.text.isEmpty() ||
            binding.fechaNacimiento.text.isEmpty() ||
            binding.telefono.text.isEmpty() ||
            binding.correo.text.isEmpty() ||
            binding.contraseA.text.isEmpty() ||
            binding.confirmacionContraseA.text.isEmpty()
        ) {
            firebaseAuthManager.mostrarAlertaDialog("Por favor complete todos los campos.")
            return false
        }
        if (binding.contraseA.text.equals(binding.confirmacionContraseA.text)) {
            firebaseAuthManager.mostrarAlertaDialog("Las contraseñas no coinciden")
            return false
        }
        if (!binding.terminos.isChecked) {
            firebaseAuthManager.mostrarAlertaDialog("Por favor acepte los términos y condiciones.")
            return false
        }
        return true
    }


    /**
     * Función que recoge los datos introducidos y los sube a la base de datos
     * Llama a la función guardarDatosUsuario, a la que le pasa un callback, dependiendo de
     * si esa función devuelve true o false, se determina si esta devuelve true or false.
     * Eso determina si se han podido subir los datos a la DB correctamente o no*/
    fun recogerDatosYSubirlosADB(user: User): Boolean {
        user.name = binding.nombre.text.toString()
        user.surname = binding.nombre.text.toString()
        user.birthdate = binding.fechaNacimiento.text.toString()
        user.telephone = binding.telefono.text.toString()

        var boolean = false
        firebaseFirestoreManager.guardarDatosUsuario(user) { booleanCallback ->
            if (booleanCallback == true) {
                boolean = true
            } else {
                boolean = false
            }
        }

        return boolean
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

    fun IntentAMainAct(user: User) {
        var intent = Intent(requireContext(), MainActivity::class.java).apply {
            putExtra("user", user)
        }
        startActivity(intent)
    }


}