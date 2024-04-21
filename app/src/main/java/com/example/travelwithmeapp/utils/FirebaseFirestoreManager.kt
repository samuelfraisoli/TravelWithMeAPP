package com.example.travelwithmeapp.utils

import android.content.Context
import android.view.View
import com.example.travelwithmeapp.models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue

class FirebaseFirestoreManager(var context: Context, var view: View) {
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val utilities = Utilities()


    fun guardarDatosUsuario(user: User, callback: (Boolean) -> Unit) {
        firebaseFirestore.collection("users").document(user.uid).set(
            hashMapOf(
                "email" to user.email,
                "provider" to user.provider,
                "name" to user.name,
                "surname" to user.surname,
                "birthdate" to user.birthdate,
                "telephone" to user.telephone
            )
        )
            .addOnSuccessListener {
                utilities.lanzarSnackBarCorto(
                    "Datos guardados correctamente en la BD",
                    view
                )
                callback(true)
            }
            .addOnFailureListener { e ->
                if (e.message != null) {
                    utilities.lanzarSnackBarCorto(e.message!!, view)
                    callback(false)
                }
            }
    }

    fun recogerDatosUsuario(uid: String, callback: (User?) -> Unit) {
        firebaseFirestore.collection("users").document(uid).get()
            .addOnSuccessListener {
                val user = User()
                user.email = it.get("email") as String
                user.name = it.get("name") as String
                user.surname = it.get("surname") as String
                user.birthdate = it.get("birthdate") as String
                user.telephone = it.get("telephone") as String
                callback(user)
            }
            .addOnFailureListener { e ->
                utilities.lanzarSnackBarCorto("Error al recuperar los datos. ${e}",view)
                callback(null)
            }
    }

    /**
     * En firebase no puedes eliminar un String de un documento con .delete()
     * Tienes que crear un hashmap, al que le metes el dato a eliminar, con FieldValue.delete()
     * Luego realizas una update pasando ese hashmap, y así eliminará el dato
     */
    fun eliminarDatoUsuario(uid: String, dato: String, callback: (Boolean) -> Unit) {
        val documentoUsuario = firebaseFirestore.collection("users").document(uid)

        val updates = hashMapOf<String, Any>(
            dato to FieldValue.delete(),
        )

        var boolean = false
        documentoUsuario.update(updates)
            .addOnSuccessListener {
                utilities.lanzarSnackBarCorto(
                    "Campo ${dato} eliminado correctamente",
                    view
                )
                callback(true)
            }
            .addOnFailureListener { e ->
                utilities.lanzarSnackBarCorto("Error al eliminar ${dato}. ${e}", view)
                callback(false)
            }

    }
}
