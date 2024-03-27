package com.example.travelwithmeapp.utils

import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class HotelAdvisorAPIManager {

    /**
     * Primera búsqueda que se lanza a la API. Coge un String con un texto, el cual puede contener el nombre de hoteles, lugares...
     * La API lo analizará y devolverá un JSON con hoteles, restaurantes y otros sitios turísticos, que coincidan con ese texto.
     * Si la API lo recibe correctamente, (codigo 200), se recortará el body y se devolverá como String para ser procesado por otras funciones
     * Si no es correcto, devolverá null
     */
    //todo si funciona pasar por parámetro el idioma para poder buscar en ingles
    fun getLocationsAutocomplete(query: String) : String? {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://travel-advisor.p.rapidapi.com/locations/v2/auto-complete?query=${query}&lang=es_EN&units=km")
            .get()
            .addHeader("X-RapidAPI-Key", "5a76f77a06msh959756ab028045bp188164jsn04d82b8d8a5b")
            .addHeader("X-RapidAPI-Host", "travel-advisor.p.rapidapi.com")
            .build()

        val response = client.newCall(request).execute()

        Log.v("response", "$response")

        if(response.isSuccessful) {
            val responseBodyString: String? = response.body?.string()
            Log.v("responseBodyString", "$responseBodyString")
            return responseBodyString
        }
        return null
    }


    /**
     * Parsea el JSON que devuelve la función getLocationsAutocomplete().
     * La respuesta de la API a esa petición podía devolver más lugares que no fueran hoteles, así que esta función se encarga de recoger solamente los hoteles, y obviar el resto
     * Va a devolver un ArrayList de int, y cada int es una "locationID", una id que representa cada hotel.
     * Si el arraylist no esta vacío lo devuelve.
     * Si está vacío devuelve null
     */
    fun parseJSONgetLocationsAutocomplete(json: String): ArrayList<Int>? {
        val jsonObject = JSONObject(json)
        val arrayResultados = jsonObject.getJSONObject("data")
            .getJSONObject("Typeahead_autocomplete")
            .getJSONArray("results")

        val hotelLocationIds = ArrayList<Int>()

        for (i in 0 until arrayResultados.length()) {
            val item = arrayResultados.getJSONObject(i)
            if (item.has("__typename") && item.getString("__typename") == "Typeahead_LocationItem") {
                val detailsV2 = item.getJSONObject("detailsV2")
                if (detailsV2.has("placeType") && detailsV2.getString("placeType") == "HOTEL") {
                    val locationId = detailsV2.getInt("locationId")
                    hotelLocationIds.add(locationId)
                }
            }


        }

        if(!hotelLocationIds.isEmpty()) {
            return hotelLocationIds
        }

        return null

    }

    /**
     * Esta función usa las geoId (o locationId) devueltas por la primera función y recortadas por la segunda,
     * las envía a la API y devuelve un JSON con una lista de hoteles para cada geoId
     */
    fun listHotels(geoId: Int) {
        val client = OkHttpClient()

        val mediaType = "application/json".toMediaTypeOrNull()
        val body = """
    {
        "geoId": ${geoId},
        "checkIn": "2022-03-10",
        "checkOut": "2022-03-15",
        "sort": "POPULARITY",
        "sortOrder": "desc",
        "filters": [],
        "rooms": [],
        "boundingBox": {
            "northEastCorner": {
                "latitude": 12.248278039408776,
                "longitude": 109.1981618106365
            },
            "southWestCorner": {
                "latitude": 12.243407232845051,
                "longitude": 109.1921640560031
            }
        },
        "updateToken": ""
    }
""".trimIndent().toRequestBody(mediaType)
        val request = Request.Builder()
            .url("https://travel-advisor.p.rapidapi.com/hotels/v2/list?currency=USD&units=km&lang=en_US")
            .post(body)
            .addHeader("content-type", "application/json")
            .addHeader("X-RapidAPI-Key", "5a76f77a06msh959756ab028045bp188164jsn04d82b8d8a5b")
            .addHeader("X-RapidAPI-Host", "travel-advisor.p.rapidapi.com")
            .build()

        val response = client.newCall(request).execute()

    }

    /**
     *
     */
    fun parseJSONListHotels(json: String) {
        val jsonObject = JSONObject(json)
        val arrayResultados = jsonObject.getJSONObject("data")
            .getJSONArray("AppPresentation_queryAppListV2")
            .getJSONObject(0)
            .getJSONArray("sections")



    }
}
