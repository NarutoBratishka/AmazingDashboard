package ru.alexeysekatskiy.amazingdashboard.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import ru.alexeysekatskiy.amazingdashboard.R
import ru.alexeysekatskiy.amazingdashboard.databinding.ActivityEditAdsBinding
import ru.alexeysekatskiy.amazingdashboard.utils.LocalityHelper

class EditAdsActivity : AppCompatActivity() {
    private lateinit var rootElement: ActivityEditAdsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        rootElement = ActivityEditAdsBinding.inflate(layoutInflater)
        val rootView = rootElement.root
        super.onCreate(savedInstanceState)
        setContentView(rootView)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,
                LocalityHelper.getAllCountries(this))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        rootElement.spCountry.adapter = adapter
    }
}