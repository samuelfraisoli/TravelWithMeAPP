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
import com.example.travelwithmeapp.adapters.CarouselExplorarAdapter
import com.example.travelwithmeapp.databinding.FragmentExplorarBinding
import com.example.travelwithmeapp.models.Hotel
import com.example.travelwithmeapp.utils.MockData
import com.google.android.material.carousel.CarouselSnapHelper

class ExplorarFragment : Fragment() {
    private var mockData = MockData()
    private lateinit var binding: FragmentExplorarBinding
    private var listaHotelesDestacados: ArrayList<Hotel> = ArrayList()

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

        binding.botonExplorarHoteles.setOnClickListener {
            intentABuscarFragParteHoteles()
        }
        binding.botonExplorarVuelos.setOnClickListener {
            intentABuscarFragParteVuelos()
        }
        binding.botonVerMapa.setOnClickListener {
            intentAMapaPuntosInteresFrag()
        }

        rellenarListaImagenes()
        inicializarCarouselRecyclerView()
    }

    //CAROUSEL
    private fun rellenarListaImagenes() {
        //todo cambiar por datos de la api
        listaHotelesDestacados = mockData.listaPruebaHoteles()
    }

    private fun inicializarCarouselRecyclerView() {
        binding.carouselRecyclerView.adapter = CarouselExplorarAdapter(listaHotelesDestacados) {
                hotel -> intentAHotelFrag(hotel)
        }
        var snaphelper = CarouselSnapHelper().attachToRecyclerView(binding.carouselRecyclerView)
    }

    //INTENTS
    fun intentAHotelFrag(hotel: Hotel) {
        val bundle = Bundle()
        bundle.putSerializable("hotel", hotel)
        findNavController()?.navigate(R.id.action_explorarFragment_to_hotelFragment, bundle)
    }

    fun intentABuscarFragParteHoteles() {
        val bundle = Bundle()
        bundle.putInt("eleccion", 2)
        findNavController().navigate(R.id.action_explorarFragment_to_buscarFragment, bundle)

    }

    fun intentABuscarFragParteVuelos() {
        val bundle = Bundle()
        bundle.putInt("eleccion", 1)
        findNavController().navigate(R.id.action_explorarFragment_to_buscarFragment, bundle)
    }

    fun intentAMapaPuntosInteresFrag() {
        findNavController().navigate(R.id.action_explorarFragment_to_mapaPuntosInteresFragment)

    }


}
