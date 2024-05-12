package com.example.travelwithmeapp.fragments


import android.content.Intent

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.travelwithmeapp.activities.MainActivity
import com.example.travelwithmeapp.databinding.FragmentRegistroBinding
import com.example.travelwithmeapp.models.ProviderType
import com.example.travelwithmeapp.models.User
import com.example.travelwithmeapp.utils.FirebaseAuthManager
import com.example.travelwithmeapp.utils.FirebaseFirestoreManager
import com.example.travelwithmeapp.utils.Utilities
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


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
        firebaseFirestoreManager = FirebaseFirestoreManager(requireContext(), binding.root)

        binding.botonLimpiar.setOnClickListener(this)
        binding.botonRegistrar.setOnClickListener(this)
        binding.fechaNacimiento.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                utilities.lanzarDatePicker(childFragmentManager, binding.fechaNacimiento)
            }
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            binding.botonLimpiar.id -> {
                limpiar()
            }

            binding.botonRegistrar.id -> {
                //como realizar registro es una función con una corrutina, hay que agregar el contexto de corrutina.
                //En este caso lifecycleScope, genera un contexto de corrutina mientras el fragment sigue activo
                lifecycleScope.launch {
                realizarRegistro() }

            }
        }
    }

    /**
     * Función que llama a las demás funciones para realizar el registro
     * La función tiene una corrutina suspend, para que la función se suspenda cuando se están recuperando los datos de la bd.
     * - Primero comprueba si los datos son correctos. Si lo son, crea un objeto usuario sin uid. Registra ese usuario en la db y devuelve la uid que firebase le asigna, y la añade al objeto usuario. Luego lo sube a la base de datos, y hace un intent a activity main
     * */
    suspend fun realizarRegistro() {
        if (verificarDatosCorrectos()) {
            crearUsuarioSinUid()
            var uid = registrarUsuarioConCorreo()
            Log.v("uid realizarRegistro()", "${uid}")
            if(uid != null) {
                user.uid = uid
                Log.v("user con uid", "${user.toString()}")
                subirUsuarioADB()
                IntentAMainAct(user)
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
            utilities.mostrarAlertaDialog("Por favor complete todos los campos.", requireContext())
            return false
        }
        if (binding.contraseA.text.equals(binding.confirmacionContraseA.text)) {
            utilities.mostrarAlertaDialog("Las contraseñas no coinciden", requireContext())
            return false
        }
        if (!binding.terminos.isChecked) {
            utilities.mostrarAlertaDialog("Por favor acepte los términos y condiciones.", requireContext())
            return false
        }
        return true
    }

    /**
     * Crea el usuario pero sin uid, porque no la conseguirá hasta que no registre al usuario en firebase auth
     */
    fun crearUsuarioSinUid() {
        user = User(
            uid = "",
            email = binding.correo.text.toString(),
            provider = ProviderType.BASIC,
            name = binding.nombre.text.toString(),
            surname = binding.apellidos.text.toString(),
            birthdate = binding.fechaNacimiento.text.toString(),
            telephone = binding.telefono.text.toString()
        )
        Log.v("user", "${user.toString()}")
    }

    /**
     * Registra al usuario con correo en firebase. Si el hay un error, devolverá null. Si se realiza correctamente, devolverá la uid.
     * Utiliza una corrutina que permite suspender la ejecución de la función hasta que se recupere el dato de la bd. Si no, podría ser que el dato fuera nulo
     */
    suspend fun registrarUsuarioConCorreo(): String? = suspendCoroutine { continuation ->
            firebaseAuthManager.registrarConCorreo(user.email, binding.contraseA.text.toString()) { user ->
                continuation.resume(user.uid)
            }
        }



    /**
     * Función que recoge el usuario y lo sube a la base de datos
     * Llama a la función guardarDatosUsuario, a la que le pasa un callback, dependiendo de
     * si esa función devuelve true o false, se determina si esta devuelve true or false.
     * Eso determina si se han podido subir los datos a la DB correctamente o no*/
    suspend fun subirUsuarioADB(): Boolean {
        return suspendCancellableCoroutine { continuation ->
            firebaseFirestoreManager.crearDocumentoUsuario(user) {respuesta ->
                if(respuesta) {
                    firebaseFirestoreManager.crearUsuario(user) {respuesta ->
                        if(respuesta) {
                            continuation.resume(respuesta == true)
                        }
                    }
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

    fun IntentAMainAct(user: User) {
        Log.v("funcion", "IntentAMainAct()")
        var intent = Intent(requireContext(), MainActivity::class.java).apply {
            putExtra("user", user)
        }
        startActivity(intent)
    }


}