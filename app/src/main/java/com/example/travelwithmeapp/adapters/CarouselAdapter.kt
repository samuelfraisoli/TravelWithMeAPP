package com.example.travelwithmeapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.ViewholderCarouselBinding
import com.example.travelwithmeapp.models.Hotel

/**
 * Adapter for photo carousels of the application
 *
 * @author Samuel Fraisoli
 */

class CarouselAdapter(
    val lista: List<String>)
 : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {
    inner class CarouselViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val carouselImageView: AppCompatImageView =
            view.findViewById(R.id.carouselImageView)

        fun bind(imageUrl: String) {
            carouselImageView.load(imageUrl) {
                transformations(RoundedCornersTransformation(20f))
            }
        }
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
        val imagen = lista.get(position)
        holder.bind(imagen)
    }
}