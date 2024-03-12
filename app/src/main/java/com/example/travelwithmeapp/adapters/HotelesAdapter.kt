package com.example.travelwithmeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelwithmeapp.databinding.ViewholderHotelBinding
import com.example.travelwithmeapp.models.Hotel


class HotelesAdapter(val lista: List<Hotel>) : RecyclerView.Adapter<HotelesAdapter.HotelHolder>() {

    //todo hacer que la interfaz funcione o usar lambdas
    private lateinit var listener: OnItemListener

    inner class HotelHolder(val itemBinding: ViewholderHotelBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(hotel: Hotel) {
            itemBinding.textviewNombre.text = hotel.nombre
            itemBinding.textViewDescripcion.text = hotel.descripcion
            //todo añadir imagen con volley
            //itemBinding.imagen.   = hotel.arrayImagenes...
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelHolder {
        return HotelHolder(ViewholderHotelBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HotelHolder, position: Int) {
        val hotel = lista.get(position)
        holder.bindItem(hotel)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    interface OnItemListener {
        fun onItemClick(hotel:Hotel)
    }
}