package com.example.travelwithmeapp.utils

import com.example.travelwithmeapp.models.Address
import com.example.travelwithmeapp.models.Aeropuerto
import com.example.travelwithmeapp.models.TrayectoVuelo
import com.example.travelwithmeapp.models.Hotel
import com.example.travelwithmeapp.models.HotelDetails
import com.example.travelwithmeapp.models.Vuelo
import java.util.Date
import kotlin.time.Duration

class MockData {
    fun listaPruebaVuelos(): ArrayList<Vuelo> {
        var listaVuelos = ArrayList<Vuelo>()
        // Crear algunos objetos Aeropuerto para usar en los vuelos
        val aeropuerto1 = Aeropuerto("1", "Aeropuerto 1", "Ciudad 1", "C1", "País 1")
        val aeropuerto2 = Aeropuerto("2", "Aeropuerto 2", "Ciudad 2", "C2", "País 2")


        val trayectoVuelo1 = TrayectoVuelo(
            id = "E1",
            origen = Aeropuerto("A002", "Java Airport", "Javatown", "JT", "Java", "Terminal B"),
            destino = Aeropuerto(
                "A003",
                "Python International",
                "Pythontown",
                "PT",
                "Python",
                "Terminal C"
            ),
            fechaSalida = Date(),
            fechaLlegada = Date(Date().time + 1800000), // Añadiendo 30 minutos en milisegundos
            duracion = Duration.parse("PT1H30M"),
            aerolinea = "JetBrains Airways",
            escala = true,
            tiempoEscala = Duration.parse("PT1H30M")
        )

        val trayectoVuelo2 = TrayectoVuelo(
            id = "E2",
            origen = Aeropuerto(
                "A003",
                "Python International",
                "Pythontown",
                "PT",
                "Python",
                "Terminal C"
            ),
            destino = Aeropuerto("A004", "C++ Airport", "C++ville", "CP", "C++", "Terminal D"),
            fechaSalida = Date(Date().time + 3600000), // Añadiendo 1 hora en milisegundos
            fechaLlegada = Date(Date().time + 5400000), // Añadiendo 1.5 horas en milisegundos
            duracion = Duration.parse("PT1H30M"),
            aerolinea = "PyFlight"
        )



        listaVuelos.add(
            Vuelo(
                "1",
                "Aerolínea 1",
                aeropuerto1,
                aeropuerto2,
                Date(),
                Date(),
                Duration.parse("PT1H30M"),
                arrayListOf(trayectoVuelo1, trayectoVuelo2),
                150.0
            )
        )

        listaVuelos.add(
            Vuelo(
                "2",
                "Aerolínea 2",
                aeropuerto2,
                aeropuerto1,
                Date(),
                Date(),
                Duration.parse("PT5H30M"),
                arrayListOf(trayectoVuelo1, trayectoVuelo2),
                200.0
            )
        )

        listaVuelos.add(
            Vuelo(
                "3",
                "Aerolínea 3",
                aeropuerto1,
                aeropuerto2,
                Date(),
                Date(),
                Duration.parse("PT2H20M"),
                arrayListOf(trayectoVuelo1, trayectoVuelo2),
                180.0
            )
        )

        listaVuelos.add(
            Vuelo(
                "4",
                "Aerolínea 3",
                aeropuerto1,
                aeropuerto2,
                Date(),
                Date(),
                Duration.parse("PT2H20M"),
                arrayListOf(trayectoVuelo1, trayectoVuelo2),
                180.0
            )
        )

        listaVuelos.add(
            Vuelo(
                "5",
                "Aerolínea 3",
                aeropuerto1,
                aeropuerto2,
                Date(),
                Date(),
                Duration.parse("PT2H20M"),
                arrayListOf(trayectoVuelo1, trayectoVuelo2),
                180.0
            )
        )

        return listaVuelos
    }

    fun listaPruebaHoteles(): ArrayList<Hotel> {
        var listaHoteles = ArrayList<Hotel>()
        val hotelDetails = HotelDetails(
            description = "El Hotel Serenidad es un refugio de lujo ubicado en un entorno idílico, rodeado de exuberante vegetación tropical y vistas panorámicas al mar. Con una arquitectura contemporánea y elegante, ofrece una experiencia de alojamiento incomparable para viajeros exigentes. Sus amplias y lujosas habitaciones están diseñadas para el máximo confort, con comodidades modernas y detalles cuidadosamente seleccionados. Los huéspedes pueden disfrutar de una variedad de opciones gastronómicas que van desde platos locales hasta cocina internacional en sus exclusivos restaurantes. Además, el hotel cuenta con instalaciones de primer nivel, que incluyen una piscina infinita, un spa de clase mundial y servicios personalizados para satisfacer todas las necesidades de sus distinguidos visitantes. En el Hotel Serenidad, cada momento se convierte en una experiencia inolvidable de relajación y lujo.",
            web = "www.google.com",
            telephone = "666666666",
            amenities = arrayListOf(
                "Piscina",
                "Gimnasio",
                "Spa",
                "Restaurante",
                "Bar",
                "Servicio de habitaciones",
                "Wi-Fi gratuito",
                "Estacionamiento"
            )
        )

        val hotelPhotos = ArrayList<String>()
        hotelPhotos.add("https://dynamic-media-cdn.tripadvisor.com/media/photo-o/21/37/69/93/four-seasons-hotel-madrid.jpg?w=700&h=-1&s=1")
        hotelPhotos.add("https://www.momondo.es/himg/65/a8/5b/expediav2-4288651-6d557b-453569.jpg")
        hotelPhotos.add("https://upload.wikimedia.org/wikipedia/commons/7/7d/The_Westin_Palace_Madrid.jpg")
        hotelPhotos.add("https://www.hotelmadridpraga.com/wp-content/uploads/sites/2490/nggallery/content-img//Praga_39-1.jpg")
        hotelPhotos.add("https://www.ahstatic.com/photos/7438_ho_00_p_1024x768.jpg")
        hotelPhotos.add("https://media.timeout.com/images/105965911/750/562/image.jpg")
        hotelPhotos.add("https://cf.bstatic.com/xdata/images/hotel/max1024x768/470960002.jpg?k=a70e747872b27bbb5093759421705a033ff9522988c9d5668d4543aee721b45d&o=&hp=1")
        hotelPhotos.add("https://www.ahstatic.com/photos/9298_ho_00_p_1024x768.jpg")
        hotelPhotos.add("https://dynamic-media-cdn.tripadvisor.com/media/photo-o/2a/43/22/1e/only-you-boutique-hotel.jpg?w=1200&h=-1&s=1")
        hotelPhotos.add("https://www.indigomadrid.com/wp-content/uploads/pool.jpg")

        val hotel1 = Hotel(
            id = "1",
            name = "Hotel Serenidad",
            address = Address(
                addressString = "Calle Principal 123, Ciudad Principal, País Principal",
                street1 = "Calle Principal 123",
                city = "Ciudad Principal",
                country = "País Principal"
            ),
            photos = hotelPhotos.shuffled() as ArrayList<String>,
            details = hotelDetails,
            reviews = arrayListOf("Excelente servicio", "Vistas impresionantes")
        )
        listaHoteles.add(hotel1)

        val hotel2 = Hotel(
            id = "2",
            name = "Hotel Oasis",
            address = Address(
                addressString = "Avenida Central 456, Ciudad Secundaria, País Secundario",
                street1 = "Avenida Central 456",
                city = "Ciudad Secundaria",
                country = "País Secundario"
            ),
            photos = hotelPhotos.shuffled() as ArrayList<String>,
            details = hotelDetails,
            reviews = arrayListOf("Muy relajante", "Personal amable")
        )
        listaHoteles.add(hotel2)

        val hotel3 = Hotel(
            id = "3",
            name = "Hotel Montaña",
            address = Address(
                addressString = "Calle de la Montaña 789, Ciudad de Montaña, País de Montaña",
                street1 = "Calle de la Montaña 789",
                city = "Ciudad de Montaña",
                country = "País de Montaña"
            ),
            photos = hotelPhotos.shuffled() as ArrayList<String>,
            details = hotelDetails,
            reviews = arrayListOf("Vistas espectaculares", "Comida deliciosa")
        )
        listaHoteles.add(hotel3)

        val hotel4 = Hotel(
            id = "4",
            name = "Hotel Playa",
            address = Address(
                addressString = "Avenida Costera 101, Ciudad Costera, País Costero",
                street1 = "Avenida Costera 101",
                city = "Ciudad Costera",
                country = "País Costero"
            ),
            photos = hotelPhotos.shuffled() as ArrayList<String>,
            details = hotelDetails,
            reviews = arrayListOf("Ubicación perfecta", "Playa hermosa")
        )
        listaHoteles.add(hotel4)

        val hotel5 = Hotel(
            id = "5",
            name = "Hotel Elegancia",
            address = Address(
                addressString = "Avenida Elegante 202, Ciudad Elegante, País Elegante",
                street1 = "Avenida Elegante 202",
                city = "Ciudad Elegante",
                country = "País Elegante"
            ),
            photos = hotelPhotos.shuffled() as ArrayList<String>,
            details = hotelDetails,
            reviews = arrayListOf("Estilo impresionante", "Habitaciones cómodas")
        )
        listaHoteles.add(hotel5)

        val hotel6 = Hotel(
            id = "6",
            name = "Hotel Aventura",
            address = Address(
                addressString = "Calle de la Aventura 303, Ciudad de Aventura, País de Aventura",
                street1 = "Calle de la Aventura 303",
                city = "Ciudad de Aventura",
                country = "País de Aventura"
            ),
            photos = hotelPhotos.shuffled() as ArrayList<String>,
            details = hotelDetails,
            reviews = arrayListOf("Actividades emocionantes", "Personal atento")
        )
        listaHoteles.add(hotel6)

        return listaHoteles
    }
}