package com.example.travelwithmeapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.adapters.HotelesAdapter
import com.example.travelwithmeapp.databinding.FragmentBuscarHotelesBinding
import com.example.travelwithmeapp.models.Hotel
import com.example.travelwithmeapp.utils.MockData
import com.example.travelwithmeapp.utils.Utilities


class BuscarHotelesFragment : Fragment() {
    private lateinit var binding: FragmentBuscarHotelesBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adaptadorRecycler: HotelesAdapter

    private var destino_hotel : String = ""
    private var fecha_entrada_hotel : String = ""
    private var fecha_salida_hotel : String = ""

    private var hoteles = ArrayList<Hotel>()

    private var mockdata = MockData()
    private var utilities = Utilities()

    private lateinit var hotelClicado: Hotel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuscarHotelesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var destino_hotel = arguments?.getString("destino_hotel").toString()
        var fecha_entrada_hotel = arguments?.getString("fecha_entrada_hotel").toString()
        var fecha_salida_hotel = arguments?.getString("destino_hotel").toString()
        Log.v("destino_hotel", "$destino_hotel")
        inicializar()
    }



    fun inicializar() {
        utilities.crearToolbarFragmSecundario(binding.toolbar.toolbarLayout, "hoteles", binding.toolbar.toolbarLayoutTitle, activity as AppCompatActivity)
        recogerIntent()
        configurarRecycler()
        buscarHoteles()

    }

    fun recogerIntent() {
        val bundle = arguments
        if (bundle != null) {
            destino_hotel = bundle.getString("destino_hotel") ?: ""
            fecha_entrada_hotel = bundle.getString("fecha_entrada_hotel") ?: ""
            fecha_salida_hotel = bundle.getString("fecha_salida_hotel") ?: ""
            Log.v("bundle", "$destino_hotel, $fecha_entrada_hotel, $fecha_salida_hotel")
        }
    }

    /**
     * Configura el recycler con su adaptador
     * Al crear el adaptador se le pasa la lista de los hoteles y una lambda
     * La lambda se ha declarado en el constructor del adapter (Hotel -> Unit), es la acción que se va a ejecutar cuando el
     * usuario clique en un viewHolder
     * - El dato Hotel es el hotel de la lista sobre el que se hará click
     * - Una vez que se ejecute la lambda, pasará ese objeto Hotel, y se ejecutará la función cambiarFragment*/
    fun configurarRecycler() {
        recyclerView = binding.recyclerBusquedaFrag
        adaptadorRecycler = HotelesAdapter(hoteles) { hotel ->
            intentAHotelFrag(hotel)
        }
        recyclerView.adapter = adaptadorRecycler
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    fun buscarHoteles() {
            //todo cambiar para que coja los datos de la api
            //hoteles = lanzarPeticionApi(origen, destino, fecha
            hoteles.clear()
            hoteles.addAll(mockdata.listaPruebaHoteles())
            adaptadorRecycler.notifyDataSetChanged()
        }





    /**pasa al fragment HotelFragment, y le pasa los datos del hotel seleccionado para mostrarlos en el fragment
     * No llama a su propio navegador, si no al del fragment padre "BuscarFragment". De esta forma es capaz de salir del tablayout en el que
     * está este fragment y buscarVuelosFragment*/
    fun intentAHotelFrag(hotel: Hotel) {
        val bundle = Bundle()
        bundle.putSerializable("hotel", hotel)
        bundle.putString("fecha_entrada_hotel", fecha_entrada_hotel)
        bundle.putString("fecha_salida_hotel", fecha_salida_hotel)
        findNavController()?.navigate(R.id.action_buscarHotelesFragment_to_hotelFragment, bundle)

    }
}
