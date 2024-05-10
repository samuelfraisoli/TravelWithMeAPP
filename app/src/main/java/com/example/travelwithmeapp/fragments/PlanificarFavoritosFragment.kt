package com.example.travelwithmeapp.fragments

import android.os.Build
import android.os.Bundle
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
import com.example.travelwithmeapp.adapters.PlanificarFavoritosAdapter
import com.example.travelwithmeapp.databinding.FragmentPlanificarFavoritosBinding
import com.example.travelwithmeapp.models.Hotel
import com.example.travelwithmeapp.utils.TravelWithMeApiManager
import com.example.travelwithmeapp.utils.Utilities


class PlanificarFavoritosFragment : Fragment() {
    private lateinit var binding: FragmentPlanificarFavoritosBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adaptadorRecycler: PlanificarFavoritosAdapter

    private var listaHotelesFav = ArrayList<Hotel>()
    private var utilities = Utilities()
    private lateinit var travelWithMeApiManager: TravelWithMeApiManager


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
        utilities.crearToolbarMenuPrincipal(binding.toolbar.toolbarLayout, "Favoritos", binding.toolbar.toolbarLayoutTitle, activity as AppCompatActivity)
        configurarRecycler()
    }

    //todo completar con bd
    fun recogerHotelesFavDb() {
        //listaHotelesFav = datos db
        adaptadorRecycler.setData(listaHotelesFav)
    }



    fun configurarRecycler() {
        recyclerView = binding.recyclerFavoritos
        adaptadorRecycler = PlanificarFavoritosAdapter(listaHotelesFav) { hotel ->
            intentAHotelFrag(hotel)
        }
        recyclerView.adapter = adaptadorRecycler
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    fun intentAHotelFrag(hotel: Hotel) {
        val bundle = Bundle()
        bundle.putSerializable("hotel", hotel)
        findNavController()?.navigate(R.id.action_planificarFavoritosFragment_to_hotelFragment, bundle)
    }
}
