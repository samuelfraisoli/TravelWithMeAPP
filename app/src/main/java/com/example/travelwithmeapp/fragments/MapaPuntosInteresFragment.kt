package com.example.travelwithmeapp.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import com.example.travelwithmeapp.databinding.FragmentMapaPuntosInteresBinding

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.travelwithmeapp.utils.Utilities
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Fragments that shows a map with the user location and points of interest near them
 *
 * @author Samuel Fraisoli
 */

class MapaPuntosInteresFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMapaPuntosInteresBinding
    private lateinit var utilities: Utilities

    private lateinit var mapview: MapView
    private lateinit var locationManager: LocationManager

    //por defecto tiene la localización de plaza del sol
    private var localizacionUsuario = LatLng(40.4166667, -3.7038889)
    private var listaLocalizaciones = ArrayList<LatLng>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapaPuntosInteresBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()

        mapview = binding.mapView
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
        utilities.crearToolbarFragmSecundario(binding.toolbar.toolbarLayout, "Puntos de interés", binding.toolbar.toolbarLayoutTitle, activity as AppCompatActivity)
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private fun generarLocalizaciones() {
        //todo coger hoteles y generar las localizaciones
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.uiSettings.isZoomControlsEnabled = true

        val zoomLevel = 15.0f
        googleMap.setMaxZoomPreference(googleMap.maxZoomLevel)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacionUsuario, zoomLevel))


        if(listaLocalizaciones.isNotEmpty()) {
            for(localizacion: LatLng in listaLocalizaciones) {
                googleMap.addMarker(
                    MarkerOptions()
                        .position(localizacion)
                        .title("Hotel")
                )
            }
        }
    }

    //GESTIONAR LOCALIZACIÓN DEL USUARIO

    fun verificarPermisosLocalizacion() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation()
        } else {
            // Si no se tienen permisos, solicitarlos
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
        }
    }
    private fun getLocation() {
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Obtener la última ubicación conocida
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location != null) {
            val latitude = location.latitude
            val longitude = location.longitude
            // Aquí puedes hacer lo que necesites con la latitud y longitud
            Log.d("Ubicación", "Latitud: $latitude, Longitud: $longitude")
        } else {
            Log.v("", "No se ha encontrado la ubicación")

        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
    }


}
