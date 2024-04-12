package com.example.travelwithmeapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.adapters.CarouselAdapter
import com.example.travelwithmeapp.databinding.FragmentHotelBinding
import com.example.travelwithmeapp.databinding.FragmentResenaBinding
import com.example.travelwithmeapp.models.Hotel
import com.example.travelwithmeapp.utils.Utilities
import com.google.android.material.carousel.CarouselSnapHelper


class HotelFragment : Fragment() {
    private lateinit var binding: FragmentHotelBinding
    private var listaImagenes: ArrayList<String> = ArrayList()
    private lateinit var utilities: Utilities

    private lateinit var hotel: Hotel
    private var fecha_entrada_hotel: String = ""
    private var fecha_salida_hotel: String = ""

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

    private fun inicializar() {
        recogerIntent()
        utilities = Utilities()
        utilities.crearToolbar(binding.toolbar, "${hotel.name}", binding.toolbarTitle, activity as AppCompatActivity)

        inicializarCarouselRecyclerView()
        cargarElementosHotel()

        binding.buttonSitioWeb.setOnClickListener() {
            intentASitioWeb(hotel.details.web)
        }

        binding.buttonEscribirReseA.setOnClickListener() {
            intentAReseñas(hotel)
        }

    }

    private fun recogerIntent() {
        val bundle = arguments
        if (bundle != null) {
            hotel = bundle.getSerializable("hotel") as Hotel
            fecha_entrada_hotel = bundle.getString("fecha_entrada_hotel") ?: ""
            fecha_salida_hotel = bundle.getString("fecha_salida_hotel") ?: ""
            Log.v("bundle", "$hotel, $fecha_entrada_hotel, $fecha_entrada_hotel")
        }
    }

    private fun inicializarCarouselRecyclerView() {
        CarouselSnapHelper().attachToRecyclerView(binding.carouselRecyclerView)
        binding.carouselRecyclerView.adapter = CarouselAdapter(listaImagenes)
    }

    private fun cargarElementosHotel() {
        listaImagenes.addAll(hotel.photos)
        binding.textviewFecha.text = "${getString(R.string.Del)} ${fecha_entrada_hotel} ${getString(R.string.al)} ${fecha_salida_hotel}"
        binding.textviewDescripcionTexto.text = hotel.details.description
    }

    private fun intentASitioWeb(url: String) {
        // Crea un Intent implícito con la acción ACTION_VIEW y la URL del sitio web
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)


    }

    private fun intentAReseñas(hotel: Hotel) {
        //todo completar
    }




}