package com.example.travelwithmeapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.travelwithmeapp.databinding.FragmentPlanificarBinding
import com.example.travelwithmeapp.utils.Utilities


class PlanificarFragment : Fragment() {

    private lateinit var binding: FragmentPlanificarBinding
    private lateinit var utilities: Utilities

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
    }

    fun inicializar() {
        utilities = Utilities()

        utilities.crearToolbarMenuPrincipal(
            binding.toolbar.toolbarLayout,
            "Mis planes",
            binding.toolbar.toolbarLayoutTitle,
            activity as AppCompatActivity
        )

    }
}

