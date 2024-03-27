package com.example.travelwithmeapp.fragments

import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.FragmentBuscarBinding


class BuscarFragment : Fragment() {
    private lateinit var binding: FragmentBuscarBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuscarBinding.inflate(inflater, container, false)

        inicializar()

        return binding.root

    }

    fun inicializar() {
        binding.hotelesImageButton.setOnClickListener() {
            findNavController()?.navigate(R.id.action_buscarFragment_to_buscarHotelesFragment)
            }

        binding.vuelosImageButton.setOnClickListener() {
            findNavController()?.navigate(R.id.action_buscarFragment_to_buscarVuelosFragment)
        }

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

       binding.toolbar.setNavigationOnClickListener() {
           requireActivity().onBackPressedDispatcher.onBackPressed()
       }

    }





}


