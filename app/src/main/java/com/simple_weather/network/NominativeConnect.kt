package com.simple_weather.network

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object NominativeConnect {
    private const val GET = "GET"
    private const val GEOPARSING = "Geoparsing"
    private const val IDENTIFIER = "guahoo42@gmail.com"
    fun executeGet(targetURL: String?): String? {
        val url: URL
        var connection: HttpURLConnection? = null
        return try {
            url = URL(targetURL)
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = GET
            connection.setRequestProperty(GEOPARSING, IDENTIFIER)
            val `is`: InputStream
            val status = connection.responseCode
            `is` = if (status != HttpURLConnection.HTTP_OK) connection.errorStream else connection.inputStream
            val rd = BufferedReader(InputStreamReader(`is`))
            var line: String?
            val response = StringBuilder()
            while (rd.readLine().also { line = it } != null) {
                response.append(line)
                response.append('\r')
            }
            rd.close()
            response.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            connection?.disconnect()
        }
    }
}