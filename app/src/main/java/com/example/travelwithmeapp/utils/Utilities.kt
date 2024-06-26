package com.example.travelwithmeapp.utils

import android.content.Context

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.travelwithmeapp.R
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

/**
 * A collection of utility functions for various tasks such as managing date pickers, creating toolbars,
 * displaying snackbar messages, handling dialogs, formatting dates, and manipulating prices.
 *
 * @author Samuel Fraisoli
 */

class Utilities {

    // =============================================================================================
    // ELEMENTOS VISUALES
    // =============================================================================================

    // DATEPICKER

    /**
     * Crea un datepicker que deja elegir dos fechas con un rango entre ellas
     */
    fun lanzarDatePickerRango(fragmentManager: FragmentManager, editTextFechaInicio: EditText, editTextFechaFin: EditText) {
        //constraints que limitan al calendario a no poder elegir días que sean anteriores al actual
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())
                //pongo que el calendario semanal empiece en lunes
                .setFirstDayOfWeek(Calendar.MONDAY)


        //calendario
        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("")
                //añado las constraints
                .setCalendarConstraints(constraintsBuilder.build())
                .setTheme(R.style.MaterialDatePicker)
                .build()

        //listener que recoge las fechas, las convierte a String y las pega en los edittext
        dateRangePicker.addOnPositiveButtonClickListener { seleccion ->
            val startDate = seleccion.first
            val endDate = seleccion.second
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val startDateString = dateFormat.format(Date(startDate))
            val endDateString = dateFormat.format(Date(endDate))
            editTextFechaInicio.setText(startDateString)
            editTextFechaFin.setText(endDateString)
        }

        dateRangePicker.show(fragmentManager, "date_picker")

    }


    /**
     * Crea un datepicker que deja elegir una fecha sin limitaciones
     */
    fun lanzarDatePicker(fragmentManager: FragmentManager, editText: EditText) {

        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setFirstDayOfWeek(Calendar.MONDAY)

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("")
                .setTheme(R.style.MaterialDatePicker)
                .setCalendarConstraints(constraintsBuilder.build())
                .build()

        datePicker.addOnPositiveButtonClickListener { seleccion ->
            val date = seleccion
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dateString = dateFormat.format(Date(date))

            editText.setText(dateString)
        }

        datePicker.show(fragmentManager, "date_picker")
    }

    /**
     * Crea un datepicker que deja elegir una sola fecha pero no permite que sea anterior al dia actual
     */
    fun lanzarDatePickerConstraintHoy(fragmentManager: FragmentManager, editText: EditText) {

        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())
                //pongo que el calendario semanal empiece en lunes
                .setFirstDayOfWeek(Calendar.MONDAY)

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("")
                .setTheme(R.style.MaterialDatePicker)
                .setCalendarConstraints(constraintsBuilder.build())
                .build()

        datePicker.addOnPositiveButtonClickListener { seleccion ->
            val date = seleccion
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dateString = dateFormat.format(Date(date))

            editText.setText(dateString)
        }

        datePicker.show(fragmentManager, "date_picker")
    }


    // TOOLBARS

    fun crearToolbarFragmSecundario(toolbar: Toolbar, titulo: String, textView: TextView, activity: FragmentActivity) {
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

    fun crearToolbarMenuPrincipal(toolbar: Toolbar, titulo: String, textView: TextView, activity: FragmentActivity) {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val actionBar = (activity).supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
        textView.text = titulo
        toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }
    }

    // SNACKBARS Y DIALOGS

    fun lanzarSnackBarCorto(mensaje: String, view: View) {
        val snackbar = Snackbar.make(view, mensaje, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }

    fun mostrarAlertaDialog(datos: String, context: Context) {
        AlertDialog.Builder(context)
            .setTitle("Error de autenticacion")
            .setMessage(datos)
            .setPositiveButton("Aceptar", null)
            .create()
            .show()
    }

    // =============================================================================================
    // FECHAS
    // =============================================================================================
    fun formatearOffsetDateTimeDDMMMM(offsetDateTime: OffsetDateTime): String {
        // Crear un DateTimeFormatter con el patrón "dd MMMM" y el Locale español
        val formatter = DateTimeFormatter.ofPattern("dd MMMM").withLocale(Locale.getDefault())
        return offsetDateTime.format(formatter)
    }

    fun formatearOffsetDateTimeDDMMYYYY(offsetDateTime: OffsetDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM YYYY").withLocale(Locale.getDefault())
        return offsetDateTime.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatoOffsetDateTimeHHMM(offsetDateTime: OffsetDateTime): String {
        val formatoHora = DateTimeFormatter.ofPattern("HH:mm")
        return offsetDateTime.format(formatoHora)
    }

    /**
     * Solo sirve para formatos de fecha DD/MM/YYYY !!!
     */
    fun parseStringAOffsetDateDDMMYYYY(string: String) : OffsetDateTime {
        // Obtener la zona horaria predeterminada del sistema
        val zoneId = ZoneId.systemDefault()
        // Obtener el OffsetDateTime actual con la zona horaria predeterminada del sistema
        val offsetDateTime = OffsetDateTime.now(zoneId)
        // Obtener el offset (desplazamiento) del OffsetDateTime
        val offset = offsetDateTime.offset

        // Crear un DateTimeFormatter con el formato "dd/MM/yyyy"
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        // Parsear la cadena a LocalDate usando el formatter
        val localDate = LocalDate.parse(string, formatter)

        // Combinar el el localdate(tiene dias, meses, años), localtime (tiene hora y minutos), y offset (el UTC)
        return OffsetDateTime.of(localDate, LocalTime.of(7, 0), offset)
    }

    /**
     * Solo parsea formatos ISO (ej. "2024-02-01T00:00:00Z"). Son los que vienen de la bd
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun parseStringISOAOffsetDateTime(string: String) : OffsetDateTime {
        // Crear un formateador de fecha y hora con el patrón adecuado
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

        // Parsear la cadena a OffsetDateTime utilizando el formateador
        val offsetDateTime = OffsetDateTime.parse(string, formatter)

        return offsetDateTime
    }

    fun parseStringISOTaLocalDate(string: String): LocalDate {
        // Crear un formateador de fecha con el patrón adecuado (solo fecha, sin hora)
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE

        // Parsear la cadena a LocalDate utilizando el formateador
        return LocalDate.parse(string, formatter)
    }

    fun horaEstaEntre(
        hora: LocalTime,
        horaInicio: LocalTime,
        horaFin: LocalTime
    ): Boolean {
        return !hora.isBefore(horaInicio) && !hora.isAfter(horaFin)
    }

    // =============================================================================================
    // QUITAR DECIMALES A LOS PRECIOS
    // =============================================================================================


    fun quitarDecimalesSiAcabaEnCero(double: Double): String {
        if (double % 1 == 0.0) {
            return double.toInt().toString()
        } else {
            return double.toString()
        }
    }
 }