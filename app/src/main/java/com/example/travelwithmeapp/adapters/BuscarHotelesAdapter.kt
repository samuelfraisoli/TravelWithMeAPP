package com.example.travelwithmeapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.travelwithmeapp.databinding.ViewholderBuscarhotelBinding
import com.example.travelwithmeapp.models.Hotel
import com.example.travelwithmeapp.utils.Utilities

/**
 * Adapter for recyclerview in BuscarHotelesFragment
 *
 * @author Samuel Fraisoli
 */

class BuscarHotelesAdapter(
    val lista: ArrayList<Hotel>,
    val lambda: (Hotel) -> Unit)
    : RecyclerView.Adapter<BuscarHotelesAdapter.HotelHolder>() {

    val utilities = Utilities()

    inner class HotelHolder(val itemBinding: ViewholderBuscarhotelBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(hotel: Hotel) {
            itemBinding.textviewNombre.text = hotel.nombre
            itemBinding.textViewProvinciaPais.text = "${hotel.direccion.ciudad}, ${hotel.direccion.pais}"
            itemBinding.textViewDescripcion.text = hotel.detalles.descripcion

            itemBinding.textViewPrecio.text = "${utilities.quitarDecimalesSiAcabaEnCero(hotel.detalles.precio)}€"
            if(hotel.fotos.size > 0) {
                try {
                    itemBinding.imagen.load(hotel?.fotos?.get(0)) {
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
                    lambda(lista.get(position))
                }
            }
        }
    }
    fun setData(nuevaLista: ArrayList<Hotel>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged() // Notificar al RecyclerView de que los datos han cambiado
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