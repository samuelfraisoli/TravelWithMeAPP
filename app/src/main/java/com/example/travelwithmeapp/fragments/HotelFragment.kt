package com.example.travelwithmeapp.fragments

import ResenaHotelAdapter
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
    private lateinit var utilities: Utilities
    private lateinit var recyclerResena: RecyclerView
    private lateinit var adaptadorResena: ResenaHotelAdapter

    private lateinit var hotel: Hotel
    private var fecha_entrada_hotel: OffsetDateTime? = null
    private var fecha_salida_hotel: OffsetDateTime? = null

    private var listaImagenes: ArrayList<String> = ArrayList()  //lista imágenes carousel
    private var listaHotelesFav = ArrayList<Hotel>() // Lista de hoteles favoritos

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
        utilities = Utilities()
        recogerIntent()
        utilities.crearToolbarFragmSecundario(binding.toolbar.toolbarLayout, "${hotel.nombre}", binding.toolbar.toolbarLayoutTitle, activity as AppCompatActivity)
        listaImagenes.addAll(hotel.fotos)

        inicializarCarouselRecyclerView()
        configuarRecycler()


        val comodidadesFormateadas = hotel.detalles.comodidades.joinToString(separator = ", ", postfix = ".")
        binding.textviewComodidadesTexto.text = comodidadesFormateadas

        // Pulsación de botones de favorito
        gestionBotonFavoritos()
    }


    // INTENTS
    private fun recogerIntent() {
    val bundle = arguments
    if (bundle != null) {
        hotel = bundle.getSerializable("hotel") as Hotel
        var fecha_entrada_string = bundle.getString("fecha_entrada_hotel")
        fecha_entrada_hotel = if (fecha_entrada_string != null) utilities.parseStringAOffsetDateDDMMYYYY(fecha_entrada_string) else null
        var fecha_salida_string = bundle.getString("fecha_salida_hotel")
        fecha_salida_hotel = if (fecha_salida_string != null) utilities.parseStringAOffsetDateDDMMYYYY(fecha_salida_string) else null
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


    // RECYCLER RESEÑAS
    private fun configuarRecycler() {
        recyclerResena = binding.recyclerResenaHotel
        adaptadorResena = ResenaHotelAdapter(hotel.resena)
        recyclerResena.adapter = adaptadorResena
        recyclerResena.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }


    // BOTÓN FAVORITOS
    private fun gestionBotonFavoritos() {
        binding.noFavorito.setOnClickListener() {
            binding.noFavorito.visibility = View.INVISIBLE
            binding.favorito.visibility = View.VISIBLE

            //TODO añadir funcion para subir hotel a favoritos a la bd
        }

        binding.favorito.setOnClickListener() {
            binding.favorito.visibility = View.INVISIBLE
            binding.noFavorito.visibility = View.VISIBLE

            //TODO añadir funcion para eliminar hotel de la bd
        }
    }
}