package com.example.travelwithmeapp.adapters

import android.annotation.SuppressLint
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
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class VuelosAdapter(
    val lista: List<Vuelo>,
    val lambda: (Vuelo) -> Unit)
    : RecyclerView.Adapter<VuelosAdapter.BuscarVueloHolder>() {

    inner class BuscarVueloHolder(val itemBinding: ViewholderBuscarvueloBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
            val utilities = Utilities()
        @RequiresApi(Build.VERSION_CODES.O)
        fun bindItem(vuelo: Vuelo) {

            //doy formato a las escalas
            var escalasText = ""
            if(vuelo.trayectos.size == 1 && vuelo.trayectos.size == 0) { escalasText = "Sin escalas" }
            else if(vuelo.trayectos.size == 2) { escalasText = "1 escala" }
            else { escalasText = "${vuelo.trayectos.size + 1} escalas" }
            itemBinding.escala.text = escalasText

            itemBinding.letrasCiudadOrigen.text = "${vuelo.origen.ciudadAbrev}"
            itemBinding.letrasCiudadDestino.text = "${vuelo.destino.ciudadAbrev}"
            itemBinding.aerolinea.text = "${vuelo.aerolinea}"
            itemBinding.precio.text = "${vuelo.precio} € "
            itemBinding.tiempoVuelo.text = utilities.formatoDurationHHhMMm(vuelo.duracion)
            itemBinding.hora.text = "${utilities.formatoDateHHMM(vuelo.fechaSalida)} - ${utilities.formatoDateHHMM(vuelo.fechaLlegada)}"

            //comparo las fechas
            val horaSalida = vuelo.fechaSalida.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
            val horaLlegada = vuelo.fechaSalida.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()

            //selecciono una imagen dependiendo de si el vuelo es durante el dia, la noche o ambos
            var imagenId = 0
            if(horaLlegada.isBefore(LocalTime.of(19,0)) && horaSalida.isAfter(LocalTime.of(7,0))) {
                itemBinding.imagen.setImageResource(R.drawable.sun_icon)
                ImageViewCompat.setImageTintList(itemBinding.imagen, ColorStateList.valueOf(Color.parseColor("#FFCC00")));
            }
            else if(horaLlegada.isAfter(LocalTime.of(19,0)) && horaSalida.isBefore(LocalTime.of(7,0))) {
                itemBinding.imagen.setImageResource(R.drawable.moon_icon)
                ImageViewCompat.setImageTintList(itemBinding.imagen, ColorStateList.valueOf(Color.parseColor("#5856D6")));
            }
            else {
                itemBinding.imagen.setImageResource(R.drawable.sun_moon_icon)
                ImageViewCompat.setImageTintList(itemBinding.imagen, ColorStateList.valueOf(Color.parseColor("#FF3B30")));
            }




            //añado un onclicklistener a cada viewgholder
            itemBinding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    lambda(lista.get(position))
                }
            }
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