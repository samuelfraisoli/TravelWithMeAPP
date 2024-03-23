package com.example.travelwithmeapp.utils

import opennlp.tools.tokenize.TokenizerME
import opennlp.tools.tokenize.TokenizerModel
import java.io.FileInputStream
import java.io.InputStream

/**
 * OpenNLP (Open Natural Language Processing) es una biblioteca de procesamiento de lenguaje.
 * Se utiliza para procesar lenguaje natural introducido por los usuarios.
 * En nuestra aplicación la usamos para poder extraer tokens (palabras clave) a partir de los inputs de los usuarios.
 * Así es como podemos analizar el cuadro de búsqueda de hoteles (nos permite reconocer si el usuario busca una ciudad, país u hotel),
 * extraer las palabras clave y mandarlas a la API para que devuelva la información correctamente*/
class OpenNLPManager {

    /**
     * Pide un String, que es la cadena de texto que introduce el usuario, extrae los tokens usando un modelo, y luego los devuelve. */
    fun tokenize(sentence: String): Array<String> {
        // Cargar el modelo del tokenizer
        val modelIn: InputStream = this::class.java.getResourceAsStream("es-ner-location.bin")
        val model = TokenizerModel(modelIn)

        // Inicializar el TokenizerME
        val tokenizer = TokenizerME(model)

        // Tokenizar la oración
        return tokenizer.tokenize(sentence)
    }
}