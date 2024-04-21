package com.example.travelwithmeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.travelwithmeapp.databinding.ViewholderBuscarhotelBinding
import com.example.travelwithmeapp.models.Hotel


class HotelesAdapter(
    val lista: List<Hotel>,
    val lambda: (Hotel) -> Unit)
    : RecyclerView.Adapter<HotelesAdapter.HotelHolder>() {

    inner class HotelHolder(val itemBinding: ViewholderBuscarhotelBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(hotel: Hotel) {
            itemBinding.textviewNombre.text = hotel.nombre
            itemBinding.textViewProvinciaPais.text = "${hotel.direccion.ciudad}, ${hotel.direccion.pais}"
            itemBinding.imagen.load(hotel?.fotos?.get(0)) {
                transformations(RoundedCornersTransformation(20f))
            }
            itemBinding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    lambda(lista.get(position))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelHolder {
        return HotelHolder(
            ViewholderBuscarhotelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HotelHolder, position: Int) {
        val hotel = lista.get(position)
        holder.bindItem(hotel)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

}