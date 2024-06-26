import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.ViewholderResenaHotelBinding
import com.example.travelwithmeapp.models.Hotel
import com.example.travelwithmeapp.models.Resena
import com.example.travelwithmeapp.utils.Utilities

/**
 * Adapter for reviews recyclerview on HotelFragment
 *
 * @author Matías Martínez
 * @author Javier Cuesta
 * @author Samuel Fraisoli
 */

class ResenaHotelAdapter(
    private val lista: ArrayList<Resena>,
) : RecyclerView.Adapter<ResenaHotelAdapter.ResenaHolder>() {
    private val utilities = Utilities()

    inner class ResenaHolder(private val itemBinding: ViewholderResenaHotelBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(resena: Resena) {
            itemBinding.textNombrePlan.text = resena.titulo
            itemBinding.horaFechaResena.text = utilities.formatearOffsetDateTimeDDMMYYYY(resena.fecha)
            itemBinding.textDescripcionPlan.text = resena.contenido
            itemBinding.ratingBar.rating = resena.nota

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResenaHotelAdapter.ResenaHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ViewholderResenaHotelBinding.inflate(inflater, parent, false)
        return ResenaHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ResenaHotelAdapter.ResenaHolder, position: Int) {
        val resena = lista[position]
        holder.bindItem(resena)
    }

    fun setData(nuevaLista: ArrayList<Resena>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged() // Notificar al RecyclerView de que los datos han cambiado
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}
