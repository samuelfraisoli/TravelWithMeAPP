package com.example.travelwithmeapp.utils

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.example.travelwithmeapp.R
import com.example.travelwithmeapp.models.ProviderType
import com.example.travelwithmeapp.models.User
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

/**
 * Class responsible for managing Firebase Authentication related operations.
 *
 * @property context Context of the activity.
 *
 * @author Samuel Fraisoli
 */
class FirebaseAuthManager(val context: Context) {
    private val CODIGO_GOOGLE = 100
    private val utilities = Utilities()


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
     * Logea al usuario en firebase.
     * Si el usuario se logea correctamente realiza un callback al que se le pasa como parámetro el usuario. Este callback va a ser una función que se va a crear en la actividad, y va a realizar una operación con el usuario. Lo que haremos será únicamente retornar el usuario
     *
     * Hay que retornar el usuario con un callback en vez de con un return, porque las llamadas a firebase son asíncronas, entonces, si se pone un return, puede ser que se ejecute antes de que se haya recuperado el usuario, por lo que devolvería el usuario vacío
     *
     * Si hay un error, mostrará un AlertDialog con el mensaje de error
     */
    fun logearConCorreo(email: String, password: String, callback: (User) -> Unit) {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                var user = User()
                val uid = it.user?.uid ?: null
                val email = it.user?.email ?: null
                val provider = ProviderType.BASIC
                if (uid != null && email != null) {
                    user = User(uid, email, provider)
                    callback(user)
                } else {
                    utilities.mostrarAlertaDialog("No se pudo completar el login", context)
                }
            }
            .addOnFailureListener {
                utilities.mostrarAlertaDialog(it?.message ?: "Error", context)
            }
    }


    fun procesarDatosActivityLoginGoogle(data: Intent?, callback: (User) -> Unit) {
        try {
            Log.v("", "procesarDatosActivityLoginGoogle")
            //recoge la cuenta de google del usuario a partir del Intent llamado data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)

            //si la cuenta recuperada no es null. Registrará al usuario en firebase con los datos que coge de la cuenta de google.
            //Luego lo devolverá mediante un callback
            if (account != null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnSuccessListener {
                        var user = User()
                        var uid = it.user?.uid
                        var email = account.email
                        var name = account.displayName
                        var provider = ProviderType.GOOGLE
                        if (uid != null && email != null && email != null && name != null) {
                            user = User(uid, email, provider, name)
                            Log.v("", user.toString())
                            callback(user)
                        }
                    }
                    .addOnFailureListener {
                        utilities.mostrarAlertaDialog(it?.message ?: "Error", context)
                    }

            }
        }
        catch (e: ApiException) {

        }
    }


    //REGISTRO
    fun registrarConCorreo(email: String, password: String, callback: (User) -> Unit) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                var user = User()
                val uid = it.user?.uid ?: null
                val email = it.user?.email ?: null
                val provider = ProviderType.BASIC
                if (uid != null && email != null) {
                    user = User(uid, email, provider)
                    callback(user)
                } else {
                    utilities.mostrarAlertaDialog("No se pudo completar el registro", context)
                }
            }
            .addOnFailureListener {
                utilities.mostrarAlertaDialog(it?.message ?: "Error", context)
            }
    }


    //OTRAS FUNCIONALIDADES
    fun enviarMailRestablecerContraseña(email: String, view: View, callback: (Boolean) -> Unit) {
        if(email.isEmpty()) {
            utilities.mostrarAlertaDialog("Rellena el campo de tu email para que te enviemos un correo con la nueva contraseña", context)
        }
        else {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    utilities.lanzarSnackBarCorto("Correo enviado", view)
                    callback(true)

                }
                .addOnFailureListener {
                    utilities.mostrarAlertaDialog("No se ha podido realizar el proceso", context)
                }
        }
    }





}