import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.ViewholderResenaHotelBinding
import com.example.travelwithmeapp.models.Resena
import com.example.travelwithmeapp.utils.Utilities

class ResenaHotelAdapter(
    private val lista: List<Resena>,
) : RecyclerView.Adapter<ResenaHotelAdapter.ResenaHolder>() {
    private val utilities = Utilities()

    inner class ResenaHolder(private val itemBinding: ViewholderResenaHotelBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(resena: Resena) {
            itemBinding.textNombrePlan.text = resena.titulo
            itemBinding.horaFechaResena.text = utilities.formatearOffsetDateTimeDDMMYYYY(resena.fecha)
            itemBinding.textDescripcionPlan.text = resena.contenido

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

    override fun getItemCount(): Int {
        return lista.size
    }
}
