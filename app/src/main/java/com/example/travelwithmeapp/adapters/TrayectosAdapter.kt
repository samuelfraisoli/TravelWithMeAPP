package com.example.travelwithmeapp.adapters
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.ViewholderTrayectoBinding
import com.example.travelwithmeapp.models.TrayectoVuelo
import com.example.travelwithmeapp.utils.Utilities
import java.util.Locale


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
            itemBinding.fechaSalida.text = utilities.formatoDateDDMMMM(trayectoVuelo.fechaSalida, Locale.getDefault())
            itemBinding.horaSalida.text = utilities.formatoDateHHMM(trayectoVuelo.fechaSalida)
            itemBinding.aeropuertoOrigen.text = trayectoVuelo.origen.nombre
            itemBinding.terminalOrigen.text = trayectoVuelo.origen.terminal
            itemBinding.duracion.text = utilities.formatoDurationHHhMMm(trayectoVuelo.duracion)

            itemBinding.iconoAerolinea.setImageResource(R.drawable.plane2_icon)
            if (trayectoVuelo.aerolinea.isNotEmpty()) {
                itemBinding.iconoAerolinea.load(trayectoVuelo.aerolinea) {
                    placeholder(R.drawable.plane2_icon)
                    error(R.drawable.plane2_icon)
                }



                itemBinding.letrasTrayectoDestino.text = trayectoVuelo.destino.ciudadAbrev
                itemBinding.fechaLlegada.text = utilities.formatoDateDDMMMM(trayectoVuelo.fechaLlegada, Locale.getDefault())
                itemBinding.horaLlegada.text = utilities.formatoDateHHMM(trayectoVuelo.fechaLlegada)
                itemBinding.aeropuertoDestino.text = trayectoVuelo.destino.nombre
                itemBinding.terminalDestino.text = trayectoVuelo.destino.terminal

                if (trayectoVuelo.escala) {
                    itemBinding.tiempoEscala.text =
                        "ESCALA: ${utilities.formatoDurationHHhMMm(trayectoVuelo.tiempoEscala)}"
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

    override fun onBindViewHolder(holder: TrayectoHolder, position: Int) {
            val escala = lista.get(position)
            holder.bindItem(escala)
        }
    }
