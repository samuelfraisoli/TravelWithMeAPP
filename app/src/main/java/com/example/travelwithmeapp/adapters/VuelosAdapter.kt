package com.example.travelwithmeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.ViewholderVueloBinding
import com.example.travelwithmeapp.models.Vuelo

class VuelosAdapter(
    val lista: List<Vuelo>,
    val lambda: (Vuelo) -> Unit)
    : RecyclerView.Adapter<VuelosAdapter.VueloHolder>() {

    inner class VueloHolder(val itemBinding: ViewholderVueloBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(vuelo: Vuelo) {
            itemBinding.imagen.setImageResource(R.drawable.location_icon)

            itemBinding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    lambda(lista.get(position))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VueloHolder {
        return VueloHolder(
            ViewholderVueloBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VueloHolder, position: Int) {
        val vuelo = lista.get(position)
        holder.bindItem(vuelo)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

}