package com.example.travelwithmeapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.models.Hotel

/**
 * Adapter for photo carousel of ExplorarFragment
 *
 * @author Samuel Fraisoli
 */

class CarouselExplorarAdapter (
    val lista: ArrayList<Hotel>,
    val lambda: (Hotel) -> Unit)
    : RecyclerView.Adapter<CarouselExplorarAdapter.CarouselViewHolder>() {
    inner class CarouselViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val carouselImageView: AppCompatImageView =
            view.findViewById(R.id.carouselImageView)

        fun bind(imageUrl: String) {
            carouselImageView.load(imageUrl) {
                transformations(RoundedCornersTransformation(20f))
            }

            carouselImageView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    lambda(lista.get(position))
                }
            }
        }
    }
    fun setData(nuevaLista: ArrayList<Hotel>) {
        Log.v("carousel setdata", "carousel setdata")
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged() // Notificar al RecyclerView de que los datos han cambiado
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        return CarouselViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.image_carousel, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val hotel = lista.get(position)
        holder.bind(hotel.fotos.get(0))
    }
}