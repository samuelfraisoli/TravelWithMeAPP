package com.example.travelwithmeapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.travelwithmeapp.databinding.FragmentResenaBinding
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.travelwithmeapp.models.Resena
import java.time.OffsetDateTime

class ResenaFragment : Fragment() {
    private lateinit var binding: FragmentResenaBinding
    private var rating: Double = 0.0 // Variable donde almacenamos la nota
    private lateinit var resena: Resena // Variable para almacenar el objeto Resena

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

        binding.buttonResena.setOnClickListener {
            almacenarResena()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun almacenarResena() {
        // Recoger la calificación de la RatingBar
        val rating = binding.ratingBarResenas.rating.toDouble()

        // Recoger el texto de la reseña del EditText
        val textoResena = binding.editTextTextoResena.text.toString()

        // Crear un nuevo objeto Resena con la nota y el texto de la reseña
        resena = Resena(
            idUsuario = 1, // Aquí puedes poner el id del usuario que crea la reseña
            fecha = OffsetDateTime.now(),
            titulo = "Título de la reseña", // Aquí puedes poner el título de la reseña
            contenido = textoResena,
            nota = rating.toInt()
        )

        // Guardar la reseña en la base de datos
    }
}