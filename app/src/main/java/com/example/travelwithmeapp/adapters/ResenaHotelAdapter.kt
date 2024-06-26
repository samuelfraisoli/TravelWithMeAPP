import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.ViewholderResenaHotelBinding
import com.example.travelwithmeapp.models.Hotel
import com.example.travelwithmeapp.models.Resena
import com.example.travelwithmeapp.utils.Utilities

/**
 * Adapter class for displaying hotel reviews in a RecyclerView.
 *
 * @property lista The list of reviews to display.
 *
 * @author Matías Martínez
 * @author Javier Cuesta
 * @author Samuel Fraisoli
 */

class ResenaHotelAdapter(
    private val lista: ArrayList<Resena>,
) : RecyclerView.Adapter<ResenaHotelAdapter.ResenaHolder>() {
    private val utilities = Utilities()

    /**
     * ViewHolder class for holding and binding review item views.
     * @param itemBinding The View Binding object for the review item layout.
     */
    inner class ResenaHolder(private val itemBinding: ViewholderResenaHotelBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        /**
         * Binds review data to the UI elements in the ViewHolder.
         * @param resena The review object containing data to bind.
         */
        fun bindItem(resena: Resena) {
            itemBinding.textNombrePlan.text = resena.titulo
            itemBinding.horaFechaResena.text = utilities.formatearOffsetDateTimeDDMMYYYY(resena.fecha)
            itemBinding.textDescripcionPlan.text = resena.contenido
            itemBinding.ratingBar.rating = resena.nota
        }
    }

    /**
     * Updates the data set with a new list of reviews.
     * @param nuevaLista The new list of reviews to update the RecyclerView with.
     */
    fun setData(nuevaLista: ArrayList<Resena>) {
        lista.clear()
        lista.addAll(nuevaLista)
        notifyDataSetChanged()
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
