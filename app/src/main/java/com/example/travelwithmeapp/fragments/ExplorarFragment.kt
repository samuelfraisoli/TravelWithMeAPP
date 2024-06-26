package com.example.travelwithmeapp.fragments

import android.os.Bundle
import android.util.Log
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
import com.example.travelwithmeapp.utils.TravelWithMeApiManager
import com.example.travelwithmeapp.utils.Utilities
import com.google.android.material.carousel.CarouselSnapHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Fragment that shows the main fragment of the app
 *
 * @author Matías Martínez
 */


class ExplorarFragment : Fragment() {
    private var mockData = MockData()
    private lateinit var binding: FragmentExplorarBinding
    private lateinit var travelWithMeApiManager: TravelWithMeApiManager
    private lateinit var utilities: Utilities

    private var listaHotelesDestacados: ArrayList<Hotel> = ArrayList()
    private lateinit var carouselExplorarAdapter: CarouselExplorarAdapter

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

        inicializar()


    }

    fun inicializar() {
        utilities = Utilities()
        travelWithMeApiManager = TravelWithMeApiManager(requireContext())


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
        CoroutineScope(Dispatchers.IO).launch {
            try {
                listaHotelesDestacados = travelWithMeApiManager.buscarDiezHotelesRandom()

                // Actualiza el RecyclerView en el hilo principal (no deja hacerlo en una corrutina)
                withContext(Dispatchers.Main) {
                    carouselExplorarAdapter.setData(listaHotelesDestacados)
                }

            } catch (e: Exception) {
                Log.v("buscarHotelesConParametrosParent", "${e.message}")
                view?.let { utilities.lanzarSnackBarCorto("Error cargar los resultados", it) };
            }
        }



    }

    private fun inicializarCarouselRecyclerView() {
        carouselExplorarAdapter = CarouselExplorarAdapter(listaHotelesDestacados) {
                hotel -> intentAHotelFrag(hotel)
        }
        binding.carouselRecyclerView.adapter = carouselExplorarAdapter

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
