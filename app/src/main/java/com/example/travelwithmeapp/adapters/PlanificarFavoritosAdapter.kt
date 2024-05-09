package com.example.travelwithmeapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.travelwithmeapp.databinding.ViewholderPlanificarFavoritosBinding
import com.example.travelwithmeapp.models.Hotel

class PlanificarFavoritosAdapter(
    private val listaHotelesFav: ArrayList<Hotel>,
    private val lambda: (Hotel) -> Unit)
    : RecyclerView.Adapter<PlanificarFavoritosAdapter.HotelViewHolder>() {

    inner class HotelViewHolder(val itemBinding: ViewholderPlanificarFavoritosBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(hotel: Hotel) {
            itemBinding.textviewNombre.text = hotel.nombre
            itemBinding.textViewProvinciaPais.text = "${hotel.direccion.ciudad}, ${hotel.direccion.pais}"
            if(hotel.fotos.size > 0) {
                try {
                    itemBinding.imagen.load(hotel.fotos[0]) {
                        transformations(RoundedCornersTransformation(20f))
                    }
                }
                catch(e: Exception) {
                    Log.v("Error al cargar imagen", "${e.message}")
                }
            }



            itemBinding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    lambda(listaHotelesFav[position])
                }
            }
        }
    }

    fun setData(nuevaLista: ArrayList<Hotel>) {
        listaHotelesFav.clear()
        listaHotelesFav.addAll(nuevaLista)
        notifyDataSetChanged() // Notificar al RecyclerView de que los datos han cambiado
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        return HotelViewHolder(
            ViewholderPlanificarFavoritosBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        val hotel = listaHotelesFav[position]
        holder.bindItem(hotel)
    }

    override fun getItemCount(): Int {
        return listaHotelesFav.size
    }

    fun obtenerHotelesFavoritos(): ArrayList<Hotel> {
        return listaHotelesFav
    }
}