package com.example.travelwithmeapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.adapters.HotelesAdapter
import com.example.travelwithmeapp.databinding.FragmentBuscarHotelesBinding
import com.example.travelwithmeapp.models.Hotel
import com.example.travelwithmeapp.utils.TripadvisorAPIManager
import com.example.travelwithmeapp.utils.Utilities
import java.util.Locale


class BuscarHotelesFragment : Fragment() {
    private lateinit var binding: FragmentBuscarHotelesBinding
    private lateinit var searchView: SearchView
    private lateinit var fechaEntradaTextView: Button
    private lateinit var fechaSalidaTextView: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adaptadorRecycler: HotelesAdapter


    private var utilities = Utilities()


    private lateinit var busqueda: String
    private lateinit var hotelClicado: Hotel
    private var listaHoteles = ArrayList<Hotel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuscarHotelesBinding.inflate(inflater, container, false)

        inicializar()

        return binding.root
    }


    fun inicializar() {
        searchView = binding.searchViewBusquedaFrag
        fechaEntradaTextView = binding.buttonFechaEntrada
        fechaSalidaTextView = binding.buttonFechaSalida
        recyclerView = binding.recyclerBusquedaFrag

        binding.buttonFechaSalida.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                utilities.lanzarDatePickerDialog(view, requireContext())
            }
        }
        binding.buttonFechaEntrada.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                utilities.lanzarDatePickerDialog(view, requireContext())
            }
        }

        /**se crea un objeto que instancia la interfaz onquerytextlistener, la cual obliga a implementar 2 métodos
         * - onquerytextsubmit -> Es el método que se ejecuta cuando el usuario le da a enter, ejecutará la función buscarHoteles
         * - onquerytextchange -> Se ejecuta cuando el texto se modifica, no le he dado funcionalidad
        */
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.v("", "presionado enter")
                //buscarHoteles()
                buscarHotelesDatosPorDefecto()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        configurarRecycler()
    }


    /**
     * Crea un thread que va a ejecutar en segundo plano la función de búsqueda de la API de tripadvisor
     * Si recibe datos, actualizará el recyclerview con esos datos
     */
    fun buscarHoteles() {
        val thread = Thread {
            var respuesta = tripadvisorAPIManager.locationSearch(searchView.query.toString(), Locale.getDefault().language)
            Log.v("", "respuesta recibida")
            if(!respuesta.isNullOrEmpty()) {
                Log.v("", "Datos api recibidos, modificando recycler")
                listaHoteles = respuesta
                adaptadorRecycler.notifyDataSetChanged()
            }
        }
        thread.start()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun buscarHotelesDatosPorDefecto() {
        for(i in 0 until 10) {
            var hotel = Hotel()
            hotel.name = ("hotel ${i}")
            hotel.address.city = ("ciudad ${i}")
            hotel.address.country = ("pais ${i}")
            listaHoteles.add(hotel)
        }
        listaHoteles.get(0).photos.mediumPhotos.add("https://dynamic-media-cdn.tripadvisor.com/media/photo-o/21/37/69/93/four-seasons-hotel-madrid.jpg?w=700&h=-1&s=1")
        listaHoteles.get(1).photos.mediumPhotos.add("https://www.momondo.es/himg/65/a8/5b/expediav2-4288651-6d557b-453569.jpg")
        listaHoteles.get(2).photos.mediumPhotos.add("https://upload.wikimedia.org/wikipedia/commons/7/7d/The_Westin_Palace_Madrid.jpg")
        listaHoteles.get(3).photos.mediumPhotos.add("https://www.hotelmadridpraga.com/wp-content/uploads/sites/2490/nggallery/content-img//Praga_39-1.jpg")
        listaHoteles.get(4).photos.mediumPhotos.add("https://www.ahstatic.com/photos/7438_ho_00_p_1024x768.jpg")
        listaHoteles.get(5).photos.mediumPhotos.add("https://media.timeout.com/images/105965911/750/562/image.jpg")
        listaHoteles.get(6).photos.mediumPhotos.add("https://cf.bstatic.com/xdata/images/hotel/max1024x768/470960002.jpg?k=a70e747872b27bbb5093759421705a033ff9522988c9d5668d4543aee721b45d&o=&hp=1")
        listaHoteles.get(7).photos.mediumPhotos.add("https://www.ahstatic.com/photos/9298_ho_00_p_1024x768.jpg")
        listaHoteles.get(8).photos.mediumPhotos.add("https://dynamic-media-cdn.tripadvisor.com/media/photo-o/2a/43/22/1e/only-you-boutique-hotel.jpg?w=1200&h=-1&s=1")
        listaHoteles.get(9).photos.mediumPhotos.add("https://www.indigomadrid.com/wp-content/uploads/pool.jpg")

        adaptadorRecycler.notifyDataSetChanged()
    }

    /**
     * Configura el recycler con su adaptador
     * Al crear el adaptador se le pasa la lista de los hoteles y una lambda
     * La lambda se ha declarado en el constructor del adapter (Hotel -> Unit), es la acción que se va a ejecutar cuando el
     * usuario clique en un viewHolder
     * - El dato Hotel es el hotel de la lista sobre el que se hará click
     * - Una vez que se ejecute la lambda, pasará ese objeto Hotel, y se ejecutará la función cambiarFragment*/
    fun configurarRecycler() {
        adaptadorRecycler = HotelesAdapter(listaHoteles) { hotel ->
            cambiarFragment(hotel)
        }
        recyclerView.adapter = adaptadorRecycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }


    /**pasa al fragment HotelFragment, y le pasa los datos del hotel seleccionado para mostrarlos en el fragment
     * No llama a su propio navegador, si no al del fragment padre "BuscarFragment". De esta forma es capaz de salir del tablayout en el que
     * está este fragment y buscarVuelosFragment*/
    fun cambiarFragment(hotel: Hotel) {
        val bundle = Bundle()
        bundle.putSerializable("hotel",hotel)
        findNavController()?.navigate(R.id.action_buscarHotelesFragment_to_hotelFragment)

    }
}
