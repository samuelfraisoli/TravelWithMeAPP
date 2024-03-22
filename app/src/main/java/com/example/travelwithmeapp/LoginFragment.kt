package com.example.travelwithmeapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.travelwithmeapp.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class LoginFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentLoginBinding

    private lateinit var email: Editable
    private lateinit var password: Editable
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
            binding.botonLoginGoogle.id -> { logearConGoogle() }
            binding.botonRestablecerContraseA.id -> { restablecerContraseña() }
        }
    }

    fun logearConCorreo() {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email.toString(), password.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        intentAMainAct(it.result?.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        mostrarAlertaDialog(it.exception?.message ?: "Error")
                    }
                }
        }
    }

    fun logearConGoogle() {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(com.firebase.ui.auth.R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleClient = GoogleSignIn.getClient(requireContext(), googleConf)
        //para que cuando el usuario vuelva a dar al botón de google se haga logout del usuario anterior que estaba registrado con google
        googleClient.signOut()
        startActivityForResult(googleClient.signInIntent, CODIGO_GOOGLE)

    }

    //todo completar
    fun restablecerContraseña() {

    }

    //lanza una activity para que el usuario introduzca unos datos, y luego devuelve los datos que procesa esa actividad.
    // - Se usa para lanzar los distintos tipos de login que tendrá la aplicación
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
                                intentAMainAct(account.email ?: "", ProviderType.GOOGLE)
                            } else {
                                mostrarAlertaDialog(it.exception?.message ?: "Error")
                            }
                        }
                }
            } catch (e: ApiException) {
                mostrarAlertaDialog(e.message ?: "Error")
            }
        }
    }

    fun mostrarAlertaDialog(datos: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error de autenticacion")
            .setMessage(datos)
            .setPositiveButton("Aceptar", null)
            .create()
            .show()
    }

    fun intentAMainAct(email: String, provider: ProviderType) {
        var intent = Intent(requireContext(), MainActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(intent)
    }

    fun intentARegistroFrag() {
        findNavController()?.navigate(R.id.action_loginFragment_to_registroFragment)

    }


}


