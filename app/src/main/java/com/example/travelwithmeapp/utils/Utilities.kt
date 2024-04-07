package com.example.travelwithmeapp.utils

import android.app.DatePickerDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import java.util.Locale

class Utilities {

    /** Primero crea el objeto fechaSeleccionada, que almacenará la fecha que el usuario seleccione
     * (por ahora se inicializa con el valor del día actual para que no tenga valor null)
     * Luego usa la clase Calendar para coger el año, mes y día actuales.
     * Luego crea un datePickerDialog, un diálogo con un calendario para seleccionar una fecha
     * Inicializa ese calendario con el año, mes y día que había recogido
     * Le añade un OnDateSetListener, que escucha cuándo el usuario selecciona una fecha
     * Guarda el año, mes y día que selecciona en la variable fechaSeleccionada
     * Finalmente retorna la fecha que ha seleccionado el usuario en el dialog
     */
    fun lanzarDatePickerDialog(view: View, context: Context): Calendar {
        cerrarTeclado(view, context)
        var fechaSeleccionada = Calendar.getInstance()
        var calendar = Calendar.getInstance()
        var ano = calendar.get(Calendar.YEAR)
        var mes = calendar.get(Calendar.MONTH)
        var dia = calendar.get(Calendar.DAY_OF_MONTH)

        var datePickerDialog = DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { _, anoSeleccionado, mesSeleccionado, diaSeleccionado ->
                fechaSeleccionada = Calendar.getInstance()
                fechaSeleccionada.set(anoSeleccionado, mesSeleccionado, diaSeleccionado)
                view as EditText
                view.setText(
                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                        fechaSeleccionada.time
                    )
                )
            },
            ano,
            mes,
            dia
        )
        datePickerDialog.show()
        return fechaSeleccionada
    }

    private fun cerrarTeclado(view: View, context: Context) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }


    fun lanzarSnackBarCorto(mensaje: String, view: View) {
        val snackbar = Snackbar.make(view, "Mensaje corto", Snackbar.LENGTH_SHORT)
        snackbar.show()
    }
}