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
 * Adapter for recyclerview in BuscarVuelosFragment
 *
 * @author Samuel Fraisoli
 */

class BuscarVuelosAdapter(
    val lista: ArrayList<Vuelo>,
    val lambda: (Vuelo) -> Unit)
    : RecyclerView.Adapter<BuscarVuelosAdapter.BuscarVueloHolder>() {

    val utilities = Utilities()

    inner class BuscarVueloHolder(val itemBinding: ViewholderBuscarvueloBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
            val utilities = Utilities()
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

            //añado un onclicklistener a cada viewgholder
            itemBinding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    lambda(lista.get(position))
                }
            }
        }
    }

    fun elegirIcono(vuelo: Vuelo, itemBinding: ViewholderBuscarvueloBinding) {
        var imagenId = 0
        //convierte los objetos offsetDateTime a la hora en su zona horaria (si eran las 14:00 UTC+2, ahora lo pasa a ser solo las 14:00)
        val horaSalida = vuelo.getPrimerTrayecto().fechaSalida.toLocalTime()
        val horaLlegada = vuelo.getUltimoTrayecto().fechaLlegada.toLocalTime()

        //compruebo si la salida y la llegada son por la mañana o por la noche
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

        //dependiendo de si el vuelo es por el dia o por la noche, le doy un icono
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

    fun setData(nuevaLista: ArrayList<Vuelo>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged() // Notificar al RecyclerView de que los datos han cambiado
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