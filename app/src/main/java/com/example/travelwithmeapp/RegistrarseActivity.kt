package com.example.travelwithmeapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.travelwithmeapp.databinding.ActivityRegistrarseBinding

class RegistrarseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrarseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}