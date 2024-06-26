package com.example.travelwithmeapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.FragmentBuscarBinding
import com.example.travelwithmeapp.utils.Utilities

/**
 * Fragment class for handling the search functionality.
 * Allows the user to search for flights or hotels based on selected criteria.

 * @author Samuel Fraisoli
 */

class BuscarFragment : Fragment() {
    private lateinit var binding: FragmentBuscarBinding
    private var backgroundActual: ImageView? = null
    // A flag to determine if the user wants to search for flights (1), hotels (2), or has not selected anything (0)
    private var busquedaFlag = 0
    private lateinit var utilities: Utilities

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuscarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    /**
     * Initializes the fragment by setting up the UI components and event listeners.
     */
    fun inicializar() {
        utilities = Utilities()
        cambiarVisibilidadChildrenViewGroup(binding.constraintLayoutVuelos, 0)
        cambiarVisibilidadChildrenViewGroup(binding.constraintLayoutHoteles, 0)
        utilities.crearToolbarMenuPrincipal(binding.toolbar.toolbarLayout, "Buscar", binding.toolbar.toolbarLayoutTitle, activity as AppCompatActivity)

        recogerIntentExplorar()

        binding.toggleButton.addOnButtonCheckedListener { toggleButtonGroup, checkedId, isChecked ->
            if(isChecked) {
                when(checkedId) {
                    binding.buttonVuelos.id -> { mostrarPantallaVuelos() }
                    binding.buttonHoteles.id -> { mostrarPantallaHoteles() }
                }
            }
        }
        binding.fechaVuelo.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) { utilities.lanzarDatePickerConstraintHoy(childFragmentManager, binding.fechaVuelo) }
        }
        binding.fechaEntradaHotel.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) { utilities.lanzarDatePickerRango(childFragmentManager, binding.fechaEntradaHotel, binding.fechaSalidaHotel)}
        }
        binding.fechaSalidaHotel.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) { utilities.lanzarDatePickerRango(childFragmentManager, binding.fechaEntradaHotel, binding.fechaSalidaHotel) }
        }
        binding.buttonComenzar.setOnClickListener() {
            when(busquedaFlag) {
                1 -> { if(verificarDatosVuelos()) { intentABuscarVuelos() } }
                2 -> {  if(verificarDatosHoteles()) { intentABuscarHoteles() } }
            }
        }
    }

    /**
     * ============================
     * FUNCTIONS FOR VERIFYING DATA
     * ============================
     */

    /**
     * Verifies if the required hotel search fields are filled.
     */
    fun verificarDatosHoteles() : Boolean {
        if(
            binding.destinoHotel.text.isNotEmpty() &&
            binding.fechaEntradaHotel.text.isNotEmpty() &&
            binding.fechaSalidaHotel.text.isNotEmpty()
        )
        { return true }
        else {return false}
    }

    /**
     * Verifies if the required flight search fields are filled.
     */
    fun verificarDatosVuelos() : Boolean {
        if(
            binding.origenVuelo.text.isNotEmpty() &&
            binding.destinoVuelo.text.isNotEmpty() &&
            binding.fechaVuelo.text.isNotEmpty()
        )
        {return true}
        else {return false}
    }

    /**
     * ============================
     * INTENTS
     * ============================
     */

    /**
     * Retrieves the intent data if the fragment is launched from another part of the app.
     */
    fun recogerIntentExplorar() {
        val bundle = arguments
        val eleccion = bundle?.getInt("eleccion") ?: 0

        when(eleccion) {
            1 -> mostrarPantallaVuelos()
            2 -> mostrarPantallaHoteles()
        }
    }

    /**
     * Sends an intent to BuscarHoteles with hotel filters
     */
    fun intentABuscarHoteles() {
        val bundle = Bundle()
        bundle.putString("destino_hotel", binding.destinoHotel.text.toString())
        bundle.putString("fecha_entrada_hotel", binding.fechaEntradaHotel.text.toString())
        bundle.putString("fecha_salida_hotel", binding.fechaSalidaHotel.text.toString())
        findNavController()?.navigate(R.id.action_buscarFragment_to_buscarHotelesFragment, bundle)
    }

    /**
     * Sends an intent to BuscarVuelos with flight filters
     */
    fun intentABuscarVuelos() {
        val bundle = Bundle()
        bundle.putString("origen_vuelo", binding.origenVuelo.text.toString())
        bundle.putString("destino_vuelo", binding.destinoVuelo.text.toString())
        bundle.putString("fecha_vuelo", binding.fechaVuelo.text.toString())
        findNavController()?.navigate(R.id.action_buscarFragment_to_buscarVuelosFragment, bundle)
    }


    /**
     * ============================
     * UI CHANGES
     * ============================
     */

    /**
     * Displays the flight search screen and updates the UI accordingly.
     */
    fun mostrarPantallaVuelos() {
        cambiarFondo("vuelos")
        busquedaFlag = 1
        cambiarVisibilidadChildrenViewGroup(binding.constraintLayoutVuelos, 1)
        cambiarVisibilidadChildrenViewGroup(binding.constraintLayoutHoteles, 0)
    }

    /**
     * Displays the hotel search screen and updates the UI accordingly.
     */
    fun mostrarPantallaHoteles() {
        cambiarFondo("hoteles")
        busquedaFlag = 2
        cambiarVisibilidadChildrenViewGroup(binding.constraintLayoutHoteles, 1)
        cambiarVisibilidadChildrenViewGroup(binding.constraintLayoutVuelos, 0)
    }

    /**
     * Changes the visibility of all children in a ViewGroup.
     *
     * @param viewGroup The ViewGroup whose children should be shown or hidden.
     * @param funcionalidad The visibility flag: 0 to hide, 1 to show.
     */
    fun cambiarVisibilidadChildrenViewGroup(viewGroup: ViewGroup, funcionalidad: Int) {
        when(funcionalidad) {
            0 -> viewGroup.visibility = View.GONE
            1 -> viewGroup.visibility = View.VISIBLE
        }
        for(i in 0 until viewGroup.childCount) {
            val child = viewGroup.getChildAt(i)
            when(funcionalidad) {
                0 -> child.visibility = View.GONE
                1 -> child.visibility = View.VISIBLE
            }
        }
    }

    /**
     * Changes the background image based on the type (flights or hotels).
     * @param tipo The type of search (either "vuelos" for flights or "hoteles" for hotels).
     */
    fun cambiarFondo(tipo: String) {
        var imagen: ImageView? = null
        when(tipo) {
            "hoteles" -> imagen = binding.backgroundHoteles
            "vuelos" -> imagen = binding.backgroundVuelos
        }

        imagen?.let {
            backgroundActual?.let {
                // Make the new background image overlay the previous one
                imagen.elevation = backgroundActual!!.elevation
                backgroundActual!!.elevation = backgroundActual!!.elevation -1
            }

            // Apply a fade-in animation to the new background image
            val fadeIn = AlphaAnimation(0f, 1f)
            fadeIn.duration = 1000
            imagen.startAnimation(fadeIn)
            imagen.visibility = View.VISIBLE
            backgroundActual = imagen
        }
    }
}


