package com.example.travelwithmeapp.fragments

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
import com.example.travelwithmeapp.adapters.BuscarHotelesAdapter
import com.example.travelwithmeapp.databinding.FragmentBuscarHotelesBinding
import com.example.travelwithmeapp.models.Hotel
import com.example.travelwithmeapp.utils.MockData
import com.example.travelwithmeapp.utils.TravelWithMeApiManager
import com.example.travelwithmeapp.utils.Utilities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Fragment that shows a clicable list of hotels based on a destination and dates
 *
 * @author Samuel Fraisoli
 */

class BuscarHotelesFragment : Fragment() {
    private lateinit var binding: FragmentBuscarHotelesBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adaptadorRecycler: BuscarHotelesAdapter

    private var destino_hotel : String = ""
    private var fecha_entrada_hotel : String = ""
    private var fecha_salida_hotel : String = ""

    private var listaHoteles = ArrayList<Hotel>()

    private var mockdata = MockData()
    private var utilities = Utilities()
    private lateinit var travelWithMeApiManager: TravelWithMeApiManager



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuscarHotelesBinding.inflate(inflater, container, false)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        destino_hotel = arguments?.getString("destino_hotel").toString()
        fecha_entrada_hotel = arguments?.getString("fecha_entrada_hotel").toString()
        fecha_salida_hotel = arguments?.getString("destino_hotel").toString()
        Log.v("destino_hotel", "$destino_hotel")
        inicializar()
    }


    /**
     * Initializes the fragment by setting up necessary components and configurations.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun inicializar() {
        travelWithMeApiManager = TravelWithMeApiManager(requireContext())
        utilities.crearToolbarFragmSecundario(binding.toolbar.toolbarLayout, "hoteles", binding.toolbar.toolbarLayoutTitle, activity as AppCompatActivity)
        recogerIntent()
        configurarRecycler()
        buscarHoteles(destino_hotel, fecha_entrada_hotel, fecha_salida_hotel)
    }

    /**
     * Searches for hotels based on the provided parameters.
     * Testing - When the user writes PRUEBA in the edittext, gets Hotel data from the class mockdata
     * Normal mode - Gets hoteldata from the API
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun buscarHoteles(nombre: String, fechaEntrada: String, fechaSalida: String) {
        if(nombre.equals("PRUEBA")) {
            listaHoteles = mockdata.listaPruebaHoteles()
            adaptadorRecycler.setData(listaHoteles)
        }
        else {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    listaHoteles = travelWithMeApiManager.buscarHotelesConParametrosParent(nombre, fechaEntrada, fechaSalida)
                    Log.v("buscarHotelesConParametrosParent", "${nombre}, ${fechaEntrada}, ${fechaSalida}")

                    // Actualiza el RecyclerView en el hilo principal (no deja hacerlo en una corrutina)
                    withContext(Dispatchers.Main) {
                        adaptadorRecycler.setData(listaHoteles)
                    }

                } catch (e: Exception) {
                    Log.v("buscarHotelesConParametrosParent", "${e.message}")
                    view?.let { utilities.lanzarSnackBarCorto("Error cargar los resultados", it) };
                }
            }
        }
    }

    /**
     * Retrieves intent data passed to the fragment.
     */
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
     * Configures the RecyclerView with its adapter.
     * The adapter is created with a list of hotels and a lambda function.
     * The lambda function is defined in the adapter's constructor (Hotel -> Unit), which is the action executed when a viewHolder is clicked.
     * - The Hotel object is the hotel in the list that will be clicked.
     * - When the lambda is executed, it passes the Hotel object and executes the cambiarFragment function.
     */
    fun configurarRecycler() {
        recyclerView = binding.recyclerBusquedaFrag
        adaptadorRecycler = BuscarHotelesAdapter(listaHoteles) { hotel ->
            intentAHotelFrag(hotel)
        }
        recyclerView.adapter = adaptadorRecycler
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }




    /**
     * Navigates to the HotelFragment, passing the selected hotel's data to display in the fragment.
     * It calls the parent fragment's navigator (BuscarFragment), allowing it to exit the TabLayout where this fragment and BuscarVuelosFragment reside.
     */
    fun intentAHotelFrag(hotel: Hotel) {
        val bundle = Bundle()
        bundle.putSerializable("hotel", hotel)
        bundle.putString("fecha_entrada_hotel", fecha_entrada_hotel)
        bundle.putString("fecha_salida_hotel", fecha_salida_hotel)
        findNavController()?.navigate(R.id.action_buscarHotelesFragment_to_hotelFragment, bundle)

    }
}
