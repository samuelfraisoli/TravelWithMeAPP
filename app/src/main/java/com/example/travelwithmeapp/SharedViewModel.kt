import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelwithmeapp.models.Hotel

class SharedViewModel : ViewModel() {
    private val _listaHotelesFav = MutableLiveData<ArrayList<Hotel>>()
    val listaHotelesFav: LiveData<ArrayList<Hotel>> get() = _listaHotelesFav

    fun addHotel(hotel: Hotel) {
        val updatedList = _listaHotelesFav.value ?: ArrayList()
        updatedList.add(hotel)
        _listaHotelesFav.value = updatedList
    }

    fun removeHotel(hotel: Hotel) {
        _listaHotelesFav.value?.remove(hotel)
        _listaHotelesFav.value = _listaHotelesFav.value
    }
}