package com.example.travelwithmeapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.travelwithmeapp.models.Direccion
import com.example.travelwithmeapp.models.Aeropuerto
import com.example.travelwithmeapp.models.TrayectoVuelo
import com.example.travelwithmeapp.models.Hotel
import com.example.travelwithmeapp.models.DetallesHotel
import com.example.travelwithmeapp.models.Resena
import com.example.travelwithmeapp.models.Vuelo
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneOffset

/**
 * Class responsible for providing mock data generation for flights, hotels, and hotel photos.
 * This class is used for testing and demo purposes.
 *
 * @author Samuel Fraisoli
 */

class MockData {
    @RequiresApi(Build.VERSION_CODES.O)
    fun listaPruebaVuelos(): ArrayList<Vuelo> {

        val aeropuertos = arrayOf(
            Aeropuerto(1, "Barajas", "Madrid", "MAD", "España"),
            Aeropuerto(2, "El Prat", "Barcelona", "BCN", "España"),
            Aeropuerto(3, "Charles de Gaulle", "París", "CDG", "Francia"),
            Aeropuerto(4, "Heathrow", "Londres", "LHR", "Reino Unido"),
            Aeropuerto(5, "John F. Kennedy", "Nueva York", "JFK", "Estados Unidos")
        )


        val trayectos = arrayOf(
            TrayectoVuelo(
                id = 1,
                id_trayecto = "E1",
                origen = aeropuertos[0], // Madrid
                destino = aeropuertos[1], // Barcelona
                aerolinea = "Ryanair",
                tipo= "turista",
                terminalSalida = "C1",
                terminalLlegada = "B2",
                fechaSalida = OffsetDateTime.of(2024, 4, 21, 14, 30, 0, 0, ZoneOffset.ofHours(2)),
                fechaLlegada = OffsetDateTime.of(2024, 4, 21, 20, 30, 0, 0, ZoneOffset.ofHours(2)),
                escala = true,
                fechaInicioEscala = OffsetDateTime.of(2024, 4, 21, 20, 30, 0, 0, ZoneOffset.ofHours(2)),
                fechaFinEscala = OffsetDateTime.of(2024, 4, 21, 21, 30, 0, 0, ZoneOffset.ofHours(2))
            ),

            TrayectoVuelo(
                id = 2,
                id_trayecto = "E2",
                origen = aeropuertos[1], //Barcelona
                destino = aeropuertos[2], //Paris
                aerolinea = "Air France",
                terminalSalida = "C1",
                terminalLlegada = "B2",
                fechaSalida = OffsetDateTime.of(2024, 4, 22, 10, 0, 0, 0, ZoneOffset.ofHours(1)),
                fechaLlegada = OffsetDateTime.of(2024, 4, 22, 13, 0, 0, 0, ZoneOffset.ofHours(2)),
                escala = true,
                fechaInicioEscala = OffsetDateTime.of(2024, 4, 22, 13, 0, 0, 0, ZoneOffset.ofHours(2)),
                fechaFinEscala = OffsetDateTime.of(2024, 4, 22, 13, 30, 0, 0, ZoneOffset.ofHours(2))
            ),

            TrayectoVuelo(
                id = 3,
                id_trayecto = "E3",
                origen = aeropuertos[2], // París
                destino = aeropuertos[3], // Londres
                aerolinea = "British Airways",
                terminalSalida = "C1",
                terminalLlegada = "B2",
                fechaSalida = OffsetDateTime.of(2024, 4, 23, 9, 0, 0, 0, ZoneOffset.ofHours(1)),
                fechaLlegada = OffsetDateTime.of(2024, 4, 23, 10, 0, 0, 0, ZoneOffset.ofHours(1)),
                escala = false,
                fechaInicioEscala = OffsetDateTime.of(2024, 4, 23, 10, 0, 0, 0, ZoneOffset.ofHours(1)),
                fechaFinEscala = OffsetDateTime.of(2024, 4, 23, 12, 0, 0, 0, ZoneOffset.ofHours(1)),
            ),

            TrayectoVuelo(
                id = 4,
                id_trayecto = "E4",
                origen = aeropuertos[3], // Londres
                destino = aeropuertos[4], // Nueva York
                aerolinea = "British Airways",
                terminalSalida = "C1",
                terminalLlegada = "B2",
                fechaSalida = OffsetDateTime.of(2024, 4, 24, 11, 0, 0, 0, ZoneOffset.ofHours(1)),
                fechaLlegada = OffsetDateTime.of(2024, 4, 24, 15, 0, 0, 0, ZoneOffset.ofHours(-5)),
                escala = false
            ),

            TrayectoVuelo(
                id = 5,
                id_trayecto = "E5",
                origen = aeropuertos[4], // Nueva York
                destino = aeropuertos[0], // Madrid
                aerolinea = "Iberia",
                terminalSalida = "C1",
                terminalLlegada = "B2",
                fechaSalida = OffsetDateTime.of(2024, 4, 25, 20, 0, 0, 0, ZoneOffset.ofHours(-5)),
                fechaLlegada = OffsetDateTime.of(2024, 4, 26, 8, 0, 0, 0, ZoneOffset.ofHours(2)),
                escala = false
            )
        )

        val vuelos = arrayListOf(
            Vuelo(
                id =1,
                id_vuelo = "C2123",
                aerolinea ="Aerolínea 1",
                precio = 150.0,
                trayectos = arrayListOf(trayectos[1], trayectos[2], trayectos[4]),
                tipo = "turista"
            ),

            Vuelo(
                id = 2,
                id_vuelo = "F4501",
                aerolinea = "Air France",
                precio = 250.0,
                trayectos = arrayListOf(trayectos[1], trayectos[2]),
                tipo = "turista"
            ),
            Vuelo(
                id = 3,
                id_vuelo = "BA789",
                aerolinea = "British Airways",
                precio = 300.0,
                trayectos = arrayListOf(trayectos[2], trayectos[3]),
                tipo = "business"
            ),
            Vuelo(
                id = 4,
                id_vuelo = "IB901",
                aerolinea = "Iberia",
                precio = 350.0,
                trayectos = arrayListOf(trayectos[3], trayectos[4]),
                tipo = "business"
            ),
            Vuelo(
                id = 5,
                id_vuelo = "RY5678",
                aerolinea = "Ryanair",
                precio = 200.0,
                trayectos = arrayListOf(trayectos[0], trayectos[2]),
                tipo = "business"
            )
        )

        return vuelos
    }

    fun listaFotosHoteles(): ArrayList<String> {
        val hotelPhotos: ArrayList<String> = arrayListOf(
            "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/21/37/69/93/four-seasons-hotel-madrid.jpg?w=700&h=-1&s=1",
            "https://www.momondo.es/himg/65/a8/5b/expediav2-4288651-6d557b-453569.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/7/7d/The_Westin_Palace_Madrid.jpg",
            "https://www.hotelmadridpraga.com/wp-content/uploads/sites/2490/nggallery/content-img//Praga_39-1.jpg",
            "https://www.ahstatic.com/photos/7438_ho_00_p_1024x768.jpg",
            "https://media.timeout.com/images/105965911/750/562/image.jpg",
            "https://cf.bstatic.com/xdata/images/hotel/max1024x768/470960002.jpg?k=a70e747872b27bbb5093759421705a033ff9522988c9d5668d4543aee721b45d&o=&hp=1",
            "https://www.ahstatic.com/photos/9298_ho_00_p_1024x768.jpg",
            "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/2a/43/22/1e/only-you-boutique-hotel.jpg?w=1200&h=-1&s=1",
            "https://www.indigomadrid.com/wp-content/uploads/pool.jpg",
        )
        return hotelPhotos
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun listaPruebaHoteles(): ArrayList<Hotel> {

        val hotelPhotos = listaFotosHoteles()


        val hoteles = arrayListOf(
            Hotel(
                id = 1,
                nombre = "Hotel Sol",
                fotos = hotelPhotos.shuffled() as ArrayList<String>,
                fechasLibres = arrayListOf(
                    LocalDate.of(2024, 5, 1),
                    LocalDate.of(2024, 5, 10)
                ),
                direccion = Direccion(
                    direccionString = "123 Calle Sol, Madrid, España",
                    ciudad = "Madrid",
                    pais = "España",
                    codPostal = "28001"
                ),
                detalles = DetallesHotel(
                    descripcion = "Un hotel en el centro de Madrid.",
                    web = "www.hotelsol.com",
                    telefono = "123456789",
                    comodidades = arrayListOf("Piscina", "Gimnasio", "Restaurante")
                ),
                resenas = arrayListOf(
                    Resena(
                        id = "1",
                        idUsuario = "1001",
                        fecha = OffsetDateTime.now(),
                        titulo = "Javier Cuesta Soto",
                        contenido = "Me encantó mi estancia en el Hotel Sol.",
                        nota = 5.0F
                    )
                )
            ),
            Hotel(
                id = 2,
                nombre = "Hotel Luna",
                fotos = hotelPhotos.shuffled() as ArrayList<String>,
                fechasLibres = arrayListOf(
                    LocalDate.of(2024, 5, 1),
                    LocalDate.of(2024, 5, 10)
                ),
                direccion = Direccion(
                    direccionString = "456 Avenida Luna, Barcelona, España",
                    ciudad = "Barcelona",
                    pais = "España",
                    codPostal = "08001"
                ),
                detalles = DetallesHotel(
                    descripcion = "Hotel moderno cerca de la playa.",
                    web = "www.hotelluna.com",
                    telefono = "987654321",
                    comodidades = arrayListOf("Spa", "Restaurante", "Terraza")
                ),
                resenas = arrayListOf(
                    Resena(
                        id = "2",
                        idUsuario = "1002",
                        fecha = OffsetDateTime.now(),
                        titulo = "Servicio excelente",
                        contenido = "El personal del hotel fue muy amable y servicial.",
                        nota = 5.0F

                    )
                )
            ),
            // Agregar más hoteles de manera similar
            Hotel(
                id = 3,
                nombre = "Hotel Estrella",
                fotos = hotelPhotos.shuffled() as ArrayList<String>,
                fechasLibres = arrayListOf(
                    LocalDate.of(2024, 5, 1),
                    LocalDate.of(2024, 5, 10)
                ),
                direccion = Direccion(
                    direccionString = "789 Calle Estrella, Sevilla, España",
                    ciudad = "Sevilla",
                    pais = "España",
                    codPostal = "41001"
                ),
                detalles = DetallesHotel(
                    descripcion = "Hotel de lujo en el corazón de Sevilla.",
                    web = "www.hotelestrella.com",
                    telefono = "543210987",
                    comodidades = arrayListOf("Piscina en la azotea", "Bar", "Spa")
                ),
                resenas = arrayListOf(
                    Resena(
                        id = "3",
                        idUsuario = "1003",
                        fecha = OffsetDateTime.now(),
                        titulo = "Experiencia inolvidable",
                        contenido = "El hotel tiene vistas espectaculares desde la piscina en la azotea.",
                        nota = 5.0F
                    )
                )
            ),
            Hotel(
                id = 4,
                nombre = "Hotel Mar",
                fotos = hotelPhotos.shuffled() as ArrayList<String>,
                fechasLibres = arrayListOf(
                    LocalDate.of(2024, 5, 1),
                    LocalDate.of(2024, 5, 10)
                ),
                direccion = Direccion(
                    direccionString = "321 Paseo Mar, Málaga, España",
                    ciudad = "Málaga",
                    pais = "España",
                    codPostal = "29001"
                ),
                detalles = DetallesHotel(
                    descripcion = "Hotel con vistas al mar y acceso directo a la playa.",
                    web = "www.hotelmar.com",
                    telefono = "234567890",
                    comodidades = arrayListOf("Piscina", "Restaurante", "Gimnasio")
                ),
                resenas = arrayListOf(
                    Resena(
                        id = "4",
                        idUsuario = "1004",
                        fecha = OffsetDateTime.now(),
                        titulo = "Ubicación inmejorable",
                        contenido = "El hotel está justo frente al mar, con acceso directo a la playa.",
                        nota = 5.0F
                    )
                )
            ),
            Hotel(
                id = 5,
                nombre = "Hotel Montaña",
                fotos = hotelPhotos.shuffled() as ArrayList<String>,
                fechasLibres = arrayListOf(
                    LocalDate.of(2024, 5, 1),
                    LocalDate.of(2024, 5, 10)
                ),
                direccion = Direccion(
                    direccionString = "654 Calle Montaña, Granada, España",
                    ciudad = "Granada",
                    pais = "España",
                    codPostal = "18001"
                ),
                detalles = DetallesHotel(
                    descripcion = "Hotel rodeado de montañas y naturaleza.",
                    web = "www.hotelmontana.com",
                    telefono = "876543210",
                    comodidades = arrayListOf("Senderismo", "Spa", "Restaurante")
                ),
                resenas = arrayListOf(
                    Resena(
                        id = "5",
                        idUsuario = "1005",
                        fecha = OffsetDateTime.now(),
                        titulo = "Naturaleza en estado puro",
                        contenido = "El hotel ofrece acceso a hermosos senderos de montaña.",
                        nota = 5.0F
                    )
                )
            )
        )

        return hoteles
    }
}