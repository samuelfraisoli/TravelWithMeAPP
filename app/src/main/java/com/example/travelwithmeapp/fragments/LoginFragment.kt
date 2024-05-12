package com.example.travelwithmeapp.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.travelwithmeapp.activities.MainActivity
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.FragmentLoginBinding
import com.example.travelwithmeapp.models.User
import com.example.travelwithmeapp.utils.FirebaseAuthManager
import com.example.travelwithmeapp.utils.FirebaseFirestoreManager
import com.example.travelwithmeapp.utils.Utilities
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions



class LoginFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentLoginBinding

    private lateinit var email: Editable
    private lateinit var password: Editable

    private lateinit var firebaseAuthManager: FirebaseAuthManager
    private lateinit var utilities: Utilities
    private lateinit var firebaseFirestoreManager: FirebaseFirestoreManager
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
        firebaseFirestoreManager = FirebaseFirestoreManager(requireContext(), binding.root)
        utilities = Utilities()

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
                lanzarActivityLoginGoogle()
            }

            binding.botonRestablecerContraseA.id -> {
                restablecerContraseña()


            }
        }
    }


    /**
     * Comprueba que el correo y la contraseña no estén vacíos. Si no lo están, llama al gestor de firebase para que registre al usuario.
     */
    fun logearConCorreo() {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            val user = User()
            firebaseAuthManager.logearConCorreo(email.toString(), password.toString()) { user ->
                if (user != null) {
                    intentAMainAct(user)
                }
            }
        }
    }


    /**
     * Primero crea y configura la activity que se va a lanzar para que el usuario se registre con su cuenta de google.
     * Luego la lanza
     */
    fun lanzarActivityLoginGoogle() {
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
            utilities.mostrarAlertaDialog(e.message ?: "Error de autenticacion", requireContext())

        }
    }


    fun restablecerContraseña() {
        firebaseAuthManager.enviarMailRestablecerContraseña(email.toString(), binding.root) {}
    }

    /**
     * Se llama a esta función cuando la actividad que se ha lanzado con el login de google finaliza. Esta función se encarga de procesar los datos que ha recibido.
     * Los datos los ha recogido a través de un Intent, al que llama data.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.v("", "onActivityResult")
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODIGO_GOOGLE) {
            firebaseAuthManager.procesarDatosActivityLoginGoogle(data) { user ->
                if (user != null) {
                    firebaseFirestoreManager.crearDocumentoUsuario(user) {response ->
                        if(response) {
                            firebaseFirestoreManager.crearUsuario(user) {response ->
                                if(response) {
                                    intentAMainAct(user)
                                }
                            }
                        }
                    }
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


