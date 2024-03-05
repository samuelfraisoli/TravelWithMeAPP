package com.example.travelwithmeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.travelwithmeapp.databinding.FragmentResenaBinding


class ResenaFragment : Fragment() {
    private lateinit var binding: FragmentResenaBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResenaBinding.inflate(inflater, container, false)
        return binding.root

    }
}

