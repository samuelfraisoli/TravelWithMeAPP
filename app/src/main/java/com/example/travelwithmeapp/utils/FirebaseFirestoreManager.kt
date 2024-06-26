package com.example.travelwithmeapp.utils

import android.content.Context
import android.view.View
import com.example.travelwithmeapp.models.User
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue

/**
 * Class responsible for managing Firestore database operations related to user data.
 *
 * @property context Context of the activity.
 * @property view View to display UI components like Snackbars.
 *
 * @author Samuel Fraisoli
 */

class FirebaseFirestoreManager(var context: Context, var view: View) {
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val utilities = Utilities()

    /**
     * Crea el documento en la base de datos, donde se almacenan los otros documentos que almacenan los datos del usuario.
     * Solo crea el documento si no existe, si no, lo deja igual
     */
    fun crearDocumentoUsuario(user: User, callback: (Boolean) -> Unit) {
        val userDocRef = firebaseFirestore.collection("users").document(user.uid)

        userDocRef.get().addOnSuccessListener { userDoc ->
            if (!userDoc.exists()) {
                // El documento del usuario no existe, lo creamos
                userDocRef.set(hashMapOf("created_at" to FieldValue.serverTimestamp()))
                    .addOnCompleteListener { userCreationTask ->
                        if (userCreationTask.isSuccessful) {
                            callback(true)
                        } else {
                            callback(false)
                        }
                    }
            } else {
                // El documento del usuario ya existe
                callback(true)
            }
        }.addOnFailureListener {
            callback(false)
        }
    }

    /**
     * Función que guarda los datos del usuario por primera vez en el documento info.
     */

    fun crearUsuario(user: User, callback: (Boolean) -> Unit) {
        val userData = hashMapOf<String, Any>(
            "email" to user.email,
            "provider" to user.provider,
            "name" to user.name,
            "surname" to user.surname,
            "birthdate" to user.birthdate,
            "telephone" to user.telephone
        )

        val userDocRef = firebaseFirestore.collection("users").document(user.uid)
        val datosUsuarioDocRef = userDocRef.collection("datosUsuario").document("info")

        datosUsuarioDocRef.get().addOnSuccessListener { datosUsuarioDoc ->
            datosUsuarioDocRef.set(userData)
                .addOnSuccessListener {
                    callback(true)
                }
                .addOnFailureListener {
                    callback(false)
                }
            }
        }


    fun actualizarDatosUsuario(user: User, callback: (Boolean) -> Unit) {
        val userData = hashMapOf<String, Any>(
            "email" to user.email,
            "provider" to user.provider,
            "name" to user.name,
            "surname" to user.surname,
            "birthdate" to user.birthdate,
            "telephone" to user.telephone
        )

        val userDocRef = firebaseFirestore.collection("users").document(user.uid)
        val datosUsuarioDocRef = userDocRef.collection("datosUsuario").document("info")

        datosUsuarioDocRef.get().addOnSuccessListener { datosUsuarioDoc ->
            if (datosUsuarioDoc.exists()) {
                datosUsuarioDocRef.update(userData)
                    .addOnSuccessListener {
                        callback(true)

                    }
                    .addOnFailureListener {
                        callback(false)
                    }
            }
            else {
                callback(false)
            }
        }.addOnFailureListener {
            callback(false)
        }
    }




    fun recogerDatosUsuario(uid: String, callback: (User?) -> Unit) {
        firebaseFirestore.collection("users").document(uid).collection("datosUsuario").document("info").get()
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
                utilities.lanzarSnackBarCorto("Error al recuperar los datos. ${e}", view)
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

    /**
     * Pide la id del usuario, y la lista de ids de los hoteles favoritos.
     * REEMPLAZA TODOS LOS FAVORITOS POR ESTA LISTA
     */
    fun sobreescribirFavoritos(uid: String, favoritos: ArrayList<String>, callback: (Boolean) -> Unit) {
        val favoritosData = hashMapOf<String, Any>()
        for (i in 0 until favoritos.size) {
            favoritosData[i.toString()] = favoritos[i]

            firebaseFirestore.collection("users").document(uid).collection("favoritos")
                .document("lista")
                .set(favoritosData)
                .addOnSuccessListener {
                    utilities.lanzarSnackBarCorto(
                        "Favoritos guardados correctamente en la BD",
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
    }


    fun recogerFavoritos(uid: String, callback: (ArrayList<String>?) -> Unit) {
        firebaseFirestore.collection("users").document(uid).collection("favoritos")
            .document("lista")
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val favoritosData = document.data
                    if (favoritosData != null) {
                        val favoritos = ArrayList<String>()
                        for (key in favoritosData.keys) {
                            val favorito = favoritosData[key] as? String
                            if (favorito != null) {
                                favoritos.add(favorito)
                            }
                        }
                        callback(favoritos)
                    } else {
                        callback(null)
                    }
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener { e ->
                callback(null)
            }
    }

    fun añadirFavorito(uid: String, nuevoFavorito: String, callback: (Boolean) -> Unit) {
        val favoritosRef = firebaseFirestore.collection("users").document(uid).collection("favoritos").document("lista")

        favoritosRef.get()
            .addOnSuccessListener { documentSnapshot ->
                val favoritosData = if (documentSnapshot.exists()) {
                    documentSnapshot.data as MutableMap<String, Any>
                } else {
                    hashMapOf<String, Any>()
                }

                val newIndex = favoritosData.size.toString()
                favoritosData[newIndex] = nuevoFavorito

                favoritosRef.set(favoritosData)
                    .addOnSuccessListener {
                        callback(true)
                    }
                    .addOnFailureListener { e ->
                        if (e.message != null) {
                            utilities.lanzarSnackBarCorto(e.message!!, view)
                            callback(false)
                        }
                    }
            }
            .addOnFailureListener { e ->
                if (e.message != null) {
                    utilities.lanzarSnackBarCorto(e.message!!, view)
                    callback(false)
                }
            }
    }

    fun eliminarFavorito(uid: String, favoritoAEliminar: String, callback: (Boolean) -> Unit) {
        val favoritosRef = firebaseFirestore.collection("users").document(uid).collection("favoritos").document("lista")

        favoritosRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val favoritosData = documentSnapshot.data ?: hashMapOf<String, Any>()

                    // Eliminar el favorito de los datos
                    val favoritosModificados = favoritosData.filterValues { it != favoritoAEliminar }

                    // Guardar los favoritos modificados de nuevo en la base de datos
                    favoritosRef.set(favoritosModificados)
                        .addOnSuccessListener {
                            callback(true)
                        }
                        .addOnFailureListener { e ->
                            if (e.message != null) {
                                utilities.lanzarSnackBarCorto(e.message!!, view)
                                callback(false)
                            }
                        }
                } else {
                    utilities.lanzarSnackBarCorto("No se encontró la lista de favoritos", view)
                    callback(false)
                }
            }
            .addOnFailureListener { e ->
                if (e.message != null) {
                    utilities.lanzarSnackBarCorto(e.message!!, view)
                    callback(false)
                }
            }
    }

    fun comprobarFavorito(uid: String, idHotel: String, callback: (Boolean) -> Unit) {
        recogerFavoritos(uid) {listaHotelesFavoritos ->
            var hotelEncontrado = false
            if(listaHotelesFavoritos != null) {
                for(idHotelFavorito in listaHotelesFavoritos) {
                    if(idHotelFavorito.equals(idHotel)) {
                        hotelEncontrado = true
                        callback(true)
                        break
                    }
                }
                if(!hotelEncontrado) {
                    callback(false)
                }
            }
        }
    }

}
