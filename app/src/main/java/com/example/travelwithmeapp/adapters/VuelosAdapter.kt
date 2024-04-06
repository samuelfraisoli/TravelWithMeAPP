package com.example.travelwithmeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.ViewholderVueloBinding
import com.example.travelwithmeapp.models.Vuelo
import java.time.LocalTime
import java.time.ZoneId
import java.time.Duration

class VuelosAdapter(
    val lista: List<Vuelo>,
    val lambda: (Vuelo) -> Unit)
    : RecyclerView.Adapter<VuelosAdapter.VueloHolder>() {

    inner class VueloHolder(val itemBinding: ViewholderVueloBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(vuelo: Vuelo) {

            var escalasText = ""
            if(vuelo.escalas.size == 0) {
                escalasText = "Sin escalas"
            }
            else if(vuelo.escalas.size == 1) {
                escalasText = "1 escala"
            }
            else {
                escalasText = "${vuelo.escalas.size} escalas"
            }

            var horas: Int = 0
            var minutos: Int = 0

            vuelo.duracion.toComponents { h, m, s, ns ->
                horas = h.toInt()
                minutos = m
            }



            val horaSalida = vuelo.fechaSalida.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
            val horaLlegada = vuelo.fechaSalida.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()

            var imagenId = 0
            if(horaLlegada.isBefore(LocalTime.of(19,0)) && horaSalida.isAfter(LocalTime.of(7,0))) {
                itemBinding.imagen.setImageResource(R.drawable.sun_icon)
            }

            else if(horaLlegada.isAfter(LocalTime.of(19,0)) && horaSalida.isBefore(LocalTime.of(7,0))) {
                itemBinding.imagen.setImageResource(R.drawable.moon_icon)
            }

            else {
                itemBinding.imagen.setImageResource(R.drawable.sun_moon_icon)
            }



            itemBinding.hora.text = "${horaSalida} - ${horaLlegada}";
            itemBinding.ciudadYAerolinea.text = "${vuelo.origen.ciudadAbrev}, ${vuelo.aerolinea}"
            itemBinding.escala.text = escalasText
            itemBinding.tiempoVuelo.text = "${horas}h ${minutos}"

            //todo poner imagen solecito de dia, y luna de noche
            //itemBinding.imagen.setImageResource(imagenId)

            itemBinding.precio.text = "${vuelo.precio.toString()} € "


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