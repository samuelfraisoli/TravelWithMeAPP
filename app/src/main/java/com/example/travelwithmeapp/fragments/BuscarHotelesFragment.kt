package com.example.travelwithmeapp.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
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
import com.example.travelwithmeapp.utils.TripadvisorAPIManager
import com.example.travelwithmeapp.utils.Utilities
import java.util.Locale


class BuscarHotelesFragment : Fragment() {
    private lateinit var binding: FragmentBuscarHotelesBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adaptadorRecycler: HotelesAdapter
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
        recyclerView = binding.recyclerBusquedaFrag
        utilities.crearToolbar(binding.toolbar, "hoteles", binding.toolbarTitle, activity as AppCompatActivity)
        configurarRecycler()
        buscarHoteles()

    }

    fun buscarHoteles() {
        val bundle = arguments
        if(bundle == null) {
            Log.v("bundle", "bundle vacío")
        }

        if (bundle != null) {
            val destino_hotel = bundle.getString("destino_hotel") ?: ""
            val fecha_entrada_hotel = bundle.getString("fecha_entrada_hotel") ?: ""
            val fecha_salida_hotel = bundle.getString("fecha_salida_hotel") ?: ""
            Log.v("bundle", "$destino_hotel, $fecha_entrada_hotel, $fecha_salida_hotel")
            //todo cambiar para que coja los datos de la api
            //hoteles = lanzarPeticionApi(origen, destino, fecha)
            hoteles = mockdata.listaPruebaHoteles()
            Log.v("", "${hoteles.get(0)}")
            adaptadorRecycler.notifyDataSetChanged()
        }
    }

    fun lanzarPeticionApi(origen: String, destino: String, fecha: String): ArrayList<Hotel> {
        var hoteles = ArrayList<Hotel>()
        return hoteles
    }

    /**
     * Configura el recycler con su adaptador
     * Al crear el adaptador se le pasa la lista de los hoteles y una lambda
     * La lambda se ha declarado en el constructor del adapter (Hotel -> Unit), es la acción que se va a ejecutar cuando el
     * usuario clique en un viewHolder
     * - El dato Hotel es el hotel de la lista sobre el que se hará click
     * - Una vez que se ejecute la lambda, pasará ese objeto Hotel, y se ejecutará la función cambiarFragment*/
    fun configurarRecycler() {
        adaptadorRecycler = HotelesAdapter(hoteles) { hotel ->
            cambiarFragment(hotel)
        }
        recyclerView.adapter = adaptadorRecycler
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }


    /**pasa al fragment HotelFragment, y le pasa los datos del hotel seleccionado para mostrarlos en el fragment
     * No llama a su propio navegador, si no al del fragment padre "BuscarFragment". De esta forma es capaz de salir del tablayout en el que
     * está este fragment y buscarVuelosFragment*/
    fun cambiarFragment(hotel: Hotel) {
        val bundle = Bundle()
        bundle.putSerializable("hotel", hotel)
        findNavController()?.navigate(R.id.action_buscarHotelesFragment_to_hotelFragment, bundle)

    }
}
