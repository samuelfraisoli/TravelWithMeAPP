package com.example.travelwithmeapp.fragments

import ResenaHotelAdapter
import SharedViewModel
import android.content.Intent
import android.net.Uri
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.adapters.CarouselAdapter
import com.example.travelwithmeapp.adapters.PlanificarFavoritosAdapter
import com.example.travelwithmeapp.databinding.FragmentHotelBinding

import com.example.travelwithmeapp.models.Hotel
import com.example.travelwithmeapp.utils.Utilities
import com.google.android.material.carousel.CarouselSnapHelper
import java.time.OffsetDateTime



class HotelFragment : Fragment() {
    private lateinit var binding: FragmentHotelBinding
    private var listaImagenes: ArrayList<String> = ArrayList()
    private lateinit var utilities: Utilities
    private lateinit var recyclerResena: RecyclerView
    private lateinit var adaptadorResena: ResenaHotelAdapter
    private lateinit var hotel: Hotel
    private lateinit var fecha_entrada_hotel: OffsetDateTime
    private lateinit var fecha_salida_hotel: OffsetDateTime
    private var listaHotelesFav = ArrayList<Hotel>() // Lista de hoteles favoritos
    lateinit var planificarFavoritosAdapter: PlanificarFavoritosAdapter // Adaptador para la lista de hoteles favoritos
    private  lateinit var sharedViewModel: SharedViewModel

    //private var contadorImagenCarousel = 0;
    //private lateinit var jobCorrutina: Job

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHotelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        // Inicializa planificarFavoritosAdapter aquí
        planificarFavoritosAdapter = PlanificarFavoritosAdapter(listaHotelesFav){}
    }

    override fun onStop() {
        super.onStop()
        //jobCorrutina.cancel()
    }


    private fun inicializar() {
        utilities = Utilities()
        recogerIntent()
        utilities.crearToolbarFragmSecundario(binding.toolbar.toolbarLayout, "${hotel.nombre}", binding.toolbar.toolbarLayoutTitle, activity as AppCompatActivity)

        listaImagenes.addAll(hotel.fotos)
        inicializarCarouselRecyclerView()
        //iniciarCorrutinaCarousel()
        configuarRecycler()


        binding.direccion.text = hotel.direccion.direccionString
        binding.telefono.text = hotel.detalles.telefono

        binding.textviewFecha.text = "${getString(R.string.Del)} ${utilities.formatearOffsetDateTimeDDMMMM(fecha_entrada_hotel)} ${getString(R.string.al)} ${utilities.formatearOffsetDateTimeDDMMMM(fecha_salida_hotel)}"
        binding.textviewDescripcionTexto.text = hotel.detalles.descripcion

        binding.buttonSitioWeb.setOnClickListener() {
            intentASitioWeb(hotel.detalles.web)
        }

        binding.buttonEscribirReseA.setOnClickListener() {
            intentAReseñas(hotel)
        }

        val comodidadesFormateadas = hotel.detalles.comodidades.joinToString(separator = ", ", postfix = ".")
        binding.textviewComodidadesTexto.text = comodidadesFormateadas

        // Pulsación de botones de favorito
        binding.noFavorito.setOnClickListener() {
            binding.noFavorito.visibility = View.INVISIBLE
            binding.favorito.visibility = View.VISIBLE
            // Añade el hotel actual a la lista de hoteles favoritos
            sharedViewModel.addHotel(hotel)
            // Notifica al adaptador del RecyclerView que los datos han cambiado
            planificarFavoritosAdapter.notifyDataSetChanged()
        }

        binding.favorito.setOnClickListener() {
            binding.favorito.visibility = View.INVISIBLE
            binding.noFavorito.visibility = View.VISIBLE
            // Elimina el hotel actual de la lista de hoteles favoritos
            sharedViewModel.removeHotel(hotel)
            // Notifica al adaptador del RecyclerView que los datos han cambiado
            planificarFavoritosAdapter.notifyDataSetChanged()
        }
    }


    // INTENTS
    private fun recogerIntent() {
        val bundle = arguments
        if (bundle != null) {
            hotel = bundle.getSerializable("hotel") as Hotel
            var fecha_entrada_string = bundle.getString("fecha_entrada_hotel")!!
            fecha_entrada_hotel = utilities.parseStringAOffsetDateDDMMYYYY(fecha_entrada_string)
            var fecha_salida_string = bundle.getString("fecha_salida_hotel")!!
            fecha_salida_hotel = utilities.parseStringAOffsetDateDDMMYYYY(fecha_salida_string)
        }
    }

    private fun intentASitioWeb(url: String) {
        // Crea un Intent implícito con la acción ACTION_VIEW y la URL del sitio web
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun intentAReseñas(hotel: Hotel) {
        val bundle = Bundle()
        bundle.putSerializable("hotel", hotel)
        findNavController()?.navigate(R.id.action_hotelFragment_to_resenaFragment, bundle)

    }

    // CAROUSEL
    private fun inicializarCarouselRecyclerView() {
        binding.carouselRecyclerView.adapter = CarouselAdapter(listaImagenes)
        var snaphelper = CarouselSnapHelper().attachToRecyclerView(binding.carouselRecyclerView)
    }



    private fun configuarRecycler() {
        recyclerResena = binding.recyclerResenaHotel
        adaptadorResena = ResenaHotelAdapter(hotel.resena)
        recyclerResena.adapter = adaptadorResena
        recyclerResena.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    fun obtenerHotelesFavoritos(): ArrayList<Hotel> {
        return listaHotelesFav
    }

}