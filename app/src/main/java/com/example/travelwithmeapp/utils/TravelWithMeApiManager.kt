package com.example.travelwithmeapp.utils
import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import android.util.Log
import com.android.volley.RequestQueue
import com.example.travelwithmeapp.models.Vuelo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import org.json.JSONObject

class TravelWithMeApiManager {
    private lateinit var volleyQueue : RequestQueue

    //es una funcion suspendida, porque las lanzamos como corrutinas (las funciones suspendidas pueden pausar su ejecucion hasta que se ejecute la operación, sin pausar el resto de
    //la aplicación
    fun crearYLanzarRequest(url: String, nombre: String, context: Context, callback: (String) -> Unit) {
        volleyQueue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                Log.v("", "recibido correctamente")
                Log.v("", "${response.toString()}")
                // ejecuta la función callback, que pide un String y devuelve un unit
                callback(response.toString())


            },
            Response.ErrorListener { error ->
                Log.v("", "los datos no se han recibido")
                Log.v("", "${error}")
                // Aquí puedes agregar alguna lógica adicional para manejar errores, si es necesario
            })

        // le da el nombre personalizado a la request (en Volley si metes dos request con el mismo nombre, se sobreescriben)
        stringRequest.tag = nombre
        // añade la request a la requestqueue
        volleyQueue.add(stringRequest)
    }





    /*suspend fun obtenerVuelos(origen: String, destino: String, fecha: String): ArrayList<Vuelo>? {

        // Cambia al contexto de entrada/salida (IO) para realizar la petición de red
        return withContext(Dispatchers.IO) {
            // Crear una instancia de OkHttpClient
            val client = OkHttpClient()

            // Construir la URL base con los parámetros de consulta
            val urlBase = "http://localhost:8080/api/vuelos"

            // Usar HttpUrl.Builder para construir la URL con los parámetros de consulta
            val url = urlBase.toHttpUrlOrNull()?.newBuilder()?.apply {
                addQueryParameter("origen", origen)
                addQueryParameter("destino", destino)
                addQueryParameter("fecha", fecha)
            }?.build()

            // Crear la solicitud GET con la URL generada
            val request = Request.Builder()
                .url("http://localhost:8080/api/vuelos")
                .get()
                .addHeader("accept", "application/json")
                .build()

            // Ejecutar la solicitud
            val response = client.newCall(request).execute()

            // Verificar si la respuesta es exitosa
            if (response.isSuccessful) {
                val responseBodyString = response.body?.string()

                // Verifica si el cuerpo de la respuesta es nulo
                if (responseBodyString == null) {
                    return@withContext null
                }

                // Parsea el JSON
                val vuelos = ArrayList<Vuelo>()
                val jsonArray = JSONObject(responseBodyString).getJSONArray("vuelos")

                // Iterar sobre los vuelos y agregarlos a la lista
                for (i in 0 until jsonArray.length()) {
                    val vuelo = Vuelo()
                    val item = jsonArray.getJSONObject(i)

                    vuelo.id = item.getLong("id")
                    vuelo.id_vuelo = item.getString("idVuelo")
                    vuelo.aerolinea = item.getString("aerolinea")
                    vuelo.precio = item.getDouble("precio")
                    vuelo.tipo = item.getString("tipo")

                    vuelos.add(vuelo)
                }
                return@withContext vuelos
            } else {
                Log.v("", "Error de conexión: ${response.code}")
                return@withContext null
            }
        }

    }*/

}