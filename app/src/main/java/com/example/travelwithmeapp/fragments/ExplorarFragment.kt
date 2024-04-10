package com.example.travelwithmeapp.fragments

import CarouselAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.FragmentExplorarBinding
import com.google.android.material.carousel.CarouselSnapHelper

class ExplorarFragment : Fragment() {

    private lateinit var binding: FragmentExplorarBinding
    //private lateinit var carouselRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExplorarBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Pulsacion para el botón explorar hoteles
        binding.botonExplorarHoteles.setOnClickListener {
            findNavController().navigate(R.id.action_explorarFragment_to_buscarHotelesFragment)
        }

        binding.botonExplorarVuelos.setOnClickListener {
            findNavController().navigate(R.id.action_explorarFragment_to_buscarVuelosFragment)
        }

        binding.botonForos.setOnClickListener {
            findNavController().navigate(R.id.action_explorarFragment_to_opinionFragment)
        }

        binding.botonExplorarCosasQueHacer.setOnClickListener {
            findNavController().navigate(R.id.action_explorarFragment_to_cosasQueHacerFragment)
        }
    }
}
