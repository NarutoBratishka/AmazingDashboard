package ru.alexeysekatskiy.amazingdashboard.utils

import androidx.appcompat.app.AppCompatActivity
import com.fxn.pix.Options
import com.fxn.pix.Pix

object ImagePicker {
    const val MAX_IMAGE_COUNT = 3
    const val REQUEST_CODE_GET_IMAGES = 666
    const val REQUEST_CODE_GET_SINGLE_IMAGE = 636

    fun getImages(context: AppCompatActivity, imageCount: Int, rCode: Int = REQUEST_CODE_GET_IMAGES) {
        val options = Options.init()
            .setRequestCode(rCode)
            .setCount(imageCount)                                                   //Number of images to restrict selection count
            .setFrontfacing(false)
            .setMode(Options.Mode.Picture)                                             //Option to select only pictures or videos or bothspanCount = 4                                               //Number for columns in grid
            .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
            .setPath("pix/images")                                         //Custom Path For media Storage

        Pix.start(context, options)
    }
}