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
 * Adapter class for displaying hotel images in a clicable carousel.
 *
 * @property lista The list of hotels to display in the carousel.
 * @property lambda Lambda function to handle click events on carousel images.
 *
 * @author Samuel Fraisoli
 */

class CarouselClicableAdapter (
    val lista: ArrayList<Hotel>,
    val lambda: (Hotel) -> Unit)
    : RecyclerView.Adapter<CarouselClicableAdapter.CarouselViewHolder>() {

    /**
     * ViewHolder class for holding and binding hotel image views.
     * @param view The view containing the carousel image layout.
     */
    inner class CarouselViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val carouselImageView: AppCompatImageView =
            view.findViewById(R.id.carouselImageView)

        /**
         * Binds a hotel image URL to the ImageView using Coil library with rounded corners transformation.
         * @param imageUrl The URL of the hotel image to load and display.
         */
        fun bind(imageUrl: String) {
            carouselImageView.load(imageUrl) {
                transformations(RoundedCornersTransformation(20f))
            }

            // Set click listener to handle hotel item click events
            carouselImageView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    lambda(lista.get(position))
                }
            }
        }
    }

    /**
     * Updates the data set with a new list of hotels.
     * @param nuevaLista The new list of hotels to update the carousel with.
     */
    fun setData(nuevaLista: ArrayList<Hotel>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged()
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