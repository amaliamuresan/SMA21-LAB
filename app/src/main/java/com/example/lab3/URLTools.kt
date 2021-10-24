package com.example.lab3

import java.io.IOException
import java.net.*

object URLTools {
    @Throws(MalformedURLException::class, IOException::class)
    fun getLongUrl(shortUrl: String): String {
        var result = shortUrl
        var header: String
        do {
            val url = URL(result)
            HttpURLConnection.setFollowRedirects(false)
            val conn: URLConnection = url.openConnection()
            header = conn.getHeaderField(null)
            val location: String = conn.getHeaderField("location")
            if (location != null) {
                result = location
            }
        } while (header.contains("301"))

        // also decode URL
        result = URLDecoder.decode(result, "UTF-8")
        // trim to extract bitmap
        result = result.substring(result.indexOf("imgurl=") + "imgurl=".length)
        result = result.substring(0, result.indexOf("&"))
        return result
    }
}