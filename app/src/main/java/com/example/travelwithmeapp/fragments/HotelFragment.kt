package com.example.travelwithmeapp.fragments

import ResenaHotelAdapter
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.activities.MainActivity
import com.example.travelwithmeapp.adapters.CarouselAdapter
import com.example.travelwithmeapp.databinding.FragmentHotelBinding
import com.example.travelwithmeapp.models.Hotel
import com.example.travelwithmeapp.utils.FirebaseFirestoreManager
import com.example.travelwithmeapp.utils.TravelWithMeApiManager
import com.example.travelwithmeapp.utils.Utilities
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.carousel.CarouselSnapHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.OffsetDateTime

/**
 * Fragment for displaying details of a specific hotel, including its location, amenities,
 * reviews, and options to book or view its website.
 *
 * @author Samuel Fraisoli
 */

class HotelFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentHotelBinding
    private var utilities = Utilities()
    private lateinit var firebaseFirestoreManager: FirebaseFirestoreManager;
    private lateinit var travelWithMeApiManager: TravelWithMeApiManager;

    private lateinit var recyclerResena: RecyclerView
    private lateinit var adaptadorResena: ResenaHotelAdapter
    private lateinit var hotel: Hotel
    private var fecha_entrada_hotel: OffsetDateTime? = null
    private var fecha_salida_hotel: OffsetDateTime? = null

    private var uid = ""
    private var hotelFavorito = false



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
        firebaseFirestoreManager = FirebaseFirestoreManager(requireContext(), binding.root)
        travelWithMeApiManager = TravelWithMeApiManager(requireContext())
        utilities = Utilities()
        recogerIntent()
        recogerUidActMain()

        //carousel
        listaImagenes.addAll(hotel.fotos)
        inicializarCarouselRecyclerView()
        //iniciarCorrutinaCarousel()
        configuarRecycler()

        //otros elementos visuales
        inicializarTextosYBotones()

        //gestión de favoritos
        comprobarHotelFavorito()

    }

    @RequiresApi(Build.VERSION_CODES.O)
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
        binding.precio.text = "${utilities.quitarDecimalesSiAcabaEnCero(hotel.detalles.precio)}€"


        //recargar las reseñas
        recargarResenasHotel(hotel.id)


        //botones
        binding.buttonSitioWeb.setOnClickListener() {
            intentASitioWeb(hotel.detalles.web)
        }
        binding.buttonReservar.setOnClickListener() {
            intentASitioWeb(hotel.detalles.web)
        }
        binding.buttonEscribirReseA.setOnClickListener() {
            intentAReseñas(hotel)
        }
        binding.favorito.setOnClickListener() {
            Log.v("", "favorito clicado")
            clickBotonFavorito()
        }
        binding.noFavorito.setOnClickListener() {
            Log.v("", "no favorito clicado")
            clickBotonFavorito()
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun recargarResenasHotel(id: Long) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                var hotelRecogido = travelWithMeApiManager.buscarHotelPorIdParent(id)
                hotel.resenas = hotelRecogido.resenas

                // Actualiza el RecyclerView en el hilo principal (no deja hacerlo en una corrutina)
                withContext(Dispatchers.Main) {
                    adaptadorResena.setData(hotel.resenas)
                }

            } catch (e: Exception) {
                Log.v("recargarResenasHotel", "${e.message}")
                view?.let { utilities.lanzarSnackBarCorto("Error cargar las reseñas", it) };
            }
        }
    }


    // CAROUSEL
    private fun inicializarCarouselRecyclerView() {
        binding.carouselRecyclerView.adapter = CarouselAdapter(listaImagenes)
        var snaphelper = CarouselSnapHelper().attachToRecyclerView(binding.carouselRecyclerView)
    }


    // RECYCLER RESEÑAS
    private fun configuarRecycler() {
        recyclerResena = binding.recyclerResenaHotel
        adaptadorResena = ResenaHotelAdapter(hotel.resenas)
        recyclerResena.adapter = adaptadorResena
        recyclerResena.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    //MAPA
    /** Cuando el mapa se carga, la interfaz OnMapReadyCallback llama a este método que coloca un marcador
     *  en la dirección, y mueve la cámara a esa ubicación también
     */
    override fun onMapReady(googleMap: GoogleMap) {
        var localizacionHotel = LatLng(hotel.direccion.latitud, hotel.direccion.longitud)

        googleMap.addMarker(
            MarkerOptions()
                .position(localizacionHotel)
                .title(hotel.nombre)
        )

        val zoomLevel = 15.0f
        googleMap.setMaxZoomPreference(googleMap.maxZoomLevel)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacionHotel, zoomLevel))
        googleMap.uiSettings.isZoomControlsEnabled = true
    }

    // BOTÓN FAVORITOS
    fun clickBotonFavorito() {
        if(!hotelFavorito) {
            botonFavoritoSeleccionado()
            añadirHotelFavorito()
        }
        else {
            botonFavoritoNoSeleccionado()
            eliminarHotelFavorito()
        }
    }


    fun botonFavoritoSeleccionado() {
        binding.favorito.visibility = View.VISIBLE
        binding.noFavorito.visibility = View.INVISIBLE
        hotelFavorito = true
    }

    fun botonFavoritoNoSeleccionado() {
        binding.favorito.visibility = View.INVISIBLE
        binding.noFavorito.visibility = View.VISIBLE
        hotelFavorito = false
    }

    fun comprobarHotelFavorito() {
        if(uid == null) {
            return
        }
        firebaseFirestoreManager.comprobarFavorito(uid, hotel.id.toString()) {
            if(it) {
                botonFavoritoSeleccionado()
                hotelFavorito = true
            }
            else {
                botonFavoritoNoSeleccionado()
                hotelFavorito = false
            }
        }
    }

    fun añadirHotelFavorito() {
        if(uid == null) {
            return
        }
        firebaseFirestoreManager.añadirFavorito(uid, hotel.id.toString()) {}
    }

    fun eliminarHotelFavorito() {
        if(uid == null) {
            return
        }
        firebaseFirestoreManager.eliminarFavorito(uid, hotel.id.toString()) {}
    }


    fun recogerUidActMain() {
        if(activity != null && activity is MainActivity) {
            uid = (activity as MainActivity).user.uid
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