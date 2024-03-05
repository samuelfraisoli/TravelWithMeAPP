package com.example.travelwithmeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.travelwithmeapp.databinding.FragmentCuentaBinding


class CuentaFragment : Fragment() {
        private lateinit var binding: FragmentCuentaBinding

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentCuentaBinding.inflate(inflater, container, false)
            return binding.root

        }

    }