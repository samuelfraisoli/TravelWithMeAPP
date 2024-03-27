package com.example.travelwithmeapp.fragments

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelwithmeapp.databinding.FragmentBuscarVuelosBinding
import java.util.Date
import java.util.Locale


class BuscarVuelosFragment : Fragment() {
    private lateinit var binding: FragmentBuscarVuelosBinding
    private lateinit var textviewOrigen: TextView
    private lateinit var textviewDestino: TextView
    private lateinit var fechaButton: Button
    private lateinit var fechaVuelo: Date
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuscarVuelosBinding.inflate(inflater, container, false)

        inicializar()

        return binding.root
    }

    fun inicializar() {



    }

    fun configurarRecycler() {




    }

    fun buscarVuelos() {
        //todo rellenar
    }


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
                view as Button
                view.setText(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(fechaSeleccionada.time))
            },
            ano,
            mes,
            dia
        )
        datePickerDialog.show()
        return fechaSeleccionada
    }


}

