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
import com.example.travelwithmeapp.adapters.PlanificarAdapter
import com.example.travelwithmeapp.databinding.FragmentPlanificarBinding
import com.example.travelwithmeapp.models.Plan
import com.example.travelwithmeapp.utils.Utilities


class PlanificarFragment : Fragment() {

    private lateinit var binding: FragmentPlanificarBinding
    private lateinit var utilities: Utilities
    private var listaPlanes = ArrayList<Plan>()
    private lateinit var planificarAdapter: PlanificarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlanificarBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
        setupRecyclerView()
        cargarDatos()


        binding.buttonFavoritos.setOnClickListener {
            findNavController().navigate(R.id.action_planificarFragment_to_planificarFavoritosFragment)
        }
    }

    fun inicializar() {
        utilities = Utilities()

        utilities.crearToolbarMenuPrincipal(
            binding.toolbar.toolbarLayout,
            "Todos los planes",
            binding.toolbar.toolbarLayoutTitle,
            activity as AppCompatActivity
        )
    }

    private fun setupRecyclerView() {
        planificarAdapter = PlanificarAdapter(requireContext(), listaPlanes)
        binding.recyclerPlanes.apply {
            adapter = planificarAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun cargarDatos() {
        listaPlanes.add(Plan("Hace un momento", "PLan A", "Descripción Plan A", "100€", false))
        listaPlanes.add(Plan("Hace 2 horas", "Plan B", "Descripción del Plan B", "150€", false))
        listaPlanes.add(Plan("Hace 1 día", "Plan C", "Descripción del Plan C", "15€", false))
        listaPlanes.add(Plan("Hace 56 minutos", "Plan D", "Descripción del Plan D", "254€", false))
    }
}

