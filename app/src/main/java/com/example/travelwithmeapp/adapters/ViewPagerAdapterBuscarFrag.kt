package com.example.travelwithmeapp.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.travelwithmeapp.BuscarHotelesFragment
import com.example.travelwithmeapp.BuscarVuelosFragment

class ViewPagerAdapterBuscarFrag(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> {return BuscarHotelesFragment() }
            1 -> {return BuscarVuelosFragment()}
            else -> {return BuscarHotelesFragment()}
        }

    }
}