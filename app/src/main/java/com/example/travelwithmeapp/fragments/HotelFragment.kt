package com.example.travelwithmeapp.fragments

import ResenaHotelAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.adapters.CarouselAdapter
import com.example.travelwithmeapp.databinding.FragmentHotelBinding
import com.example.travelwithmeapp.models.Hotel
import com.example.travelwithmeapp.utils.Utilities
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.carousel.CarouselSnapHelper
import java.time.OffsetDateTime


class HotelFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentHotelBinding
    private var utilities = Utilities()
    private lateinit var recyclerResena: RecyclerView
    private lateinit var adaptadorResena: ResenaHotelAdapter
    private lateinit var hotel: Hotel
    private var fecha_entrada_hotel: OffsetDateTime? = null
    private var fecha_salida_hotel: OffsetDateTime? = null



    private lateinit var mapview: MapView

    private var listaImagenes: ArrayList<String> = ArrayList()  //lista imágenes carousel


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

        mapview = binding.mapViewHotel
        mapview.onCreate(savedInstanceState)
        mapview.getMapAsync(this)
    }

    override fun onPause() {
        super.onPause()
        mapview.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapview.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapview.onLowMemory()
    }

    override fun onStop() {
        super.onStop()
        //jobCorrutina.cancel()
    }


    private fun inicializar() {
        utilities = Utilities()
        recogerIntent()

        //carousel
        listaImagenes.addAll(hotel.fotos)
        inicializarCarouselRecyclerView()
        //iniciarCorrutinaCarousel()
        configuarRecycler()

        //otros elementos visuales
        inicializarTextosYBotones()

        gestionBotonFavoritos()










    }

    private fun inicializarTextosYBotones() {
        //toolbar
        utilities.crearToolbarFragmSecundario(binding.toolbar.toolbarLayout, "${hotel.nombre}", binding.toolbar.toolbarLayoutTitle, activity as AppCompatActivity)

        //textos
        binding.direccion.text = hotel.direccion.direccionString
        binding.telefono.text = hotel.detalles.telefono

        if(fecha_entrada_hotel != null && fecha_salida_hotel != null) {
            binding.textviewFecha.text = "${getString(R.string.Del)} ${utilities.formatearOffsetDateTimeDDMMMM(fecha_entrada_hotel!!)} " +
                    " ${getString(R.string.al)} ${utilities.formatearOffsetDateTimeDDMMMM(fecha_salida_hotel!!)}"
        }
        val comodidadesFormateadas = hotel.detalles.comodidades.joinToString(separator = ", ", postfix = ".")
        binding.textviewComodidadesTexto.text = comodidadesFormateadas
        binding.textviewDescripcionTexto.text = hotel.detalles.descripcion

        //botones
        binding.buttonSitioWeb.setOnClickListener() {
            intentASitioWeb(hotel.detalles.web)
        }
        binding.buttonEscribirReseA.setOnClickListener() {
            intentAReseñas(hotel)
        }
    }


    // INTENTS
    private fun recogerIntent() {
        val bundle = arguments
        if (bundle != null) {
            hotel = bundle.getSerializable("hotel") as Hotel
            var fecha_entrada_string = bundle.getString("fecha_entrada_hotel")
            if(fecha_entrada_string != null) {
                fecha_entrada_hotel = utilities.parseStringAOffsetDateDDMMYYYY(fecha_entrada_string)
            }
            var fecha_salida_string = bundle.getString("fecha_salida_hotel")
            if(fecha_salida_string != null) {
                fecha_salida_hotel = utilities.parseStringAOffsetDateDDMMYYYY(fecha_salida_string)
            }
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
        recyclerResena.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    //MAPA
    /** Cuando el mapa se carga, la interfaz OnMapReadyCallback llama a este método que coloca un marcador
     *  en la dirección, y mueve la cámara a esa ubicación también
     */
    override fun onMapReady(googleMap: GoogleMap) {
        //todo cambiar con localización del hotel
        var localizacionPzaSol = LatLng(40.4166667, -3.7038889)

        googleMap.addMarker(
            MarkerOptions()
                .position(localizacionPzaSol)
                .title(hotel.nombre)
        )

        val zoomLevel = 15.0f
        googleMap.setMaxZoomPreference(googleMap.maxZoomLevel)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacionPzaSol, zoomLevel))
        googleMap.uiSettings.isZoomControlsEnabled = true
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