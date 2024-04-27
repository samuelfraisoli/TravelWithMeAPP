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
import com.example.travelwithmeapp.models.Equipaje
import com.example.travelwithmeapp.models.Hotel
import com.example.travelwithmeapp.models.TrayectoVuelo
import com.example.travelwithmeapp.models.Vuelo
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class TravelWithMeApiManager(var context: Context) {
    @RequiresApi(Build.VERSION_CODES.O)
    val utilities = Utilities()
    private lateinit var volleyQueue : RequestQueue
    private val url = "http://10.0.2.2:8080/api"

    // =======================================================================================================
    //  VUELOS
    // =======================================================================================================

    // CORRUTINAS
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun buscarVuelosConParametrosParent(origen: String, destino: String, fecha: String) : ArrayList<Vuelo> {
        val vuelos: ArrayList<Vuelo>
        val jsonVuelos = getVuelosConParametrosCorrutina(origen, destino, fecha)
        vuelos = parsearJsonVuelos(jsonVuelos)

        for(vuelo: Vuelo in vuelos) {
            val jsonTrayectosVuelo = getTrayectosVueloPorIdVueloCorrutina(vuelo.id)
            val trayectos = parsearJsonTrayectos(jsonTrayectosVuelo)

            val jsonEquipaje = getEquipajePorIdCorrutina(vuelo.equipaje.id)
            val equipaje = parsearJsonEquipaje(jsonEquipaje)
            vuelo.equipaje = equipaje

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

    suspend fun getEquipajePorIdCorrutina(id: Long): String {
        return suspendCancellableCoroutine { continuation ->
            val url = "${url}/equipajes/${id}"
            Log.v("getEquipajePorIdCorrutina", url)
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    Log.v("getEquipajePorIdCorrutina", "recibido correctamente")
                    Log.v("getEquipajePorIdCorrutina", "${response.toString()}")
                    // Parsear el JSON y luego reanudar la corrutina con el resultado
                    continuation.resume(response.toString())
                },
                { error ->
                    Log.v("getEquipajePorIdCorrutina", "los datos no se han recibido")
                    Log.v("getEquipajePorIdCorrutina", "${error}")
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

    fun parsearJsonEquipaje(json: String): Equipaje {
        var equipaje = Equipaje()
        val jsonObject = JSONObject(json)

        equipaje.id = jsonObject.getInt("id").toLong()
        equipaje.peso = jsonObject.getString("peso")
        equipaje.alto = jsonObject.getString("alto")
        equipaje.ancho = jsonObject.getString("ancho")
        equipaje.precio = jsonObject.getString("precio")

        return equipaje
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


    // =======================================================================================================
    //  VUELOS
    // =======================================================================================================

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun buscarHotelesConParametrosParent(nombre: String, fecha_entrada: String, fecha_salida: String) : ArrayList<Hotel> {
        val hoteles: ArrayList<Hotel>
        val jsonHoteles = getHotelesConParametrosCorrutina(nombre, fecha_entrada, fecha_salida)
        Log.v("jsonHoteles", "${jsonHoteles}")

        return ArrayList<Hotel>()
    }

    suspend fun getHotelesConParametrosCorrutina(nombre: String, fecha_entrada: String, fecha_salida: String): String {
        return suspendCancellableCoroutine { continuation ->
            Log.v("getVuelosConParametros","${nombre}, ${fecha_entrada}, ${fecha_salida}")
            Log.v("getVuelosConParametros", "${url}/hoteles/filtrados?nombre=${nombre}&fecha_entrada=${fecha_entrada}&fecha_salida=${fecha_salida}")
            val url = "${url}/hoteles/filtrados?nombre=${nombre}&fecha_entrada=${fecha_entrada}&fecha_salida=${fecha_salida}"
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    Log.v("getHotelesConParametros", "recibido correctamente")
                    Log.v("getHotelesConParametros", "${response.toString()}")
                    // Parsear el JSON y luego reanudar la corrutina con el resultado
                    continuation.resume(response.toString())
                },
                { error ->
                    Log.v("getHotelesConParametros", "los datos no se han recibido")
                    Log.v("getHotelesConParametros", "${error}")
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
}





