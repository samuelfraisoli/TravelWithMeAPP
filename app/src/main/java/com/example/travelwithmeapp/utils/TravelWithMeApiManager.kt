package com.example.travelwithmeapp.utils
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.travelwithmeapp.models.Aeropuerto
import com.example.travelwithmeapp.models.TrayectoVuelo
import com.example.travelwithmeapp.models.Vuelo
import com.google.gson.Gson
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class TravelWithMeApiManager(var context: Context) {
    @RequiresApi(Build.VERSION_CODES.O)
    val utilities = Utilities()
    val gson = Gson()
    private lateinit var volleyQueue : RequestQueue
    private val url = "http://10.0.2.2:8080/api"
    private var vuelos = ArrayList<Vuelo>()


    // =======================================================================================================
    // CORRUTINAS

    //todo falta añadir el equipaje
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun buscarVuelosConParametrosParent(origen: String, destino: String, fecha: String) : ArrayList<Vuelo> {
        val vuelos: ArrayList<Vuelo>
        val jsonVuelos = getVuelosConParametrosCorrutina(origen, destino, fecha)
        vuelos = parsearJsonVuelos(jsonVuelos)

        for(vuelo: Vuelo in vuelos) {
            val jsonTrayectosVuelo = getTrayectosVueloPorIdVueloCorrutina(vuelo.id)
            val trayectos = parsearJsonTrayectos(jsonTrayectosVuelo)

            for(trayecto: TrayectoVuelo in trayectos) {
                val jsonOrigen = getAeropuertoPorIdCorrutina(trayecto.origen.id)
                val jsonDestino = getAeropuertoPorIdCorrutina(trayecto.destino.id)

                val origen = parsearJsonAeropuerto(jsonOrigen)
                val destino = parsearJsonAeropuerto(jsonDestino)

                trayecto.origen = origen
                trayecto.destino = destino
            }
            vuelo.trayectos = trayectos
        }

        return vuelos
    }


    suspend fun getVuelosConParametrosCorrutina(origen: String, destino: String, fecha: String): String {
        return suspendCancellableCoroutine { continuation ->
            Log.v("getVuelosConParametros","${origen}, ${destino}, ${fecha}")
            Log.v("getVuelosConParametros", "${url}/vuelos/filtrados?origen=${origen}&destino=${destino}&fecha=${fecha}")
            val url = "${url}/vuelos/filtrados?origen=${origen}&destino=${destino}&fecha=${fecha}"
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    Log.v("getVuelosConParametros", "recibido correctamente")
                    Log.v("getVuelosConParametros", "${response.toString()}")
                    // Parsear el JSON y luego reanudar la corrutina con el resultado
                    continuation.resume(response.toString())
                },
                { error ->
                    Log.v("getVuelosConParametros", "los datos no se han recibido")
                    Log.v("getVuelosConParametros", "${error}")
                    // Manejar el error y reanudar la corrutina con una excepción
                    continuation.resumeWithException(error)
                }
            )
            Volley.newRequestQueue(context).add(stringRequest)

            // Cancelar la solicitud de red si la corrutina es cancelada
            continuation.invokeOnCancellation {
                stringRequest.cancel()
            }
        }
    }

    suspend fun getTrayectosVueloPorIdVueloCorrutina(idVuelo: Long): String {
        return suspendCancellableCoroutine { continuation ->
            val url = "${url}/trayectoVuelos/filtradosIdVuelo?idVuelo=${idVuelo}"

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    Log.v("getTrayectosVueloPorIdVuelo", "recibido correctamente")
                    Log.v("getTrayectosVueloPorIdVuelo", "${response.toString()}")
                    continuation.resume(response.toString())
                },

                { error ->
                    Log.v("getTrayectosVueloPorIdVuelo", "los datos no se han recibido")
                    Log.v("getTrayectosVueloPorIdVuelo", "${error}")
                    continuation.resumeWithException(error)
                    //todo mostrar snackbar con error
                })
            Volley.newRequestQueue(context).add(stringRequest)

            // Cancelar la solicitud de red si la corrutina es cancelada
            continuation.invokeOnCancellation {
                stringRequest.cancel()
            }
        }
    }

    suspend fun getAeropuertoPorIdCorrutina(id: Long) : String {
        return suspendCancellableCoroutine { continuation ->
            val url = "${url}/aeropuertos/${id}"

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    Log.v("getAeropuertoPorId", "recibido correctamente")
                    Log.v("getAeropuertoPorId", "${response.toString()}")
                    continuation.resume(response.toString())
                },
                { error ->
                    Log.v("getAeropuertoPorId", "los datos no se han recibido")
                    Log.v("getAeropuertoPorId", "${error}")
                    continuation.resumeWithException(error)
                })
            Volley.newRequestQueue(context).add(stringRequest)
            continuation.invokeOnCancellation {
                stringRequest.cancel()
            }
        }
    }





    // =============================================================================================
    // PARSEAR JSONS CON GSON
    /*@RequiresApi(Build.VERSION_CODES.O)
    fun parsearJsonVuelo(json: String) : ArrayList<VueloDTO> {
        val vueloType = object : TypeToken<ArrayList<VueloDTO>>() {}.type
        val vuelos: ArrayList<VueloDTO> = gson.fromJson(json, vueloType)
        return vuelos
    }*/




    /*@RequiresApi(Build.VERSION_CODES.O)
    fun parsearJsonTrayectos(json: String) : ArrayList<TrayectoVueloDTO> {
        val trayectosVuelo: ArrayList<TrayectoVueloDTO> = ArrayList()
        val jsonArray = JSONArray(json)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val trayecto = gson.fromJson(jsonObject.toString(), TrayectoVueloDTO::class.java)
            trayectosVuelo.add(trayecto)
        }
        return trayectosVuelo
    }*/

    /*@RequiresApi(Build.VERSION_CODES.O)
    fun parsearJsonAeropuerto(json: String): AeropuertoDTO {
        val aeropuerto: AeropuertoDTO = gson.fromJson(json, AeropuertoDTO::class.java)
        return aeropuerto
    }*/


    // =============================================================================================
    // PARSEAR JSONS CON JSONOBJECT
    @RequiresApi(Build.VERSION_CODES.O)
    fun parsearJsonVuelos(json: String) : ArrayList<Vuelo> {
        val vuelos = ArrayList<Vuelo>()
        val jsonArray = JSONArray(json)
        for (i in 0 until jsonArray.length()) {
            var vuelo: Vuelo = Vuelo()

            val jsonObject = jsonArray.getJSONObject(i)
            vuelo.id = jsonObject.getInt("id").toLong()
            vuelo.id_vuelo = jsonObject.getString("idVuelo")
            vuelo.aerolinea = jsonObject.getString("aerolinea")
            vuelo.precio = jsonObject.getDouble("precio")
            vuelo.tipo = jsonObject.getString("tipo")
            vuelo.origen = jsonObject.getString("origen")
            vuelo.destino = jsonObject.getString("destino")
            vuelo.fecha = utilities.parseStringISOAOffsetDateTime(jsonObject.getString("fecha"))
            vuelo.equipaje.id = jsonObject.getInt("equipaje").toLong()

            vuelos.add(vuelo)

        }
        return vuelos
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun parsearJsonTrayectos(json: String) : ArrayList<TrayectoVuelo> {
        val trayectos = ArrayList<TrayectoVuelo>()
        val jsonArray = JSONArray(json)

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            var trayecto: TrayectoVuelo = TrayectoVuelo()

            trayecto.id = jsonObject.getInt("id").toLong()
            trayecto.id_trayecto = jsonObject.getString("idTrayecto")
            trayecto.aerolinea = jsonObject.getString("aerolinea")
            trayecto.tipo = jsonObject.getString("tipo")
            trayecto.fechaSalida = utilities.parseStringISOAOffsetDateTime(jsonObject.getString("fechaSalida"))
            trayecto.fechaLlegada = utilities.parseStringISOAOffsetDateTime(jsonObject.getString("fechaLlegada"))
            trayecto.escala = jsonObject.getBoolean("escala")
            trayecto.fechaInicioEscala = utilities.parseStringISOAOffsetDateTime(jsonObject.getString("fechaInicioEscala"))
            trayecto.fechaFinEscala = utilities.parseStringISOAOffsetDateTime(jsonObject.getString("fechaFinEscala"))
            trayecto.terminalSalida = jsonObject.getString("terminalSalida")
            trayecto.terminalLlegada = jsonObject.getString("terminalLlegada")
            //trayecto.vuelo.id = jsonObject.getInt("vuelo")  //no hay una propiedad vuelo dentro de trayecto, aqui es al revés
            trayecto.origen.id = jsonObject.getInt("origen").toLong()
            trayecto.destino.id = jsonObject.getInt("destino").toLong()

            trayectos.add(trayecto)
        }
        return trayectos
    }


    fun parsearJsonAeropuerto(json: String): Aeropuerto {
        val jsonObject = JSONObject(json)
        var aeropuerto = Aeropuerto()

        aeropuerto.id = jsonObject.getInt("id").toLong()
        aeropuerto.nombre = jsonObject.getString("nombre")
        aeropuerto.ciudad = jsonObject.getString("ciudad")
        aeropuerto.ciudadAbrev = jsonObject.getString("ciudadAbrev")
        aeropuerto.pais = jsonObject.getString("pais")

        return aeropuerto
    }

    // =============================================================================================
    // CALLBACKS

    //es una funcion suspendida, porque las lanzamos como corrutinas (las funciones suspendidas pueden pausar su ejecucion hasta que se ejecute la operación, sin pausar el resto de
    //la aplicación
    /*fun buscarVuelosConParametros(origen: String, destino: String, fecha: String) {

        getVuelosConParametros(origen, destino, fecha) { it ->
            vuelos = parsearJsonVuelo(it)
            for (vuelo: Vuelo in vuelos) {
                getTrayectosVueloPorIdVuelo(vuelo.id) { it ->
                    var trayectos = ArrayList<TrayectoVuelo>()
                    trayectos = parsearJsonTrayectos(it)
                    vuelo.trayectos = trayectos
                    for (trayecto: TrayectoVuelo in trayectos) {
                        getAeropuertoPorId(trayecto.origen.id) { it ->
                            val origen = parsearJsonAeropuerto(it)
                            trayecto.origen = origen
                        }
                        getAeropuertoPorId(trayecto.destino.id) { it ->
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
                Log.v("getVuelosConParametros", "recibido correctamente")
                Log.v("getVuelosConParametros", "${response.toString()}")
                // ejecuta la función callback, que pide un String y devuelve un unit
                //se usa para procesar el resultado de la solicitud
                //todo parsear json
                callback(response.toString())
            },

            Response.ErrorListener { error ->
                Log.v("getVuelosConParametros", "los datos no se han recibido")
                Log.v("getVuelosConParametros", "${error}")
                //todo mostrar snackbar con error
            })
        Volley.newRequestQueue(context).add(stringRequest)
    }


    fun getTrayectosVueloPorIdVuelo(idVuelo : Long, callback: (String) -> Unit) {
        val url = "${url}/trayectoVuelos/filtradosIdVuelo?idVuelo=${idVuelo}"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                Log.v("getTrayectosVueloPorIdVuelo", "recibido correctamente")
                Log.v("getTrayectosVueloPorIdVuelo", "${response.toString()}")
                // ejecuta la función callback, que pide un String y devuelve un unit
                //se usa para procesar el resultado de la solicitud
                //todo parsear json
                callback(response.toString())
            },

            Response.ErrorListener { error ->
                Log.v("getTrayectosVueloPorIdVuelo", "los datos no se han recibido")
                Log.v("getTrayectosVueloPorIdVuelo", "${error}")
                //todo mostrar snackbar con error
            })
        Volley.newRequestQueue(context).add(stringRequest)
    }


    fun getAeropuertoPorId(id : Long, callback: (String) -> Unit) {
        val url = "${url}/api/aeropuertos/${id}"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                Log.v("getAeropuertoPorId", "recibido correctamente")
                Log.v("getAeropuertoPorId", "${response.toString()}")
                // ejecuta la función callback, que pide un String y devuelve un unit
                //se usa para procesar el resultado de la solicitud
                //todo parsear json
                callback(response.toString())
            },

            Response.ErrorListener { error ->
                Log.v("getAeropuertoPorId", "los datos no se han recibido")
                Log.v("getAeropuertoPorId", "${error}")
                //todo mostrar snackbar con error
            })
        Volley.newRequestQueue(context).add(stringRequest)
    }


    // =============================================================================================
    // REQUEST PRUEBA

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
    }*/
}





