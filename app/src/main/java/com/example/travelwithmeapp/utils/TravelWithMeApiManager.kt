package com.example.travelwithmeapp.utils
import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.travelwithmeapp.models.Aeropuerto
import com.example.travelwithmeapp.models.TrayectoVuelo
import com.example.travelwithmeapp.models.Vuelo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TravelWithMeApiManager(var context: Context) {
    val gson = Gson()
    private lateinit var volleyQueue : RequestQueue
    private val url = "http://10.0.2.2:8080/api"
    private var vuelos = ArrayList<Vuelo>()


    //es una funcion suspendida, porque las lanzamos como corrutinas (las funciones suspendidas pueden pausar su ejecucion hasta que se ejecute la operación, sin pausar el resto de
    //la aplicación
   fun buscarVuelosConParametros(origen: String, destino: String, fecha: String) {
       getVuelosConParametros(origen, destino, fecha) {
           it ->
           vuelos = parsearJsonVuelo(it)
           for(vuelo: Vuelo in vuelos) {
               getTrayectosVueloPorIdVuelo(vuelo.id) {
                   it ->
                   var trayectos = ArrayList<TrayectoVuelo>()
                   trayectos = parsearJsonTrayectos(it)
                   vuelo.trayectos = trayectos
                   for(trayecto: TrayectoVuelo in trayectos) {
                       getAeropuertoPorId(trayecto.origen.id) {
                           it ->
                           val origen = parsearJsonAeropuerto(it)
                           trayecto.origen = origen
                       }
                       getAeropuertoPorId(trayecto.destino.id) {
                           it ->
                           val destino = parsearJsonAeropuerto(it)
                           trayecto.destino = destino
                       }
                   }
               }
           }
       }
   }

    fun getVuelosConParametros(origen: String, destino: String, fecha: String, callback: (String) -> Unit) {
        val url = "${url}/vuelos/filtrados?origen=${origen}&destino=${destino}&fecha=${fecha}"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                Log.v("", "recibido correctamente")
                Log.v("", "${response.toString()}")
                // ejecuta la función callback, que pide un String y devuelve un unit
                //se usa para procesar el resultado de la solicitud
                //todo parsear json
                callback(response.toString())
            },

            Response.ErrorListener { error ->
                Log.v("", "los datos no se han recibido")
                Log.v("", "${error}")
                //todo mostrar snackbar con error
            })
        Volley.newRequestQueue(context).add(stringRequest)
    }

    fun parsearJsonVuelo(json: String) : ArrayList<Vuelo> {
        val vueloType = object : TypeToken<ArrayList<Vuelo>>() {}.type
        val vuelos: ArrayList<Vuelo> = gson.fromJson(json, vueloType)
        return vuelos
    }

    fun getTrayectosVueloPorIdVuelo(idVuelo : Long, callback: (String) -> Unit) {
        val url = "${url}/trayectoVuelos/filtradosIdVuelo?idVuelo=${idVuelo}"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                Log.v("", "recibido correctamente")
                Log.v("", "${response.toString()}")
                // ejecuta la función callback, que pide un String y devuelve un unit
                //se usa para procesar el resultado de la solicitud
                //todo parsear json
                callback(response.toString())
            },

            Response.ErrorListener { error ->
                Log.v("", "los datos no se han recibido")
                Log.v("", "${error}")
                //todo mostrar snackbar con error
            })
        Volley.newRequestQueue(context).add(stringRequest)
    }

    fun parsearJsonTrayectos(json: String) : ArrayList<TrayectoVuelo> {
        val listType = object : TypeToken<ArrayList<TrayectoVuelo>>() {}.type
        val trayectosVuelo: ArrayList<TrayectoVuelo> = gson.fromJson(json, listType)
        return trayectosVuelo
    }

    fun getAeropuertoPorId(id : Long, callback: (String) -> Unit) {
        val url = "${url}/api/aeropuertos/${id}"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                Log.v("", "recibido correctamente")
                Log.v("", "${response.toString()}")
                // ejecuta la función callback, que pide un String y devuelve un unit
                //se usa para procesar el resultado de la solicitud
                //todo parsear json
                callback(response.toString())
            },

            Response.ErrorListener { error ->
                Log.v("", "los datos no se han recibido")
                Log.v("", "${error}")
                //todo mostrar snackbar con error
            })
        Volley.newRequestQueue(context).add(stringRequest)
    }

    fun parsearJsonAeropuerto(json: String): Aeropuerto {
        val aeropuerto: Aeropuerto = gson.fromJson(json, Aeropuerto::class.java)
        return aeropuerto
    }








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