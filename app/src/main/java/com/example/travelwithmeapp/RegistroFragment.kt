package com.example.travelwithmeapp

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.travelwithmeapp.databinding.FragmentRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale


class RegistroFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentRegistroBinding

    private lateinit var nombre: String
    private lateinit var apellidos: String
    private lateinit var fechaNacimiento: String
    private lateinit var telefono: String
    private lateinit var email: String
    private lateinit var password: String



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
        binding.botonLimpiar.setOnClickListener(this)
        binding.botonRegistrar.setOnClickListener(this)

        binding.fechaNacimiento.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                cerrarTeclado(view)
                lanzarDatePickerDialog(view)
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            binding.botonLimpiar.id->{limpiar() }
            binding.botonRegistrar.id -> { realizarRegistro() }
        }
    }

    /**
     * Función que llama a las demás funciones para realizar el registro
     * - Primero comprueba si los datos son correctos, y si sí lo son, llamará a las otras funciones*/
    fun realizarRegistro() {
        if (verificarDatosCorrectos()) {
            recogerDatosYSubirlosADB()
            registrarConCorreo()
        }
    }


    /**
     * Función que verifica si los datos se han introducido correctamente */
    fun verificarDatosCorrectos() : Boolean {
        if (binding.nombre.text.isEmpty() ||
            binding.apellidos.text.isEmpty() ||
            binding.fechaNacimiento.text.isEmpty() ||
            binding.telefono.text.isEmpty() ||
            binding.correo.text.isEmpty() ||
            binding.contraseA.text.isEmpty() ||
            binding.confirmacionContraseA.text.isEmpty()
           ) {
            mostrarAlertaDialog("Por favor complete todos los campos.")
            return false
        }
        if(binding.contraseA.text.equals(binding.confirmacionContraseA.text)) {
            mostrarAlertaDialog("Las contraseñas no coinciden")
            return false
        }
        if (!binding.terminos.isChecked) {
            mostrarAlertaDialog("Por favor acepte los términos y condiciones.")
            return false
        }
        return true
    }

    //todo completar
    /**
     * Función que recoge los datos introducidos y los sube a la base de datos */
    fun recogerDatosYSubirlosADB() {
        nombre = binding.nombre.text.toString()
        apellidos = binding.nombre.text.toString()
        fechaNacimiento = binding.fechaNacimiento.text.toString()
        telefono = binding.telefono.text.toString()
        email = binding.correo.text.toString()
        password = binding.contraseA.toString()

        //recoge los datos de los usuarios y los sube a la BD

    }




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


    /**
     * Lanza un diálogo con un selector de fecha */
    fun lanzarDatePickerDialog(view: View) : Calendar {
        var fechaSeleccionada = Calendar.getInstance()
        var calendar = Calendar.getInstance()
        var ano = calendar.get(Calendar.YEAR)
        var mes = calendar.get(Calendar.MONTH)
        var dia = calendar.get(Calendar.DAY_OF_MONTH)

        var datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, anoSeleccionado, mesSeleccionado, diaSeleccionado ->
                fechaSeleccionada = Calendar.getInstance()
                fechaSeleccionada.set(anoSeleccionado, mesSeleccionado, diaSeleccionado)
                view as EditText
                view.setText(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(fechaSeleccionada.time))
            },
            ano,
            mes,
            dia
        )
        datePickerDialog.show()
        return fechaSeleccionada
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
        var intent = Intent(requireContext(), MainActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(intent)
    }

    /**
     * Muestra un diálogo de alerta con los datos necesarios */
    fun mostrarAlertaDialog(datos: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error de autenticacion")
            .setMessage(datos)
            .setPositiveButton("Aceptar", null)
            .create()
            .show()
    }

    private fun cerrarTeclado(view: View) {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }



}