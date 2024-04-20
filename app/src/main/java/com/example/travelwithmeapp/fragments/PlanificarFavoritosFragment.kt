package com.example.travelwithmeapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.FragmentPlanificarFavoritosBinding
import com.example.travelwithmeapp.utils.Utilities


class PlanificarFavoritosFragment : Fragment() {

    private lateinit var binding: FragmentPlanificarFavoritosBinding
    private lateinit var utilities: Utilities

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
}