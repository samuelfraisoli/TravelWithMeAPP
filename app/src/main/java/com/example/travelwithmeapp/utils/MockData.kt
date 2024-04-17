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
    val utilities = Utilities()
    fun listaPruebaVuelos(): ArrayList<Vuelo> {
        var listaVuelos = ArrayList<Vuelo>()
        // Crear algunos objetos Aeropuerto para usar en los vuelos
        val aeropuerto1 = Aeropuerto("1", "Aeropuerto 1", "Ciudad 1", "MAD", "País 1", "C1")
        val aeropuerto2 = Aeropuerto("2", "Aeropuerto 2", "Ciudad 2", "PEK", "País 2", "T5")


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

        val trayectoVuelo3 = TrayectoVuelo(
            id = "E3",
            origen = Aeropuerto("A005", "Ruby International", "Rubyville", "RB", "Ruby", "Terminal A"),
            destino = Aeropuerto(
                "A002",
                "Java Airport",
                "Javatown",
                "JT",
                "Java",
                "Terminal B"
            ),
            fechaSalida = Date(Date().time + 7200000), // Añadiendo 2 horas en milisegundos
            fechaLlegada = Date(Date().time + 9000000), // Añadiendo 2.5 horas en milisegundos
            duracion = Duration.parse("PT1H30M"),
            aerolinea = "RubyWings",
            escala = true,
            tiempoEscala = Duration.parse("PT1H")
        )

        val trayectoVuelo4 = TrayectoVuelo(
            id = "E4",
            origen = Aeropuerto(
                "A006",
                "Swift Airport",
                "Swiftville",
                "SW",
                "Swift",
                "Terminal E"
            ),
            destino = Aeropuerto("A007", "Kotlin International", "Kotlinville", "KT", "Kotlin", "Terminal F"),
            fechaSalida = Date(Date().time + 10800000), // Añadiendo 3 horas en milisegundos
            fechaLlegada = Date(Date().time + 12600000), // Añadiendo 3.5 horas en milisegundos
            duracion = Duration.parse("PT1H30M"),
            aerolinea = "SwiftAir"
        )



        listaVuelos.add(
            Vuelo(
                id = "1",
                id_vuelo = "C2123",
                aerolinea = "Aerolínea 1",
                origen = trayectoVuelo1.origen,
                destino = trayectoVuelo2.destino,
                fechaSalida = trayectoVuelo1.fechaSalida,
                fechaLlegada = trayectoVuelo2.fechaLlegada,
                duracion = Duration.parse("PT1H30M"),
                trayectos = arrayListOf(trayectoVuelo1, trayectoVuelo2),
                precio = 150.0,
                tipo = "turista"
            )


        )

        listaVuelos.add(
            Vuelo(
                id = "2",
                id_vuelo = "A123",
                aerolinea = "Aerolínea 1",
                origen = trayectoVuelo1.origen,
                destino = trayectoVuelo2.destino,
                fechaSalida = trayectoVuelo1.fechaSalida,
                fechaLlegada = trayectoVuelo2.fechaLlegada,
                duracion = Duration.parse("PT1H30M"),
                trayectos = arrayListOf(trayectoVuelo1, trayectoVuelo2),
                precio = 150.0,
                tipo = "turista"
            )
        )

        listaVuelos.add(
            Vuelo(
                id = "3",
                id_vuelo = "D678",
                aerolinea = "Aerolinea 2",
                origen = trayectoVuelo4.origen,
                destino = trayectoVuelo4.destino,
                fechaSalida = trayectoVuelo4.fechaSalida,
                fechaLlegada = trayectoVuelo4.fechaLlegada,
                duracion = Duration.parse("PT5H30M"),
                trayectos = arrayListOf(trayectoVuelo4),
                precio = 300.0,
                tipo = "business"
            )
        )

        listaVuelos.add(
            Vuelo(
                id = "4",
                id_vuelo = "W3435",
                aerolinea = "Aerolínea 3",
                origen = trayectoVuelo3.origen,
                destino = trayectoVuelo1.destino,
                fechaSalida = trayectoVuelo3.fechaSalida,
                fechaLlegada = trayectoVuelo1.fechaLlegada,
                duracion = Duration.parse("PT6H30M"),
                trayectos = arrayListOf(trayectoVuelo3, trayectoVuelo1),
                precio = 150.0,
                tipo = "económico"
            )
        )

        listaVuelos.add(
            Vuelo(
                id = "5",
                id_vuelo = "U8970",
                aerolinea = "Aerolínea 4",
                origen = trayectoVuelo4.origen,
                destino = trayectoVuelo1.destino,
                fechaSalida = trayectoVuelo4.fechaSalida,
                fechaLlegada = trayectoVuelo1.fechaLlegada,
                duracion = Duration.parse("PT1H30M"),
                trayectos = arrayListOf(trayectoVuelo4, trayectoVuelo2, trayectoVuelo1),
                precio = 150.0,
                tipo = "turista"
            )
        )

        listaVuelos.add(
            Vuelo(
                id = "6",
                id_vuelo = "T6754",
                aerolinea = "Aerolínea 5",
                origen = trayectoVuelo1.origen,
                destino = trayectoVuelo2.destino,
                fechaSalida = trayectoVuelo1.fechaSalida,
                fechaLlegada = trayectoVuelo2.fechaLlegada,
                duracion = Duration.parse("PT1H30M"),
                trayectos = arrayListOf(trayectoVuelo1, trayectoVuelo2),
                precio = 150.0,
                tipo = "turista"
            )
        )

        return listaVuelos
    }

    fun listaPruebaHoteles(): ArrayList<Hotel> {
        var listaHoteles = ArrayList<Hotel>()
        val hotelSerenidadDetails = HotelDetails(
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



        val hotelOasisDetails = HotelDetails(
            description = "Rodeado de exuberantes jardines tropicales y con vistas panorámicas al mar, Hotel Oasis ofrece una escapada paradisíaca. Sus amplias suites cuentan con terrazas privadas y todas las comodidades necesarias para una estancia inolvidable.",
            web = "www.hoteloasis.com",
            telephone = "+987654321",
            amenities = arrayListOf(
                "Acceso directo a la playa",
                "Piscinas climatizadas",
                "Club infantil",
                "Canchas de tenis y voleibol"
            )
        )

        val hotelMontañaDetails = HotelDetails(
            description = "Situado en las majestuosas montañas, Hotel Montaña ofrece un refugio tranquilo en medio de la naturaleza. Sus acogedoras cabañas y su ambiente rústico son ideales para aquellos que buscan aventuras al aire libre y tranquilidad.",
            web = "www.hotelmontana.com",
            telephone = "+1122334455",
            amenities = arrayListOf(
                "Senderos para caminatas",
                "Excursiones guiadas",
                "Restaurante con cocina local",
                "Área de fogatas"
            )
        )

        val hotelPlayaDetails = HotelDetails(
            description = "Con una ubicación privilegiada frente al mar y una arquitectura inspirada en el estilo colonial, Hotel Playa es el destino perfecto para unas vacaciones junto a la playa. Sus cómodas habitaciones y su ambiente relajado lo convierten en un lugar ideal para desconectar y disfrutar.",
            web = "www.hotelplaya.com",
            telephone = "+5544332211",
            amenities = arrayListOf(
                "Acceso privado a la playa",
                "Piscina infinity",
                "Restaurante al aire libre",
                "Servicio de masajes en la playa"
            )
        )

        val hotelEleganciaDetails = HotelDetails(
            description = "Con un diseño moderno y elegante, Hotel Elegancia redefine el concepto de lujo urbano. Sus espaciosas suites ofrecen vistas panorámicas de la ciudad y están equipadas con las últimas tecnologías. Además, su exclusivo restaurante y su lounge bar son destinos gastronómicos imprescindibles.",
            web = "www.hotelelegancia.com",
            telephone = "+9988776655",
            amenities = arrayListOf(
                "Servicio de conserjería 24/7",
                "Centro de negocios",
                "Salas de reuniones y eventos",
                "Servicio de habitaciones las 24 horas"
            )
        )

        val hotelAventuraDetails = HotelDetails(
            description = "Sumérgete en la emoción y la aventura en Hotel Aventura. Situado en el corazón de la selva tropical, este hotel ofrece una experiencia única para los amantes de la naturaleza y la aventura. Desde emocionantes actividades al aire libre hasta cómodas cabañas, aquí encontrarás todo lo que necesitas para una escapada emocionante.",
            web = "www.hotelaventura.com",
            telephone = "+1122334455",
            amenities = arrayListOf(
                "Excursiones de senderismo",
                "Tirolesa y canopy",
                "Piscina natural",
                "Observación de aves"
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
            details = hotelSerenidadDetails,
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
            details = hotelOasisDetails,
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
            details = hotelMontañaDetails,
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
            details = hotelPlayaDetails,
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
            details = hotelEleganciaDetails,
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
            details = hotelAventuraDetails,
            reviews = arrayListOf("Actividades emocionantes", "Personal atento")
        )
        listaHoteles.add(hotel6)

        return listaHoteles
    }
}