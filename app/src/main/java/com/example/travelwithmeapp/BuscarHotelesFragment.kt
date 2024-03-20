package com.example.travelwithmeapp

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelwithmeapp.adapters.HotelesAdapter
import com.example.travelwithmeapp.databinding.FragmentBuscarHotelesBinding
import com.example.travelwithmeapp.models.Hotel
import java.util.Locale


class BuscarHotelesFragment : Fragment() {
    private lateinit var binding: FragmentBuscarHotelesBinding
    private lateinit var searchView: SearchView
    private lateinit var busqueda: String
    private lateinit var fechaEntradaTextView: Button
    private lateinit var fechaSalidaTextView: Button
    private lateinit var fechaEntrada: Calendar
    private lateinit var fechaSalida:Calendar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adaptadorRecycler: HotelesAdapter
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

        //se pasa la view, que es el propio botón, para que cuando el usuario seleccione una fecha en el datepicker, la fecha que aparece en el botón cambie
        // - lanzan la función buscarHoteles, que hará una petición a la API para cargar los hoteles
        fechaEntradaTextView.setOnClickListener {view ->
            var fecha = lanzarDatePickerDialog(view)
            fechaEntrada = fecha
            buscarHoteles()
        }
        fechaSalidaTextView.setOnClickListener { view ->
            var fecha = lanzarDatePickerDialog(view)
            fechaSalida = fecha
            buscarHoteles()
        }

        //se crea un objeto que instancia la interfaz onquerytextlistener, la cual obliga a implementar 2 métodos
        // - onquerytextsubmit -> Es el método que se ejecuta cuando el usuario le da a enter, ejecutará la función buscarHoteles
        // - onquerytextchange -> Se ejecuta cuando el texto se modifica, no le he dado funcionalidad
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                buscarHoteles()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })

        rellenarListaRecycler()
        configurarRecycler()
    }


    /** Primero crea el objeto fechaSeleccionada, que almacenará la fecha que el usuario seleccione
     * (por ahora se inicializa con el valor del día actual para que no tenga valor null)
     * Luego usa la clase Calendar para coger el año, mes y día actuales.
     * Luego crea un datePickerDialog, un diálogo con un calendario para seleccionar una fecha
     * Inicializa ese calendario con el año, mes y día que había recogido
     * Le añade un OnDateSetListener, que escucha cuándo el usuario selecciona una fecha
     * Guarda el año, mes y día que selecciona en la variable fechaSeleccionada
     * Finalmente retorna la fecha que ha seleccionado el usuario en el dialog
    */
    fun lanzarDatePickerDialog(view: View) : Calendar {
        var fechaSeleccionada = Calendar.getInstance()
        var calendar = Calendar.getInstance()
        var ano = calendar.get(Calendar.YEAR)
        var mes = calendar.get(Calendar.MONTH)
        var dia = calendar.get(Calendar.DAY_OF_MONTH)

        var datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, anoSeleccionado, mesSeleccionado, diaSeleccionado ->
                fechaSeleccionada = Calendar.getInstance()
                fechaSeleccionada.set(anoSeleccionado, mesSeleccionado, diaSeleccionado)
                view as Button
                view.setText(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(fechaSeleccionada.time))
            },
            ano,
            mes,
            dia
        )
        datePickerDialog.show()
        return fechaSeleccionada
    }

    fun buscarHoteles() {
        //todo buscar con el nombre y las fechas proporcionadas
    }



    //todo cambiar por datos recogidos de la API
    fun rellenarListaRecycler() {
        for (i in 1..10) {
            val hotel = Hotel(
                id = i.toLong(),
                nombre = "nombre $i",
                descripcion = "descripcion $i",
                direccion = "direccionl $i",
                provincia = "provincia $i",
                pais = "pais $i",
                telefono = "telefono $i",
                web = "web $i",
                imagenes = arrayListOf("imagen $i", "imagen $i")
            )
            listaHoteles.add(hotel)
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
        adaptadorRecycler = HotelesAdapter(listaHoteles) { hotel ->
            cambiarFragment(hotel)
        }
        recyclerView.adapter = adaptadorRecycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }


    /**pasa al fragment HotelFragment, y le pasa los datos del hotel seleccionado para mostrarlos en el fragment
     * No llama a su propio navegador, si no al del fragment padre "BuscarFragment". De esta forma es capaz de salir del tablayout en el que
     * está este fragment y buscarVuelosFragment*/
    //todo arreglar, no funciona
    fun cambiarFragment(hotel: Hotel) {
        val bundle = Bundle()
        bundle.putSerializable("hotel",hotel)
        findNavController()?.navigate(R.id.action_buscarHotelesFragment_to_hotelFragment)

    }
}
