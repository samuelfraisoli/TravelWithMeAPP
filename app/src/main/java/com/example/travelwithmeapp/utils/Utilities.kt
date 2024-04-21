package com.example.travelwithmeapp.utils

import android.app.DatePickerDialog
import android.content.Context

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.FragmentActivity
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.LayoutToolbarFragmentSecundarioBinding
import com.google.android.material.snackbar.Snackbar
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.time.Duration

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
            context, R.style.ThemeOverlay_App_Dialog,
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

    fun crearToolbarFragmSecundario(
        toolbar: Toolbar,
        titulo: String,
        textView: TextView,
        activity: FragmentActivity
    ) {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val actionBar = (activity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        var drawable = ResourcesCompat.getDrawable(activity.resources, R.drawable.back_icon, null)
        drawable = DrawableCompat.wrap(drawable!!);
        DrawableCompat.setTint(drawable, activity.resources.getColor(R.color.cyan_app));
        actionBar?.setHomeAsUpIndicator(R.drawable.back_icon)

        actionBar?.setDisplayShowTitleEnabled(false)
        textView.text = titulo
        toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }
    }

    fun crearToolbarMenuPrincipal(
        toolbar: Toolbar,
        titulo: String,
        textView: TextView,
        activity: FragmentActivity
    ) {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val actionBar = (activity).supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
        textView.text = titulo
        toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }
    }


    fun lanzarSnackBarCorto(mensaje: String, view: View) {
        val snackbar = Snackbar.make(view, mensaje, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }


    fun formatoDateDDMM(fecha: Date): String {
        val formato = SimpleDateFormat("dd-MM")
        val fechaFormateada = formato.format(fecha)
        return fechaFormateada
    }


    fun formatoDateDDMMMM(fecha: Date, locale: Locale): String {
        val dayFormat = SimpleDateFormat("d", locale)
        val monthFormat = SimpleDateFormat("MMMM", locale)

        val day = dayFormat.format(fecha)
        val month = monthFormat.format(fecha)

        return "$day $month"
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun formatoDateHHMM(fecha: Date): String {
        val fechaLocalTime = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
        val formatoHora = DateTimeFormatter.ofPattern("HH:mm")
        val fechaFormateada = fechaLocalTime.format(formatoHora)
        return fechaFormateada
    }

    fun formatoDurationHHhMMm(duracion: Duration): String {
        var stringHora = ""
        duracion.toComponents { h, m, s, ns ->
            var horas = h.toInt()
            var minutos = m
            stringHora = "${horas}h ${minutos}m"
        }
        return stringHora
    }

    fun parseStringADateDDMMYYYYconBarras(string: String): Date {
        val format = SimpleDateFormat("dd/MM/yyyy")
        val date = format.parse(string)
        return date
    }


}