package ru.alexeysekatskiy.amazingdashboard.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.fxn.pix.Options
import com.fxn.pix.Pix

object ImagePicker {
    const val REQUEST_CODE = 666

    fun getImages(context: AppCompatActivity) {
        val options = Options.init()
            .setRequestCode(REQUEST_CODE)
            .setCount(3)                                                   //Number of images to restrict selection count
            .setFrontfacing(false)
            .setMode(Options.Mode.Picture)                                             //Option to select only pictures or videos or bothspanCount = 4                                               //Number for columns in grid
            .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
            .setPath("pix/images")                                         //Custom Path For media Storage

        Pix.start(context, options)
    }
}