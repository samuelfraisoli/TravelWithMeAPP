package com.example.travelwithmeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.travelwithmeapp.databinding.FragmentPlanificarBinding


class PlanificarFragment : Fragment() {

        private lateinit var binding: FragmentPlanificarBinding

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentPlanificarBinding.inflate(inflater, container, false)
            return binding.root

        }

    }

