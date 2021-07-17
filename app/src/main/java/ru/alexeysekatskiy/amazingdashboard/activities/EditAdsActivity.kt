package ru.alexeysekatskiy.amazingdashboard.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.alexeysekatskiy.amazingdashboard.databinding.ActivityEditAdsBinding

class EditAdsActivity : AppCompatActivity() {
    private lateinit var rootElement: ActivityEditAdsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        rootElement = ActivityEditAdsBinding.inflate(layoutInflater)
        val rootView = rootElement.root
        super.onCreate(savedInstanceState)
        setContentView(rootView)
    }
}