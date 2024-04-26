package com.example.travelwithmeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.adapters.CarouselAdapter
import com.example.travelwithmeapp.databinding.FragmentExplorarBinding
import com.example.travelwithmeapp.utils.MockData
import com.google.android.material.carousel.CarouselSnapHelper

class ExplorarFragment : Fragment() {
    private var mockData = MockData()
    private lateinit var binding: FragmentExplorarBinding
    private var listaImagenes: ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExplorarBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Pulsacion para el botón explorar hoteles
        binding.botonExplorarHoteles.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("eleccion", 2)
            findNavController().navigate(R.id.action_explorarFragment_to_buscarFragment, bundle)
        }

        binding.botonExplorarVuelos.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("eleccion", 1)
            findNavController().navigate(R.id.action_explorarFragment_to_buscarFragment, bundle)
        }

        binding.botonForos.setOnClickListener {
            findNavController().navigate(R.id.action_explorarFragment_to_resenaFragment)
        }

        binding.botonExplorarCosasQueHacer.setOnClickListener {
            findNavController().navigate(R.id.action_explorarFragment_to_cosasQueHacerFragment)
        }

        rellenarListaImagenes()
        inicializarCarouselRecyclerView()
    }

    private fun rellenarListaImagenes() {
        //todo cargar con datos de hoteles de la API
        listaImagenes = mockData.listaFotosHoteles()


    }

    private fun inicializarCarouselRecyclerView() {
        binding.carouselRecyclerView.adapter = CarouselAdapter(listaImagenes)
        var snaphelper = CarouselSnapHelper().attachToRecyclerView(binding.carouselRecyclerView)
    }


}
