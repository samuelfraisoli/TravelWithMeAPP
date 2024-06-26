package com.example.travelwithmeapp.utils

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.travelwithmeapp.models.Aeropuerto
import com.example.travelwithmeapp.models.DetallesHotel
import com.example.travelwithmeapp.models.Direccion
import com.example.travelwithmeapp.models.Equipaje
import com.example.travelwithmeapp.models.Hotel
import com.example.travelwithmeapp.models.Resena
import com.example.travelwithmeapp.models.TrayectoVuelo
import com.example.travelwithmeapp.models.Vuelo
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Class responsible for managing API interactions with TravelWithMeAPI related to travel information retrieval such as flights and hotels.
 * Uses Volley for network requests and coroutines for asynchronous operations.
 *
 * @author Samuel Fraisoli
 */

class TravelWithMeApiManager(var context: Context) {
    @RequiresApi(Build.VERSION_CODES.O)
    val utilities = Utilities()
    private lateinit var volleyQueue: RequestQueue
    private val url = "http://10.0.2.2:8080/api"

    // =======================================================================================================
    //  VUELOS
    // =======================================================================================================

    // CORRUTINAS
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun buscarVuelosConParametrosParent(
        origen: String,
        destino: String,
        fecha: String
    ): ArrayList<Vuelo> {
        val vuelos: ArrayList<Vuelo>
        val jsonVuelos = getVuelosConParametrosCorrutina(origen, destino, fecha)
        vuelos = parsearJsonVuelos(jsonVuelos)

        for (vuelo: Vuelo in vuelos) {
            val jsonTrayectosVuelo = getTrayectosVueloPorIdVueloCorrutina(vuelo.id)
            val trayectos = parsearJsonTrayectos(jsonTrayectosVuelo)

            val jsonEquipaje = getEquipajePorIdCorrutina(vuelo.equipaje.id)
            val equipaje = parsearJsonEquipaje(jsonEquipaje)
            vuelo.equipaje = equipaje

            for (trayecto: TrayectoVuelo in trayectos) {
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

    suspend fun getVuelosConParametrosCorrutina(
        origen: String,
        destino: String,
        fecha: String
    ): String {
        return suspendCancellableCoroutine { continuation ->
            Log.v("getVuelosConParametros", "${origen}, ${destino}, ${fecha}")
            Log.v(
                "getVuelosConParametros",
                "${url}/vuelos/filtrados?origen=${origen}&destino=${destino}&fecha=${fecha}"
            )
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

    suspend fun getAeropuertoPorIdCorrutina(id: Long): String {
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
    // PARSEAR JSONS
    @RequiresApi(Build.VERSION_CODES.O)
    fun parsearJsonVuelos(json: String): ArrayList<Vuelo> {
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
    fun parsearJsonTrayectos(json: String): ArrayList<TrayectoVuelo> {
        val trayectos = ArrayList<TrayectoVuelo>()
        val jsonArray = JSONArray(json)

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            var trayecto: TrayectoVuelo = TrayectoVuelo()

            trayecto.id = jsonObject.getInt("id").toLong()
            trayecto.id_trayecto = jsonObject.getString("idTrayecto")
            trayecto.aerolinea = jsonObject.getString("aerolinea")
            trayecto.tipo = jsonObject.getString("tipo")
            trayecto.fechaSalida =
                utilities.parseStringISOAOffsetDateTime(jsonObject.getString("fechaSalida"))
            trayecto.fechaLlegada =
                utilities.parseStringISOAOffsetDateTime(jsonObject.getString("fechaLlegada"))
            trayecto.escala = jsonObject.getBoolean("escala")
            if(trayecto.escala == true) {
                trayecto.fechaInicioEscala =
                    utilities.parseStringISOAOffsetDateTime(jsonObject.getString("fechaInicioEscala"))
                trayecto.fechaFinEscala =
                    utilities.parseStringISOAOffsetDateTime(jsonObject.getString("fechaFinEscala"))
                trayecto.terminalSalida = jsonObject.getString("terminalSalida")
            }
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
    //  HOTELES
    // =======================================================================================================

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun buscarHotelesConParametrosParent(nombre: String, fecha_entrada: String, fecha_salida: String): ArrayList<Hotel> {
        var hoteles = ArrayList<Hotel>()
        val jsonHoteles = getHotelesConParametrosCorrutina(nombre, fecha_entrada, fecha_salida)
        Log.v("jsonHoteles", "${jsonHoteles}")
        hoteles = parsearJsonHoteles(jsonHoteles)

        for (hotel: Hotel in hoteles) {
            var jsonDireccion = getDireccionPorIdCorrutina(hotel.direccion.id)
            val direccion = parsearJsonDireccion(jsonDireccion)
            hotel.direccion = direccion

            var jsonDetalles = getDetallesHotelPorIDCorrutina(hotel.detalles.id)
            val detallesHotel = parsearJsonDetalles(jsonDetalles)
            hotel.detalles = detallesHotel

            var jsonResenas = getResenasPorIdHotelCorrutina(hotel.id)
            val resenas = parsearJsonResenas(jsonResenas)
            hotel.resenas =  resenas
        }

        return hoteles
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun buscarHotelPorIdParent(id: Long): Hotel {

        val jsonHotel = getHotelesPorIdCorrutina(id)
        Log.v("jsonHotel", "${jsonHotel}")
        var hotel = parsearJsonHotel(jsonHotel)

        var jsonDireccion = getDireccionPorIdCorrutina(hotel.direccion.id)
        val direccion = parsearJsonDireccion(jsonDireccion)
        hotel.direccion = direccion

        var jsonDetalles = getDetallesHotelPorIDCorrutina(hotel.detalles.id)
        val detallesHotel = parsearJsonDetalles(jsonDetalles)
        hotel.detalles = detallesHotel

        var jsonResenas = getResenasPorIdHotelCorrutina(hotel.id)
        val resenas = parsearJsonResenas(jsonResenas)
        hotel.resenas =  resenas

        return hotel
    }

    suspend fun buscarDiezHotelesRandom(): ArrayList<Hotel>{
        var hoteles = ArrayList<Hotel>()
        val jsonHoteles = getAllHotelesCorrutina()
        hoteles = parsearJsonHoteles(jsonHoteles)
        Log.v("", "${hoteles.get(0).fotos.get(0)}")

        //Genera 10 números random para coger 10 hoteles random
        // Crea una lista para almacenar los índices únicos
        val indicesUnicos = mutableListOf<Int>()

        // Crea un conjunto para rastrear los índices generados
        val indicesGenerados = mutableSetOf<Int>()

        // Genera números aleatorios únicos hasta que tengamos la cantidad deseada
        while (indicesUnicos.size < 10) {
            val indice = (0 until hoteles.size).random()
            if (indice !in indicesGenerados) {
                indicesUnicos.add(indice)
                indicesGenerados.add(indice)
            }
        }

        var listaHotelesRandom = ArrayList<Hotel>()

        indicesUnicos.forEach { indice ->
            listaHotelesRandom.add(hoteles[indice])
        }

        for (hotel: Hotel in listaHotelesRandom) {
            var jsonDireccion = getDireccionPorIdCorrutina(hotel.direccion.id)
            val direccion = parsearJsonDireccion(jsonDireccion)
            hotel.direccion = direccion

            var jsonDetalles = getDetallesHotelPorIDCorrutina(hotel.detalles.id)
            val detallesHotel = parsearJsonDetalles(jsonDetalles)
            hotel.detalles = detallesHotel

            var jsonResenas = getResenasPorIdHotelCorrutina(hotel.id)
            val resenas = parsearJsonResenas(jsonResenas)
            hotel.resenas =  resenas
        }

        return listaHotelesRandom
    }

    suspend fun getAllHotelesCorrutina(): String {
        return suspendCancellableCoroutine { continuation ->

            val url = "${url}/hotels"
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    Log.v("getAllHoteles", "recibido correctamente")
                    Log.v("getAllHoteles", "${response.toString()}")
                    // Parsear el JSON y luego reanudar la corrutina con el resultado
                    continuation.resume(response.toString())
                },
                { error ->
                    Log.v("getAllHoteles", "los datos no se han recibido")
                    Log.v("getAllHoteles", "${error}")
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

    suspend fun getHotelesPorIdCorrutina(id: Long): String {
        return suspendCancellableCoroutine { continuation ->
            Log.v("getHotelesPorId", "${id}")

            val url = "${url}/hotels/$id"
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    Log.v("getHotelesPorId", "recibido correctamente")
                    Log.v("getHotelesPorId", "${response.toString()}")
                    // Parsear el JSON y luego reanudar la corrutina con el resultado
                    continuation.resume(response.toString())
                },
                { error ->
                    Log.v("getHotelesPorId", "los datos no se han recibido")
                    Log.v("getHotelesPorId", "${error}")
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

    suspend fun getHotelesConParametrosCorrutina(
        nombre: String,
        fecha_entrada: String,
        fecha_salida: String
    ): String {
        return suspendCancellableCoroutine { continuation ->
            Log.v("getHotelesConParametros", "${nombre}, ${fecha_entrada}, ${fecha_salida}")
            Log.v(
                "getHotelesConParametros",
                "${url}/hotels/filtrados?nombre=${nombre}&fecha_entrada=${fecha_entrada}&fecha_salida=${fecha_salida}"
            )
            val url =
                "${url}/hotels/filtrados?nombre=${nombre}&fecha_entrada=${fecha_entrada}&fecha_salida=${fecha_salida}"
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

    suspend fun getDireccionPorIdCorrutina(id: Long): String {
        return suspendCancellableCoroutine { continuation ->
            Log.v("getDireccionPorId", "${url}/direccions/$id")
            val url = "${url}/direccions/$id"
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    Log.v("getDireccionPorId", "recibido correctamente")
                    Log.v("getDireccionPorId", "${response.toString()}")
                    // Parsear el JSON y luego reanudar la corrutina con el resultado
                    continuation.resume(response.toString())
                },
                { error ->
                    Log.v("getDireccionPorId", "los datos no se han recibido")
                    Log.v("getDireccionPorId", "${error}")
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

    suspend fun getDetallesHotelPorIDCorrutina(id: Long): String {
        return suspendCancellableCoroutine { continuation ->
            Log.v("getHotelDetailsPorID", "${url}/detallesHotels/$id")
            val url = "${url}/detallesHotels/$id"
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    Log.v("getHotelDetailsPorID", "recibido correctamente")
                    Log.v("getHotelDetailsPorID", "${response.toString()}")
                    // Parsear el JSON y luego reanudar la corrutina con el resultado
                    continuation.resume(response.toString())
                },
                { error ->
                    Log.v("getHotelDetailsPorID", "los datos no se han recibido")
                    Log.v("getHotelDetailsPorID", "${error}")
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

    suspend fun getResenasPorIdHotelCorrutina(id: Long): String {
        return suspendCancellableCoroutine { continuation ->
            Log.v("getResenasPorIdHotel", "${url}/resenas/filtradasIdHotel?idHotel=$id")
            val url = "${url}/resenas/filtradasIdHotel?idHotel=$id"
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    Log.v("getResenasPorIdHotel", "recibido correctamente")
                    Log.v("getResenasPorIdHotel", "${response.toString()}")
                    // Parsear el JSON y luego reanudar la corrutina con el resultado
                    continuation.resume(response.toString())
                },
                { error ->
                    Log.v("getResenasPorIdHotel", "los datos no se han recibido")
                    Log.v("getResenasPorIdHotel", "${error}")
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

    suspend fun postResenaCorrutina(resena: Resena, idHotel: Long): Long {
        Log.v("postResena", "${url}/resenas")
        return suspendCancellableCoroutine { continuation ->
            val jsonBody = JSONObject().apply {
                put("idUsuario", resena.idUsuario)
                put("fecha", resena.fecha)
                put("titulo", resena.titulo)
                put("contenido", resena.contenido)
                put("nota", resena.nota.toString())
                put("hotel", idHotel.toString())
            }

            val url = "${url}/resenas"

            val request = object : StringRequest(
                Method.POST, url,
                { response ->
                    Log.v("postResena", "enviado correctamente")
                    Log.v("postResena", response)

                    val idResena = response.toLongOrNull()
                    if (idResena != null) {
                        continuation.resume(idResena)
                    } else {
                        continuation.resumeWithException(Exception("No se pudo obtener el ID de la reseña"))
                    }
                },
                { error ->
                    Log.v("postResena", "los datos no se han enviado")
                    Log.v("postResena", "$error")
                    continuation.resumeWithException(error)
                }
            ) {
                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }

                override fun getBody(): ByteArray {
                    return jsonBody.toString().toByteArray()
                }
            }

            Volley.newRequestQueue(context).add(request)

            continuation.invokeOnCancellation {
                request.cancel()
            }
        }
    }


    // =============================================================================================
    // PARSEAR JSONS

    @RequiresApi(Build.VERSION_CODES.O)
    fun parsearJsonHoteles(json: String): ArrayList<Hotel> {
        var hoteles: ArrayList<Hotel> = ArrayList()
        val jsonArray = JSONArray(json)


        for (i in 0 until jsonArray.length()) {
            var hotel: Hotel = Hotel()
            val jsonObject = jsonArray.getJSONObject(i)

            hotel.id = jsonObject.getLong("id")
            hotel.nombre = jsonObject.getString("nombre")
            var fotos = jsonObject.getJSONArray("fotos")
            if (fotos.length() > 0) {
                for (j in 0 until fotos.length()) {
                    hotel.fotos.add(fotos.getString(j))
                }
            }

            var fechasLibres = jsonObject.getJSONArray("fechasLibres")
            for (j in 0 until fechasLibres.length()) {
                val fechaString = fechasLibres.getString(j)
                val fechaLocalDate = utilities.parseStringISOTaLocalDate(fechaString)
                hotel.fechasLibres.add(fechaLocalDate)
            }

            hotel.direccion.id = jsonObject.getLong("direccion")
            hotel.detalles.id = jsonObject.getLong("detallesHotel")

            hoteles.add(hotel)

        }

        return hoteles
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun parsearJsonHotel(json: String): Hotel {

        var hotel: Hotel = Hotel()
        val jsonObject = JSONObject(json)

        hotel.id = jsonObject.getLong("id")
        hotel.nombre = jsonObject.getString("nombre")
        var fotos = jsonObject.getJSONArray("fotos")
        if (fotos.length() > 0) {
            for (j in 0 until fotos.length()) {
                hotel.fotos.add(fotos.getString(j))
            }
        }

        var fechasLibres = jsonObject.getJSONArray("fechasLibres")
        for (j in 0 until fechasLibres.length()) {
            val fechaString = fechasLibres.getString(j)
            val fechaLocalDate = utilities.parseStringISOTaLocalDate(fechaString)
            hotel.fechasLibres.add(fechaLocalDate)
        }

        hotel.direccion.id = jsonObject.getLong("direccion")
        hotel.detalles.id = jsonObject.getLong("detallesHotel")

        return hotel
    }

    fun parsearJsonDireccion(json: String): Direccion {
        var direccion: Direccion = Direccion()

        val jsonObject = JSONObject(json)
        direccion.id = jsonObject.getLong("id")
        direccion.direccionString = jsonObject.getString("direccionString")
        direccion.direccion1 = jsonObject.getString("direccion1")
        direccion.direccion2 = jsonObject.getString("direccion2")
        direccion.ciudad = jsonObject.getString("ciudad")
        direccion.pais = jsonObject.getString("pais")
        direccion.codPostal = jsonObject.getString("codPostal")
        direccion.latitud = jsonObject.getDouble("latitud")
        direccion.longitud = jsonObject.getDouble("longitud")

        return direccion
    }

    fun parsearJsonDetalles(json: String): DetallesHotel {
        var detallesHotel = DetallesHotel()
        val jsonObject = JSONObject(json)

        detallesHotel.id = jsonObject.getLong("id")
        detallesHotel.descripcion = jsonObject.getString("descripcion")
        detallesHotel.web = jsonObject.getString("web")
        detallesHotel.telefono = jsonObject.getString("telefono")
        var comodidadesArray = jsonObject.getJSONArray("comodidades")

        if (comodidadesArray.length() > 0) {
            for (i in 0 until comodidadesArray.length()) {
                detallesHotel.comodidades.add(comodidadesArray.getString(i))
            }
        }
        detallesHotel.precio = jsonObject.getDouble("precio")

        return detallesHotel
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun parsearJsonResenas(json: String): ArrayList<Resena> {
        // Parsear el string JSON a un JSONArray
        var resenas = ArrayList<Resena>()
        val jsonArray = JSONArray(json)

        // Iterar sobre cada objeto en el JSONArray
        for (i in 0 until jsonArray.length()) {
            var resena = Resena()
            val jsonObject = jsonArray.getJSONObject(i)

            // Obtener los valores de cada propiedad del objeto
            resena.id = jsonObject.getString("id")
            resena.idUsuario = jsonObject.getString("idUsuario")
            var fecha = jsonObject.getString("fecha")
            resena.fecha = utilities.parseStringISOAOffsetDateTime(fecha)
            resena.titulo = jsonObject.getString("titulo")
            resena.contenido = jsonObject.getString("contenido")
            resena.nota = jsonObject.getDouble("nota").toFloat()

            resenas.add(resena)
        }
        return resenas
    }
}





