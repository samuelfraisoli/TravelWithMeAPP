
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.travelwithmeapp.databinding.ViewholderCarouselBinding


class CarouselAdapter(

    private var images: List<String>? = null
) : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {


    private var imageList: List<String>

    init {
        // Si solo se proporciona la lista de imágenes, inicializa la lista principal 'imageList'
        if (images != null) {
            imageList = images!!
        } else {
            imageList = getImages(listOf(
                "C:/Users/matia/OneDrive/Escritorio/2oDAM/PROYECTO_TFG/TFG_2/app/src/main/res/drawable/imagen1_explorar_fragment.jpg",
                "C:/Users/matia/OneDrive/Escritorio/2oDAM/PROYECTO_TFG/TFG_2/app/src/main/res/drawable/imagen1_explorar_fragment.jpg"))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        return CarouselViewHolder(
            ViewholderCarouselBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val imageUrl = imageList[position]
        holder.bindItem(imageUrl)
    }

    inner class CarouselViewHolder(val itemBinding: ViewholderCarouselBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(imageUrl: String) {
            itemBinding.carouselImageView.load(imageUrl) {
                transformations(RoundedCornersTransformation(8f))
            }
        }
    }

    private fun getImages(drawableIds: List<String>): List<String> {
        return drawableIds
    }
}
