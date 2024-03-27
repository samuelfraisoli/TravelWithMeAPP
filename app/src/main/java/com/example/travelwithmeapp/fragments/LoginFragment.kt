package com.example.travelwithmeapp.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.travelwithmeapp.activities.MainActivity
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.databinding.FragmentLoginBinding
import com.example.travelwithmeapp.models.ProviderType
import com.example.travelwithmeapp.models.User
import com.example.travelwithmeapp.utils.FirebaseAuthManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


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
            binding.botonRegistrar.id -> { intentARegistroFrag() }
            binding.botonLogin.id -> { logearConCorreo() }
            binding.botonLoginGoogle.id -> { firebaseAuthManager.logearConGoogle() }
            binding.botonRestablecerContraseA.id -> { firebaseAuthManager.restablecerContraseña() }
        }
    }

    fun logearConCorreo() {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            val user = firebaseAuthManager.logearConCorreo(email.toString(), password.toString())
            if (user != null) {
                intentAMainAct(user)
            }
        }
    }

    /**
     * Lanza una activity para que el usuario introduzca unos datos, y luego devuelve los datos que procesa esa actividad.
     *  - Se usa para lanzar los distintos tipos de login que tendrá la aplicación
     */
    //todo arreglar y meter en FirebaseManager las partes de firebase
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == CODIGO_GOOGLE) {
                try {
                    //primero se logea con el usuario en la API de google, y coge sus datos
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    val account = task.getResult(ApiException::class.java)

                    //después, si esos datos no son nulos, logea al usuario con esos datos en firebase
                    if (account != null) {
                        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                        FirebaseAuth.getInstance().signInWithCredential(credential)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {

                                    val email = account.email
                                    val provider = ProviderType.BASIC
                                    if (email != null && email != null) {
                                        val user = User("", email, provider)
                                        intentAMainAct(user)
                                    }

                                } else {
                                    firebaseAuthManager.mostrarAlertaDialog(it.exception?.message ?: "Error")
                                }
                            }
                    }
                } catch (e: ApiException) {
                    firebaseAuthManager.mostrarAlertaDialog(e.message ?: "Error")
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


