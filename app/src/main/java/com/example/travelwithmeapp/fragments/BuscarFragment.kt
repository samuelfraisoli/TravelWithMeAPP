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
 * Fragment for searching flights or hotels
 *
 * @author Samuel Fraisoli
 */

class BuscarFragment : Fragment() {
    private lateinit var binding: FragmentBuscarBinding
    private var backgroundActual: ImageView? = null
    //es un int, que va a determinar si el usuario quiere buscar vuelos (1), hoteles (2), o no ha seleccionado nada (0)
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

    //VERIFICAR EDITTEXT
    fun verificarDatosHoteles() : Boolean {
        if(
            binding.destinoHotel.text.isNotEmpty() &&
            binding.fechaEntradaHotel.text.isNotEmpty() &&
            binding.fechaSalidaHotel.text.isNotEmpty()
        )
        { return true }
        else {return false}
    }

    fun verificarDatosVuelos() : Boolean {
        if(
            binding.origenVuelo.text.isNotEmpty() &&
            binding.destinoVuelo.text.isNotEmpty() &&
            binding.fechaVuelo.text.isNotEmpty()
        )
        {return true}
        else {return false}
    }

    //INTENTS
    fun recogerIntentExplorar() {
        val bundle = arguments
        val eleccion = bundle?.getInt("eleccion") ?: 0

        when(eleccion) {
            1 -> mostrarPantallaVuelos()
            2 -> mostrarPantallaHoteles()
        }
    }
    fun intentABuscarHoteles() {
        val bundle = Bundle()
        bundle.putString("destino_hotel", binding.destinoHotel.text.toString())
        bundle.putString("fecha_entrada_hotel", binding.fechaEntradaHotel.text.toString())
        bundle.putString("fecha_salida_hotel", binding.fechaSalidaHotel.text.toString())
        findNavController()?.navigate(R.id.action_buscarFragment_to_buscarHotelesFragment, bundle)
    }

    fun intentABuscarVuelos() {
        val bundle = Bundle()
        bundle.putString("origen_vuelo", binding.origenVuelo.text.toString())
        bundle.putString("destino_vuelo", binding.destinoVuelo.text.toString())
        bundle.putString("fecha_vuelo", binding.fechaVuelo.text.toString())
        findNavController()?.navigate(R.id.action_buscarFragment_to_buscarVuelosFragment, bundle)
    }


    //EFECTOS
    fun mostrarPantallaVuelos() {
        cambiarFondo("vuelos")
        busquedaFlag = 1
        cambiarVisibilidadChildrenViewGroup(binding.constraintLayoutVuelos, 1)
        cambiarVisibilidadChildrenViewGroup(binding.constraintLayoutHoteles, 0)
    }

    fun mostrarPantallaHoteles() {
        cambiarFondo("hoteles")
        busquedaFlag = 2
        cambiarVisibilidadChildrenViewGroup(binding.constraintLayoutHoteles, 1)
        cambiarVisibilidadChildrenViewGroup(binding.constraintLayoutVuelos, 0)
    }

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

    fun cambiarFondo(tipo: String) {
        var imagen: ImageView? = null
        when(tipo) {
            "hoteles" -> imagen = binding.backgroundHoteles
            "vuelos" -> imagen = binding.backgroundVuelos
        }

        imagen?.let {
            backgroundActual?.let {
                //hago que la imagen que quiero que sea el fondo, se superponga al fondo que había antes
                imagen.elevation = backgroundActual!!.elevation
                backgroundActual!!.elevation = backgroundActual!!.elevation -1
            }

            //la imagen aparece con un fade in
            val fadeIn = AlphaAnimation(0f, 1f)
            fadeIn.duration = 1000 // Duración de la animación en milisegundos
            imagen.startAnimation(fadeIn)

            //cuando ha terminado el fade in, hago la imagen visible
            imagen.visibility = View.VISIBLE

            //convierto la imagen que he hecho visible al background actual
            backgroundActual = imagen
        }

    }


}


