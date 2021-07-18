package ru.alexeysekatskiy.amazingdashboard.utils

import android.content.Context
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

object LocalityHelper {
    fun getAllCountries(context: Context): List<String> {
        val countries = mutableListOf<String>()

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
            //TODO
        }

        return countries
    }

    fun getAllCities(context: Context, country: String): List<String> {
        val cities = mutableListOf<String>()

        try {
            val iStream: InputStream = context.assets.open("countriesToCities.json")
            val size = iStream.available()
            val bytesArray = ByteArray(size)
            iStream.read(bytesArray)
            val stringLocations = String(bytesArray)

            val jsonLocations = JSONObject(stringLocations)
            val cityNames = jsonLocations.getJSONArray(country)

            for (n in 0 until cityNames.length()) {
                cities.add(cityNames.getString(n))
            }
        } catch (ex: IOException) {
            //TODO
        }

        return cities
    }

    fun filterListData(toFilter: List<String>, searchText: String?): List<String> {
        val tempList = mutableListOf<String>()

        searchText?.let {
            for (selection in toFilter) {
                if (selection.contains(searchText, true))
                    tempList.add(selection)
            }
        }

        if (tempList.isEmpty() || searchText == null)
            return listOf("Нет совпадений")

        return tempList
    }
}