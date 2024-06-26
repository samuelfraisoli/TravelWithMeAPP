package com.example.travelwithmeapp.adapters
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.ViewholderTrayectoBinding
import com.example.travelwithmeapp.models.TrayectoVuelo
import com.example.travelwithmeapp.utils.Utilities
import java.util.Locale

/**
 * Adapter for recyclerview of flight routes in a flight, of VueloFragment
 *
 * @author Samuel Fraisoli
 */

class TrayectosAdapter(
    val lista: List<TrayectoVuelo>
    )
    : RecyclerView.Adapter<TrayectosAdapter.TrayectoHolder>() {



    inner class TrayectoHolder(val itemBinding: ViewholderTrayectoBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        var utilities = Utilities()
        @RequiresApi(Build.VERSION_CODES.O)
        fun bindItem(trayectoVuelo: TrayectoVuelo) {
            itemBinding.letrasTrayectoOrigen.text = trayectoVuelo.origen.ciudadAbrev
            itemBinding.fechaSalida.text = utilities.formatearOffsetDateTimeDDMMMM(trayectoVuelo.fechaSalida)
            itemBinding.horaSalida.text = utilities.formatoOffsetDateTimeHHMM(trayectoVuelo.fechaSalida)
            itemBinding.aeropuertoOrigen.text = trayectoVuelo.origen.nombre
            itemBinding.terminalOrigen.text = trayectoVuelo.terminalSalida
            itemBinding.duracion.text = trayectoVuelo.getDuracionFormatoHHhMMm()

            itemBinding.iconoAerolinea.setImageResource(R.drawable.plane2_icon)
            if (trayectoVuelo.aerolinea.isNotEmpty()) {
                itemBinding.iconoAerolinea.load(trayectoVuelo.aerolinea) {
                    placeholder(R.drawable.plane2_icon)
                    error(R.drawable.plane2_icon)
                    ImageViewCompat.setImageTintList(itemBinding.iconoAerolinea, ColorStateList.valueOf(
                        Color.parseColor("#FFA3A3A3")));
                }



                itemBinding.letrasTrayectoDestino.text = trayectoVuelo.destino.ciudadAbrev
                itemBinding.fechaLlegada.text =
                    utilities.formatearOffsetDateTimeDDMMMM(trayectoVuelo.fechaLlegada)
                itemBinding.horaLlegada.text = utilities.formatoOffsetDateTimeHHMM(trayectoVuelo.fechaLlegada)
                itemBinding.aeropuertoDestino.text = trayectoVuelo.destino.nombre
                itemBinding.terminalDestino.text = trayectoVuelo.terminalLlegada

                if (trayectoVuelo.escala) {
                    itemBinding.tiempoEscala.text =
                        "ESCALA: ${trayectoVuelo.getDuracionEscalaHHhMMm()}"
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrayectoHolder {
        return TrayectoHolder(
            ViewholderTrayectoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TrayectoHolder, position: Int) {
            val escala = lista.get(position)
            holder.bindItem(escala)
        }
    }
