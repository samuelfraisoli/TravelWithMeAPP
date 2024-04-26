package com.example.travelwithmeapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.adapters.CarouselAdapter
import com.example.travelwithmeapp.databinding.FragmentHotelBinding

import com.example.travelwithmeapp.models.Hotel
import com.example.travelwithmeapp.utils.Utilities
import com.google.android.material.carousel.CarouselSnapHelper
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.OffsetDateTime



class HotelFragment : Fragment() {
    private lateinit var binding: FragmentHotelBinding
    private var listaImagenes: ArrayList<String> = ArrayList()
    private lateinit var utilities: Utilities

    private lateinit var hotel: Hotel
    private lateinit var fecha_entrada_hotel: OffsetDateTime
    private lateinit var fecha_salida_hotel: OffsetDateTime

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

        binding.textviewFecha.text = "${getString(R.string.Del)} ${utilities.formatearOffsetDateTimeDDMMMM(fecha_entrada_hotel)} ${getString(R.string.al)} ${utilities.formatearOffsetDateTimeDDMMMM(fecha_salida_hotel)}"
        binding.textviewDescripcionTexto.text = hotel.detalles.descripcion

        binding.buttonSitioWeb.setOnClickListener() {
            intentASitioWeb(hotel.detalles.web)
        }

        binding.buttonEscribirReseA.setOnClickListener() {
            intentAReseñas(hotel)
        }

    }

    private fun cargarElementosHotel() {

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
        //todo completar
    }


    // CAROUSEL
    private fun inicializarCarouselRecyclerView() {
        binding.carouselRecyclerView.adapter = CarouselAdapter(listaImagenes)
        var snaphelper = CarouselSnapHelper().attachToRecyclerView(binding.carouselRecyclerView)
    }

    //private fun iniciarCorrutinaCarousel() {
    //    jobCorrutina = CoroutineScope(Dispatchers.Main).launch {
    //        while (isActive) {
    //            delay(3000)
    //            binding.carouselRecyclerView.
    //            binding.carouselRecyclerView.smoothScrollToPosition(contadorImagenCarousel)
    //            contadorImagenCarousel++
//
    //            if(contadorImagenCarousel == listaImagenes.size) {
    //                contadorImagenCarousel = 0
//
    //            }
    //        }
    //    }
    //}














}