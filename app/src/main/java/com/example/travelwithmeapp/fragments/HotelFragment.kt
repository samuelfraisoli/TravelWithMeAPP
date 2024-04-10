package com.example.travelwithmeapp.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.travelwithmeapp.adapters.CarouselAdapter

import com.example.travelwithmeapp.databinding.FragmentHotelBinding
import com.google.android.material.carousel.CarouselSnapHelper

class HotelFragment : Fragment() {
    private lateinit var binding: FragmentHotelBinding
    private lateinit var carouselRecyclerView: RecyclerView
    private lateinit var listaImagenes: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHotelBinding.inflate(inflater, container, false)
        inicializar()
        return binding.root
    }

    private fun inicializar() {
        carouselRecyclerView = binding.carouselRecyclerView
        listaImagenes = ArrayList()
        inicializarListaImagenes()
        inicializarCarouselRecyclerView()
    }

    private fun inicializarCarouselRecyclerView() {
        CarouselSnapHelper().attachToRecyclerView(carouselRecyclerView)
        carouselRecyclerView.adapter = CarouselAdapter(listaImagenes)
    }

    private fun inicializarListaImagenes() {
        listaImagenes.apply {
            add("https://dynamic-media-cdn.tripadvisor.com/media/photo-o/28/99/89/9c/exterior-hotel-riu-plaza.jpg?w=700&h=-1&s=1")
            add("https://www.riu.com/blog/wp-content/uploads/2022/01/riu-plaza-espana-madrid.jpg")
            add("https://www.muchoturismo.com/img/hotel-riu-plaza-espana-madrid-habitacion-deluxe-kind.jpg")
            add("https://cf.bstatic.com/xdata/images/hotel/max1024x768/217552037.jpg?k=396e6abf6fc4d566a0fcad9bdeec17fcd61c17f021514f9b66b0d93672bf9424&o=&hp=1")
            add("https://www.bestmadridhotels.com/data/Pictures/OriginalPhoto/12844/1284405/1284405139/madrid-hotel-riu-plaza-espana-picture-6.JPEG")
        }
    }
}
