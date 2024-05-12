package com.example.travelwithmeapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.travelwithmeapp.databinding.FragmentResenaBinding
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.travelwithmeapp.activities.MainActivity
import com.example.travelwithmeapp.models.Hotel
import com.example.travelwithmeapp.models.Resena
import com.example.travelwithmeapp.models.User
import com.example.travelwithmeapp.utils.FirebaseFirestoreManager
import com.example.travelwithmeapp.utils.TravelWithMeApiManager
import com.example.travelwithmeapp.utils.Utilities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

class ResenaFragment : Fragment() {
    private lateinit var binding: FragmentResenaBinding
    private lateinit var travelWithMeApiManager: TravelWithMeApiManager
    private lateinit var utilities: Utilities
    private lateinit var firebaseFirestoreManager: FirebaseFirestoreManager

    private lateinit var resena: Resena
    private lateinit var hotel: Hotel
    private lateinit var uid: String
    private lateinit var user: User
    private var rating: Double = 0.0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResenaBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        inicializar()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun inicializar() {
        travelWithMeApiManager = TravelWithMeApiManager(requireContext())
        utilities = Utilities()
        firebaseFirestoreManager = FirebaseFirestoreManager(requireContext(), binding.root)

        recogerIntent()
        recogerUserActMain()
        recogerDatosUsuarioDb()

        binding.buttonResena.setOnClickListener {
            if(user != null) {
                crearResena()
                enviarResena()
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun crearResena() {
        // Recoger la calificación de la RatingBar
        val rating = binding.ratingBarResenas.rating.toInt()

        // Recoger el texto de la reseña del EditText
        val textoResena = binding.editTextTextoResena.text.toString()

        resena = Resena(
            idUsuario = user.uid,
            fecha = OffsetDateTime.now(),
            titulo = user.name,
            contenido = textoResena,
            nota = rating
        )
    }

    fun enviarResena() {
        CoroutineScope(Dispatchers.IO).launch {
                travelWithMeApiManager.postResenaCorrutina(resena, hotel.id)
        }
    }

    fun recogerUserActMain() {
        if(activity != null && activity is MainActivity) {
            uid = (activity as MainActivity).user.uid
        }
    }



    fun recogerDatosUsuarioDb() {
        if(uid == null ) {
            return
        }
        firebaseFirestoreManager.recogerDatosUsuario(uid) { userRecibido ->
            if(userRecibido != null) {
                user = User()
                user.name = userRecibido.name
            }
        }
    }


    // INTENTS
    private fun recogerIntent() {
        val bundle = arguments
        if (bundle != null) {
            hotel = bundle.getSerializable("hotel") as Hotel
        }
    }
}