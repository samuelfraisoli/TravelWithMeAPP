package com.example.travelwithmeapp.adapters
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.ViewholderBuscarvueloBinding
import com.example.travelwithmeapp.models.Vuelo
import com.example.travelwithmeapp.utils.Utilities
import java.time.LocalTime


/**
 * Adapter class for displaying flights in a RecyclerView for search results.
 * Handles binding flight data to the UI and capturing click events.
 *
 * @property lista The list of flights to display.
 * @property lambda The lambda function to execute when a flight item is clicked.
 *
 * @author Samuel Fraisoli
 */
class BuscarVuelosAdapter(
    val lista: ArrayList<Vuelo>,
    val lambda: (Vuelo) -> Unit)
    : RecyclerView.Adapter<BuscarVuelosAdapter.BuscarVueloHolder>() {

    val utilities = Utilities()

    /**
     * Inner ViewHolder class to hold and bind views for each flight item.
     * @param itemBinding The ViewholderBuscarvueloBinding object for the flight item layout.
     */
    inner class BuscarVueloHolder(val itemBinding: ViewholderBuscarvueloBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
            val utilities = Utilities()

        /**
         * Binds flight data to the views within the ViewHolder.
         * @param vuelo The Vuelo object containing data to bind.
         */
        @RequiresApi(Build.VERSION_CODES.O)
        fun bindItem(vuelo: Vuelo) {

            elegirIcono(vuelo, itemBinding)
            itemBinding.escala.text = vuelo.getNumeroEscalas()
            itemBinding.letrasCiudadOrigen.text = "${vuelo.getAeropuertoOrigen().ciudadAbrev}"
            itemBinding.letrasCiudadDestino.text = "${vuelo.getAeropuertoDestino().ciudadAbrev}"
            itemBinding.aerolinea.text = "${vuelo.aerolinea}"
            itemBinding.precio.text = "${vuelo.precio} €"
            itemBinding.tiempoVuelo.text = vuelo.getDuracionTotalFormatoHHhMMm()
            itemBinding.hora.text = "${utilities.formatoOffsetDateTimeHHMM(vuelo.getPrimerTrayecto().fechaSalida)} - ${utilities.formatoOffsetDateTimeHHMM(vuelo.getUltimoTrayecto().fechaLlegada)}"

            // Set OnClickListener to handle click events on each ViewHolder
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
     * @param nuevaLista The new list of flights to display.
     */
    fun setData(nuevaLista: ArrayList<Vuelo>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged() // Notificar al RecyclerView de que los datos han cambiado
    }

    /**
     * Chooses the appropriate icon for the flight based on departure and arrival times.
     *
     * @param vuelo The Vuelo object representing the flight.
     * @param itemBinding The ViewholderBuscarvueloBinding object for the flight item layout.
     */
    fun elegirIcono(vuelo: Vuelo, itemBinding: ViewholderBuscarvueloBinding) {
        var imagenId = 0
        // Converts OffsetDateTime objects to local times (e.g., from 14:00 UTC+2 to just 14:00)
        val horaSalida = vuelo.getPrimerTrayecto().fechaSalida.toLocalTime()
        val horaLlegada = vuelo.getUltimoTrayecto().fechaLlegada.toLocalTime()

        // Checks if departure and arrival times are in the morning or evening
        val horaAmanecer = LocalTime.of(7, 0)
        val horaAtardecer = LocalTime.of(19, 0)
        var salidaDiurnaFlag: Boolean;
        var llegadaDiurnaFlag: Boolean;

        if(utilities.horaEstaEntre(horaSalida, horaAmanecer, horaAtardecer)) {
            salidaDiurnaFlag = true;
        }
        else {
            salidaDiurnaFlag = false;
        }

        if(utilities.horaEstaEntre(horaLlegada, horaAmanecer, horaAtardecer)) {
            llegadaDiurnaFlag = true;
        }
        else {
            llegadaDiurnaFlag = false;
        }

        // Sets appropriate icon based on whether the flight is daytime or nighttime
        if(salidaDiurnaFlag && llegadaDiurnaFlag) {
            itemBinding.imagen.setImageResource(R.drawable.sun_icon)
            ImageViewCompat.setImageTintList(itemBinding.imagen, ColorStateList.valueOf(Color.parseColor("#FFCC00")));
        }
        else if(!salidaDiurnaFlag && !llegadaDiurnaFlag) {
            itemBinding.imagen.setImageResource(R.drawable.moon_icon);
            ImageViewCompat.setImageTintList(itemBinding.imagen, ColorStateList.valueOf(Color.parseColor("#5856D6")));
        }
        else {
            itemBinding.imagen.setImageResource(R.drawable.sun_moon_icon)
            ImageViewCompat.setImageTintList(itemBinding.imagen, ColorStateList.valueOf(Color.parseColor("#FF3B30")));
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuscarVueloHolder {
        return BuscarVueloHolder(
            ViewholderBuscarvueloBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: BuscarVueloHolder, position: Int) {
        val vuelo = lista.get(position)
        holder.bindItem(vuelo)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

}