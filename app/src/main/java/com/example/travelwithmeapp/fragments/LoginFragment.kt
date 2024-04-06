package com.example.travelwithmeapp.fragments


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.example.travelwithmeapp.activities.MainActivity
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.FragmentLoginBinding
import com.example.travelwithmeapp.models.User
import com.example.travelwithmeapp.utils.FirebaseAuthManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


class LoginFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var email: Editable
    private lateinit var password: Editable

    private lateinit var firebaseAuthManager: FirebaseAuthManager
    private val CODIGO_GOOGLE = 100


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        inicializar()

        return binding.root
    }


    fun inicializar() {
        firebaseAuthManager = FirebaseAuthManager(requireContext())

        email = binding.edittextCorreo.text
        password = binding.edittextPassword.text
        binding.botonRegistrar.setOnClickListener(this)
        binding.botonLogin.setOnClickListener(this)
        binding.botonLoginGoogle.setOnClickListener(this)
        binding.botonRestablecerContraseA.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.botonRegistrar.id -> {
                intentARegistroFrag()
            }

            binding.botonLogin.id -> {
                logearConCorreo()
            }

            binding.botonLoginGoogle.id -> {
                configurarActivityLoginGoogle()

            }

            binding.botonRestablecerContraseA.id -> {
                firebaseAuthManager.restablecerContraseña()
            }
        }
    }

    fun configurarActivityLoginGoogle() {
        try {
            //se configura el cliente de login de google
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(requireContext().getString(com.firebase.ui.auth.R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(requireActivity(), googleConf)

            //se llama a este método para asegurarse de que no hay ningún usuario logeado antes de iniciar sesión
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent, CODIGO_GOOGLE)

        } catch (e: Exception) {
            firebaseAuthManager.mostrarAlertaDialog(e.message ?: "Error de autenticacion")




        }
    }

    fun logearConCorreo() {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            val user =
                firebaseAuthManager.logearConCorreo(email.toString(), password.toString()) { user ->
                    if (user != null) {
                        intentAMainAct(user)
                    }
                }
        }
    }


    /**
     * Se llama a esta función cuando la actividad que se ha lanzado con el login de google finaliza. Esta función se encarga de procesar los datos que ha recibido.
     * Los datos los ha recogido a través de un Intent, al que llama data.
     *
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.v("", "onActivityResult")
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODIGO_GOOGLE) {
            firebaseAuthManager.procesarDatosActivityLoginGoogle(data) { user ->
                if (user != null) {
                    intentAMainAct(user)
                }
            }
        }
    }


    fun intentAMainAct(user: User) {
        var intent = Intent(requireContext(), MainActivity::class.java).apply {
            putExtra("user", user)
        }
        startActivity(intent)
    }

    fun intentARegistroFrag() {
        findNavController()?.navigate(R.id.action_loginFragment_to_registroFragment)

    }


}


