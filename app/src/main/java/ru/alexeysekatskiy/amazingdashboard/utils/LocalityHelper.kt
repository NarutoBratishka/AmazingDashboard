package ru.alexeysekatskiy.amazingdashboard.utils

import android.content.Context
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

object LocalityHelper {
    fun getAllCountries(context: Context): List<String> {
        var countries = mutableListOf<String>()

        try {
            val iStream: InputStream = context.assets.open("countriesToCities.json")
            val size = iStream.available()
            val bytesArray = ByteArray(size)
            iStream.read(bytesArray)
            val stringLocations = String(bytesArray)

            val jsonLocations = JSONObject(stringLocations)
            val countryNames = jsonLocations.names()

            for (n in 0 until countryNames!!.length()) {
                countries.add(countryNames.getString(n))
            }
        } catch (ex: IOException) {

        }

        return countries
    }
}