package com.example.travelwithmeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.travelwithmeapp.adapters.ViewPagerAdapterBuscarFrag
import com.example.travelwithmeapp.databinding.FragmentBuscarBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class BuscarFragment : Fragment() {
    private lateinit var binding: FragmentBuscarBinding
    //tablayout es el layout que crea el menú con Hoteles y Vuelos, y permite que al seleccionar cada uno se muestre un fragment distinto. Lo estamos usando con viewpager, así que él no tendrá esta funcionalidad, sino que la tiene el adaptador del viewpager
    private lateinit var tabLayout: TabLayout
    //viewPager es el objeto que controla la animación entre los tabs del tablayout
    private lateinit var viewPager : ViewPager2
    //el adaptador del viewPager controla qué fragment se muestra dependiendo del elemento del menú que se pulse
    private lateinit var viewPagerAdapter: ViewPagerAdapterBuscarFrag


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
        viewPager = binding.viewPagerBuscarFrag
        viewPagerAdapter = ViewPagerAdapterBuscarFrag(this)
        tabLayout = binding.tabLayoutBuscarFrag

        viewPager.setAdapter(viewPagerAdapter)

        //tabLayoutMediator es el objeto que sincroniza el viewPager y el adapter, para que al hacer swipe en la pantalla para cambiar de un fragment a otro, también se actualice el menú (en vez de resaltar Hoteles, que se resalte Vuelos, o al revés). Si no lo añadieramos, eso no pasaría
        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            when(position) {
                0 -> tab.text = "Hoteles"
                1 -> tab.text = "Vuelos"
            }
        }.attach()
    }
}