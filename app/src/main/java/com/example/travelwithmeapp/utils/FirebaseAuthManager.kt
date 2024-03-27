package com.example.travelwithmeapp.utils

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.models.ProviderType
import com.example.travelwithmeapp.models.User


class FirebaseAuthManager(val context: Context) {
    private val CODIGO_GOOGLE = 100


    //SESIÓN
    /**
     * Guarda la sesión del usuario
     * Hace esto abriendo el archivo txt SharedPreferences en su teléfono
     * La ruta al archivo está guardado en la carpeta R.strings. Accedemos a ella en modo privado
     */
    fun guardarSesion(user: User) {
        val sharedPrefEditor = context.getSharedPreferences(
            context.getString(R.string.prefs_file),
            Context.MODE_PRIVATE
        ).edit()
        sharedPrefEditor.putString("uid", user.uid)
        sharedPrefEditor.putString("email", user.email)
        sharedPrefEditor.putString("provider", user.provider.toString())
        //sharedPrefEditor.putString("name", user.name)
        sharedPrefEditor.apply()
    }

    /**
     * Comprueba si ya hay iniciada una sesión
     * Para eso va al archivo SharedPreferences, donde habíamos guardado la sesión (si ya nos habíamos registrado)
     * De ahí recoge la id del usuario, su email y su tipo de proovedor (para saber cómo se ha logeado)
     * Si los encuentra, devuelve un objeto User con esos datos
     * Si no los encuentra, devuelve null
     */
    //todo hacer que coja el nombre de usuario también
    fun comprobarSesion(): User? {
        val sharedPreferences = context.getSharedPreferences(
            context.getString(R.string.prefs_file),
            Context.MODE_PRIVATE
        )
        val uid = sharedPreferences.getString("uid", null)
        val email = sharedPreferences.getString("email", null)
        val provider = sharedPreferences.getString("provider", null)

        if (uid != null && email != null && provider != null) {
            return User(uid, email, ProviderType.valueOf(provider))
        }

        return null
    }

    /**
     * Cierra la sesión
     * Abre el editor de SharedPreferences y borra los datos guardados
     * Después desloguea al usuario de Firebase
     */
    fun cerrarSesion() {
        val sharedPrefEditor = context.getSharedPreferences(
            context.getString(R.string.prefs_file),
            Context.MODE_PRIVATE
        ).edit()
        sharedPrefEditor.clear()
        sharedPrefEditor.apply()

        FirebaseAuth.getInstance().signOut()
    }


    //LOGIN
    /**
     * Registra al usuario en firebase.
     * Si el registro se realiza correctamente, devuelve la id, el email y el tipo de proveedor del usuario
     * Si no se realiza correctamente, devuelve null
     */
    fun logearConCorreo(email: String, password: String): User? {
        lateinit var user: User
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    user = User()
                    val uid = it.result.user?.uid ?: null
                    val email = it.result?.user?.email ?: null
                    val provider = ProviderType.BASIC
                    if (uid != null && email != null) {
                        user = User(uid, email, provider)
                    }
                }
                mostrarAlertaDialog(it.exception?.message ?: "Error")
            }
        return user
    }

    /**
     *
     */
    fun logearConGoogle() {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(com.firebase.ui.auth.R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleClient = GoogleSignIn.getClient(context, googleConf)
        //para que cuando el usuario vuelva a dar al botón de google se haga logout del usuario anterior que estaba registrado con google
        googleClient.signOut()
        (context as Activity).startActivityForResult(googleClient.signInIntent, CODIGO_GOOGLE)

    }


    //REGISTRO
    fun registrarConCorreo(email: String, password: String): User? {
        lateinit var user: User
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email.toString(), password.toString())
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val uid = it.result.user?.uid ?: null
                    val email = it.result?.user?.email ?: null
                    val provider = ProviderType.BASIC
                    if (uid != null && email != null) {
                        user = User(uid, email, provider)
                    }


                } else {
                    mostrarAlertaDialog(it.exception?.message ?: "Error")
                }
            }
        return user
    }


    //OTRAS FUNCIONALIDADES
    //todo crear
    fun restablecerContraseña() {

    }



    fun mostrarAlertaDialog(datos: String) {
        AlertDialog.Builder(context)
            .setTitle("Error de autenticacion")
            .setMessage(datos)
            .setPositiveButton("Aceptar", null)
            .create()
            .show()
    }
}