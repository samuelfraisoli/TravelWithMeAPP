package com.example.travelwithmeapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.adapters.VuelosAdapter
import com.example.travelwithmeapp.databinding.FragmentBuscarVuelosBinding
import com.example.travelwithmeapp.models.Vuelo
import com.example.travelwithmeapp.utils.MockData
import com.example.travelwithmeapp.utils.Utilities
import java.util.Date


class BuscarVuelosFragment : Fragment() {
    private lateinit var binding: FragmentBuscarVuelosBinding
    private lateinit var fechaVuelo: Date
    private lateinit var recyclerView: RecyclerView

    private var utilities = Utilities()
    private var mockdata = MockData()

    private lateinit var adaptadorRecycler: VuelosAdapter
    private var listaVuelos = ArrayList<Vuelo>()

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
        listaVuelos = mockdata.listaPruebaVuelos()
        configurarRecycler()
        utilities.crearToolbarFragmSecundario(binding.toolbar.toolbarLayout, "vuelos", binding.toolbar.toolbarLayoutTitle, activity as AppCompatActivity)
    }

    fun configurarRecycler() {
        adaptadorRecycler = VuelosAdapter(listaVuelos) {
            vuelo -> cambiarFragment(vuelo)
        }
        recyclerView = binding.recyclerBusquedaFrag
        recyclerView.adapter = adaptadorRecycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    fun buscarVuelos() {
        //todo rellenar
    }

    fun cambiarFragment(vuelo: Vuelo) {
        val bundle = Bundle()
        bundle.putSerializable("vuelo", vuelo)
        findNavController()?.navigate(R.id.action_buscarVuelosFragment_to_vueloFragment)
    }


}

