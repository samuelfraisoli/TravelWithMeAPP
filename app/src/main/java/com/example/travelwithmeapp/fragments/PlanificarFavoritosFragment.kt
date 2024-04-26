package com.example.travelwithmeapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.adapters.PlanificarFavoritosAdapter
import com.example.travelwithmeapp.databinding.FragmentPlanificarFavoritosBinding
import com.example.travelwithmeapp.models.Plan
import com.example.travelwithmeapp.utils.Utilities


class PlanificarFavoritosFragment : Fragment() {

    private lateinit var binding: FragmentPlanificarFavoritosBinding
    private lateinit var utilities: Utilities
    private var listaPlanes = ArrayList<Plan>() // PRUEBA PARA VER COMO QUEDAN LOS FAVS, BORRAR EN EL FUTURO
    private lateinit var planificarFavoritosAdapter: PlanificarFavoritosAdapter // PRUEBA PARA VER COMO QUEDAN LOS FAVS, BORRAR EN EL FUTURO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlanificarFavoritosBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()

        setupRecyclerView() // PRUEBA PARA VER COMO QUEDAN LOS FAVS, BORRAR EN EL FUTURO
        cargarDatos() // PRUEBA PARA VER COMO QUEDAN LOS FAVS, BORRAR EN EL FUTURO

        binding.buttonPlanes.setOnClickListener {
            findNavController().navigate(R.id.action_planificarFavoritosFragment_to_planificarFragment)
        }
    }

    fun inicializar() {
        utilities = Utilities()

        utilities.crearToolbarMenuPrincipal(
            binding.toolbar.toolbarLayout,
            "Mis planes favoritos",
            binding.toolbar.toolbarLayoutTitle,
            activity as AppCompatActivity
        )
    }

    // PRUEBA PARA VER COMO QUEDAN LOS FAVS, BORRAR EN EL FUTURO
    private fun setupRecyclerView() {
        planificarFavoritosAdapter = PlanificarFavoritosAdapter(requireContext(), listaPlanes)
        binding.recyclerPlanesFav.apply {
            adapter = planificarFavoritosAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun cargarDatos() {
        listaPlanes.add(Plan("Hace un momento", "PLan A FAVORITO", "Descripción Plan A", "100€", false))
        listaPlanes.add(Plan("Hace 2 horas", "Plan B FAVORITO", "Descripción del Plan B", "150€", false))
        listaPlanes.add(Plan("Hace 1 día", "Plan C FAVORITO", "Descripción del Plan C", "15€", false))
        listaPlanes.add(Plan("Hace 56 minutos", "Plan D FAVORITO", "Descripción del Plan D", "254€", false))
    }
}