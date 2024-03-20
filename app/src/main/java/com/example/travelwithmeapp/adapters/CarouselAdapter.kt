package com.example.travelwithmeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.travelwithmeapp.databinding.ViewholderCarouselBinding

class CarouselAdapter(
    val lista: List<String>)
    : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {
    inner class CarouselViewHolder(val itemBinding: ViewholderCarouselBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

            fun bindItem(imageUrl: String) {
                itemBinding.carouselImageView.load(imageUrl) {
                    transformations(RoundedCornersTransformation(8f))
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        return CarouselViewHolder(ViewholderCarouselBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun getItemCount(): Int {
       return  lista.size
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val imagen = lista.get(position)
        holder.bindItem(imagen)
    }
}