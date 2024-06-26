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
import com.example.travelwithmeapp.activities.MainActivity
import com.example.travelwithmeapp.adapters.BuscarHotelesAdapter
import com.example.travelwithmeapp.databinding.FragmentPlanificarFavoritosBinding
import com.example.travelwithmeapp.models.Hotel
import com.example.travelwithmeapp.utils.FirebaseFirestoreManager
import com.example.travelwithmeapp.utils.TravelWithMeApiManager
import com.example.travelwithmeapp.utils.Utilities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *  Fragment for displaying favorite hotels planned by the user.
 *
 * @author Matías Martínez
 */
class PlanificarFavoritosFragment : Fragment() {
    private lateinit var binding: FragmentPlanificarFavoritosBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adaptadorRecycler: BuscarHotelesAdapter

    private var listaIdsHoteles = ArrayList<String>()
    private var listaHotelesFav = ArrayList<Hotel>()
    private var utilities = Utilities()
    private lateinit var travelWithMeApiManager: TravelWithMeApiManager
    private lateinit var firebaseFirestoreManager: FirebaseFirestoreManager

    private var uid = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlanificarFavoritosBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inicializar()
        recogerHotelesFavDb()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun inicializar() {
        travelWithMeApiManager = TravelWithMeApiManager(requireContext())
        firebaseFirestoreManager = FirebaseFirestoreManager(requireContext(), binding.root)
        utilities.crearToolbarMenuPrincipal(
            binding.toolbar.toolbarLayout,
            "Favoritos",
            binding.toolbar.toolbarLayoutTitle,
            activity as AppCompatActivity
        )
        configurarRecycler()
    }

    // CARGAR LISTA FAVORITOS
    /**
     * Primero recoge la uid del usuario de la act main. Luego, recoge la lista de ids de hoteles favoritos de firebase. Luego, con esa lista, recoge por id los hoteles
     * de la api y carga el recycler con ellos
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun recogerHotelesFavDb() {
        recogerUidActMain()
        if (uid.isEmpty()) {
            return
        }
        firebaseFirestoreManager.recogerFavoritos(uid) { respuesta ->
            if (respuesta != null) {
                listaIdsHoteles = respuesta
                buscarHotelesPorIdAPIcorrutina(listaIdsHoteles)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun buscarHotelesPorIdAPIcorrutina(listaIdsHoteles: ArrayList<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var nuevaListaFavoritos = ArrayList<Hotel>()
                for (id in listaIdsHoteles) {
                    var hotel = travelWithMeApiManager.buscarHotelPorIdParent(id.toLong())
                    nuevaListaFavoritos.add(hotel)
                }
                withContext(Dispatchers.Main) {
                    adaptadorRecycler.setData(nuevaListaFavoritos)
                }


            } catch (e: Exception) {
                Log.v("BuscarHotelesPorIdAPIcorrutina", "${e.message}")
                view?.let { utilities.lanzarSnackBarCorto("Error cargar los resultados", it) };
            }
        }
    }


    fun recogerUidActMain() {
        if (activity != null && activity is MainActivity) {
            uid = (activity as MainActivity).user.uid
        }
    }


    // RECYCLER
    fun configurarRecycler() {
        recyclerView = binding.recyclerFavoritos
        adaptadorRecycler = BuscarHotelesAdapter(listaHotelesFav) { hotel ->
            intentAHotelFrag(hotel)
        }
        recyclerView.adapter = adaptadorRecycler
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }


    // INTENTS
    fun intentAHotelFrag(hotel: Hotel) {
        val bundle = Bundle()
        bundle.putSerializable("hotel", hotel)
        findNavController()?.navigate(
            R.id.action_planificarFavoritosFragment_to_hotelFragment,
            bundle
        )
    }
}

