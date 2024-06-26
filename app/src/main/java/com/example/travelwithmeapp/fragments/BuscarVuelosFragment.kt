package com.example.travelwithmeapp.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.adapters.BuscarVuelosAdapter
import com.example.travelwithmeapp.databinding.FragmentBuscarVuelosBinding
import com.example.travelwithmeapp.models.Hotel
import com.example.travelwithmeapp.models.Vuelo
import com.example.travelwithmeapp.utils.MockData
import com.example.travelwithmeapp.utils.TravelWithMeApiManager
import com.example.travelwithmeapp.utils.Utilities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

/**
 * Fragment that shows a clicable list of flights based on origin, destination and dates
 *
 * @author Samuel Fraisoli
 */

class BuscarVuelosFragment : Fragment() {
    private lateinit var binding: FragmentBuscarVuelosBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var travelWithMeApiManager: TravelWithMeApiManager

    private lateinit var fechaVuelo: String
    private lateinit var origenVuelo: String
    private lateinit var destinoVuelo: String


    private var utilities = Utilities()
    private var mockdata = MockData()


    private lateinit var adaptadorRecycler: BuscarVuelosAdapter
    private var listaVuelos = ArrayList<Vuelo>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuscarVuelosBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inicializar()
    }





    @RequiresApi(Build.VERSION_CODES.O)
    fun inicializar() {
        travelWithMeApiManager = TravelWithMeApiManager(requireContext())
        configurarRecycler()
        recogerIntent()
        buscarVuelos(origenVuelo, destinoVuelo, fechaVuelo)
        utilities.crearToolbarFragmSecundario(binding.toolbar.toolbarLayout, "vuelos", binding.toolbar.toolbarLayoutTitle, activity as AppCompatActivity)
    }

    //INTENTS
    private fun recogerIntent() {
        val bundle = arguments
        if (bundle != null) {
            fechaVuelo = bundle.getString("fecha_vuelo") !!
            origenVuelo = bundle.getString("origen_vuelo")!!
            destinoVuelo = bundle.getString("destino_vuelo")!!
        }
    }

    fun intentAVueloFrag(vuelo: Vuelo) {
        val bundle = Bundle()
        bundle.putSerializable("vuelo", vuelo)
        findNavController()?.navigate(R.id.action_buscarVuelosFragment_to_vueloFragment, bundle)
    }


    //PETICIONES
    @RequiresApi(Build.VERSION_CODES.O)
    fun buscarVuelos(origenVuelo: String, destinoVuelo: String, fechaVuelo: String) {
        if(origenVuelo.equals("PRUEBA") && destinoVuelo.equals("PRUEBA")) {
            listaVuelos = mockdata.listaPruebaVuelos()
            adaptadorRecycler.setData(listaVuelos)
        }
        else {
            CoroutineScope(Dispatchers.IO).launch {
                try {

                    listaVuelos = travelWithMeApiManager.buscarVuelosConParametrosParent(
                        origenVuelo,
                        destinoVuelo,
                        fechaVuelo
                    )
                    Log.v("buscarvuelos", "${origenVuelo}, ${destinoVuelo}, ${fechaVuelo}")

                    // Actualiza el RecyclerView en el hilo principal (no deja hacerlo en una corrutina)
                    withContext(Dispatchers.Main) {
                        adaptadorRecycler.setData(listaVuelos)
                    }

                } catch (e: Exception) {
                    Log.v("buscarvuelos", "${e.message}")
                    view?.let { utilities.lanzarSnackBarCorto("Error cargar los resultados", it) };
                }
            }
        }
    }


    fun configurarRecycler() {
        adaptadorRecycler = BuscarVuelosAdapter(listaVuelos) {
            vuelo -> intentAVueloFrag(vuelo)
        }
        recyclerView = binding.recyclerBusquedaFrag
        recyclerView.adapter = adaptadorRecycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }
}

