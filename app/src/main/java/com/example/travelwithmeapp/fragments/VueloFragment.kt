package com.example.travelwithmeapp.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelwithmeapp.adapters.TrayectosAdapter
import com.example.travelwithmeapp.databinding.FragmentVueloBinding
import com.example.travelwithmeapp.models.Vuelo
import com.example.travelwithmeapp.utils.TravelWithMeApiManager
import com.example.travelwithmeapp.utils.Utilities

/**
 * Fragment to display details of a flight including duration, stops, and luggage information.
 *
 * @author Samuel Fraisoli
 */

class VueloFragment : Fragment() {
    private lateinit var binding: FragmentVueloBinding
    private lateinit var vuelo: Vuelo
    private lateinit var recyclerView: RecyclerView
    private lateinit var adaptadorRecycler: TrayectosAdapter
    private lateinit var utilities: Utilities


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVueloBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun inicializar() {
        recogerIntent()
        configurarRecycler()
        utilities = Utilities()
        utilities.crearToolbarFragmSecundario(binding.toolbar.toolbarLayout, "Tu vuelo a ${vuelo.getAeropuertoDestino().ciudad}", binding.toolbar.toolbarLayoutTitle, activity as AppCompatActivity)
        binding.duracion.text = vuelo.getDuracionTotalFormatoHHhMMm()
        binding.escalas.text = numeroEscalas(vuelo)
        binding.escalas2.text = numeroEscalas(vuelo)
        binding.tipoVuelo2.text = vuelo.tipo
        binding.tipoVuelo.text = vuelo.tipo
        binding.precio.text = "${utilities.quitarDecimalesSiAcabaEnCero(vuelo.precio)}€"
        binding.equipajeDescipcion.alto.text =  "${vuelo.equipaje.alto} cm"
        binding.equipajeDescipcion.ancho.text =  "${vuelo.equipaje.ancho} cm"
        binding.equipajeDescipcion.peso.text = "${vuelo.equipaje.peso} kg"
    }

    fun recogerIntent() {
        val bundle = arguments
        if (bundle != null) {
            vuelo = bundle.getSerializable("vuelo") as Vuelo
            Log.v("bundle", "$vuelo")
        }

    }

    fun numeroEscalas(vuelo: Vuelo) : String {
        if(vuelo.trayectos.size == 0 || vuelo.trayectos.size == 1) {
            return "Sin escalas"
        }
        else if(vuelo.trayectos.size == 2) {
            return "1 escala"
        }
        else {
            return "${vuelo.trayectos.size} escalas"
        }
    }

    fun configurarRecycler() {
        recyclerView = binding.trayectos
        adaptadorRecycler = TrayectosAdapter(vuelo.trayectos)
        recyclerView.adapter = adaptadorRecycler
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }






}