package com.example.travelwithmeapp.utils

import android.util.Log
import com.example.travelwithmeapp.models.Hotel
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.net.URLEncoder

class TripadvisorAPIManager {
    /*
    /**
     * Recibe una query en texto normal ej ("hoteles en Madrid"), manda una petición a la API y devuelve 10 localizaciones
     * El idioma acepta un String: "es" si es español, "en" si es en inglés
     * Primero formatea la query a formato URL, y luego la añade a la url de búsqueda
     * Si la petición se realiza correctamente, recorta el body, parsea el JSON que contiene, creando varios objetos de tipo hotel
     * y metiéndolos en un ArrayList de hoteles
     *
     * Si no se realiza correctamente, devuelve null
     */
    fun locationSearch(query : String, idioma: String): ArrayList<Hotel>? {
        val encodedQuery = URLEncoder.encode(query, "UTF-8")
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.content.tripadvisor.com/api/v1/location/search?key=9C7A00524C324586B88C35003A944546&searchQuery=${encodedQuery}&category=hotels&language=${idioma}")
            .get()
            .addHeader("accept", "application/json")
            .build()
        val response = client.newCall(request).execute()

        if(response.isSuccessful) {
            Log.v("locationSearch", "respuesta correcta ${response}")
            val responseBodyString: String? = response.body?.string()

            // Parsea el JSON
            var hoteles = ArrayList<Hotel>()

            val jsonObject = JSONObject(responseBodyString)
            val dataArray = jsonObject.getJSONArray("data")
            for (i in 0 until dataArray.length()) {
                val hotel = Hotel()

                val item = dataArray.getJSONObject(i)
                hotel.id = item.getString("location_id")
                hotel.name = item.getString("name")
                val addressObj = item.getJSONObject("address_obj")
                hotel.direccion.direccion1 = addressObj.getString("street1")
                hotel.direccion.direccion2 = addressObj.getString("street2")
                hotel.direccion.ciudad = addressObj.getString("city")
                hotel.address.country = addressObj.getString("country")
                hotel.address.postalCorde = addressObj.getString("postalcode")
                hotel.address.addressString = addressObj.getString("address_string")

                hoteles.add(hotel)
            }
            return hoteles
        }
        Log.v("locationSearch", "error conexion ${response}")

        return null
    }

    /**
     * Devuelve la URL de 5 fotos de una localización
     * Parámetro -> La id de la localización
     * La llamada devuelve las fotos en 3 tamaños: pequeño, mediano y grande
     * arsea el json y devuelve un objeto HotelPhotos que contiene 3 arraylist, cada uno con las fotos de cada tamaño
     */
    fun locationPhotos(id: String): HotelPhotos? {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.content.tripadvisor.com/api/v1/location/${id}/photos?language=en&offset=0&source=Management&key=9C7A00524C324586B88C35003A944546")
            .get()
            .addHeader("accept", "application/json")
            .build()
        val response = client.newCall(request).execute()

        if(response.isSuccessful) {
            val responseBodyString: String? = response.body?.string()

            val hotelphotos = HotelPhotos()
            val jsonObject = JSONObject(responseBodyString)
            val dataArray = jsonObject.getJSONArray("data")
            for (i in 0 until dataArray.length()) {
                val item = dataArray.getJSONObject(i)
                val images = item.getJSONObject("images")

                val smallImage = images.getJSONObject("small").getString("url")
                val mediumImage = images.getJSONObject("medium").getString("url")
                val largeImage = images.getJSONObject("large").getString("url")

                hotelphotos.smallPhotos.add(smallImage)
                hotelphotos.mediumPhotos.add(mediumImage)
                hotelphotos.bigPhotos.add(largeImage)
            }

            return hotelphotos
        }

        return null
    }

    /**
     * Llama a la API y recoge los detalles del hotel
     * Parámetros -> hotel del que buscará los detalles, idioma
     * Si consigue los datos los añadirá al hotel y devolverá true
     * Si no consigue los datos, devolverá false
     */
    fun LocationDetails(hotel: Hotel, idioma: String): Boolean {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://api.content.tripadvisor.com/api/v1/location/${hotel.id}/details?language=${idioma}&currency=USD&key=9C7A00524C324586B88C35003A944546")
            .get()
            .addHeader("accept", "application/json")
            .build()

        val response = client.newCall(request).execute()

        if(response.isSuccessful) {
            val responseBodyString: String? = response.body?.string()

            val jsonObject = JSONObject(responseBodyString)

            hotel.details.description = jsonObject.getString("description")
            hotel.details.web = jsonObject.getString("web_url")
            hotel.details.telephone = jsonObject.getString("phone")
            val amenitiesArray = jsonObject.getJSONArray("amenities")

            for (i in 0 until amenitiesArray.length()) {
                val amenity = amenitiesArray.getString(i)
                hotel.details.amenities.add(amenity)
            }

            return true

        }

        return false
    }

    //todo falta funcion para coger las reviews

*/
}