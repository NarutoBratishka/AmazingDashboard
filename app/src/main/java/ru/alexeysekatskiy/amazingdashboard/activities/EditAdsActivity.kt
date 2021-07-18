package ru.alexeysekatskiy.amazingdashboard.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import ru.alexeysekatskiy.amazingdashboard.R
import ru.alexeysekatskiy.amazingdashboard.databinding.ActivityEditAdsBinding
import ru.alexeysekatskiy.amazingdashboard.dialogs.DialogSpinnerHelper
import ru.alexeysekatskiy.amazingdashboard.utils.LocalityHelper

class EditAdsActivity : AppCompatActivity() {
    lateinit var rootElement: ActivityEditAdsBinding
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        rootElement = ActivityEditAdsBinding.inflate(layoutInflater)
        val rootView = rootElement.root
        super.onCreate(savedInstanceState)
        setContentView(rootView)
        init()
    }

    private fun init() {

    }

    fun onClickSelectCountry(view: View) {
        val listCountry = LocalityHelper.getAllCountries(this)
        DialogSpinnerHelper().showSpinnerDialog(this, listCountry, rootElement.tvCountryEditAds)
        if (rootElement.tvCityEditAds.text.toString() != getString(R.string.select_city)) {
            rootElement.tvCityEditAds.text = getString(R.string.select_city)
        }
    }

    fun onClickSelectCity(view: View) {
        val selectedCountry = rootElement.tvCountryEditAds.text.toString()
        if (selectedCountry != getString(R.string.select_country)) {
            val listCity =
                LocalityHelper.getAllCities(this, selectedCountry)
            DialogSpinnerHelper().showSpinnerDialog(this, listCity, rootElement.tvCityEditAds)
        } else {
            Toast.makeText(this, "Необходимо сперва выбрать страну", Toast.LENGTH_SHORT).show()
        }
    }
}