package ru.alexeysekatskiy.amazingdashboard.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.fxn.pix.Options
import com.fxn.pix.Pix

object ImagePicker {
    const val MAX_IMAGE_COUNT = 3
    const val REQUEST_CODE = 666

    fun getImages(context: AppCompatActivity, imageCount: Int) {
        val options = Options.init()
            .setRequestCode(REQUEST_CODE)
            .setCount(imageCount)                                                   //Number of images to restrict selection count
            .setFrontfacing(false)
            .setMode(Options.Mode.Picture)                                             //Option to select only pictures or videos or bothspanCount = 4                                               //Number for columns in grid
            .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
            .setPath("pix/images")                                         //Custom Path For media Storage

        Pix.start(context, options)
    }
}