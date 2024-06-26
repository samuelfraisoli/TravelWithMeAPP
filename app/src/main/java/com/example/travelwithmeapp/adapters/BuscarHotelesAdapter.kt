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
 * Adapter class for displaying hotels in a RecyclerView for search results.
 * Handles binding hotel data to the UI and capturing click events.
 *
 * @property lista The list of hotels to display.
 * @property lambda The lambda function to execute when a hotel item is clicked.
 *
 * @author Samuel Fraisoli
 */
class BuscarHotelesAdapter(
    val lista: ArrayList<Hotel>,
    val lambda: (Hotel) -> Unit)
    : RecyclerView.Adapter<BuscarHotelesAdapter.HotelHolder>() {

    val utilities = Utilities()

    /**
     * Inner ViewHolder class to hold and bind views for each hotel item.
     * @param itemBinding The ViewholderBuscarhotelBinding object for the hotel item layout.
     */
    inner class HotelHolder(val itemBinding: ViewholderBuscarhotelBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        /**
         * Binds hotel data to the views within the ViewHolder.
         * @param hotel The Hotel object containing data to bind.
         */
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

    /**
     * Sets new data to be displayed in the RecyclerView.
     * @param nuevaLista The new list of hotels to display.
     */
    fun setData(nuevaLista: ArrayList<Hotel>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged()
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