package ru.alexeysekatskiy.amazingdashboard.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File

object ImageManager {
    const val MAX_IMAGE_SIZE = 1280

    fun getImageSize(uri: String): ImageBounds {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeFile(uri, options)
        return if (getImageRotation(uri) == 0)
            ImageBounds(options.outWidth, options.outHeight)
        else
            ImageBounds(options.outHeight, options.outWidth)

    }

    private fun getImageRotation(uri: String): Int {
        val rotation: Int

        val imageFile = File(uri)
        val exif = ExifInterface(imageFile.absolutePath)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        rotation =
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90 ||
            orientation == ExifInterface.ORIENTATION_ROTATE_270)
                90
            else
                0


        return rotation
    }

    suspend fun imageResize(uris: List<String>): List<ImageBounds> = withContext(Dispatchers.IO){
        val tempList = mutableListOf<ImageBounds>()  //списки объектов с параметрами width & height
//        uris.forEach {  }
        uris.forEachIndexed { index, uri ->
            val size = getImageSize(uri)

            val imageRatio = size.width.toFloat() / size.height

            if (imageRatio > 1) {       // Horizontal (width > height)
                if (size.width > MAX_IMAGE_SIZE)
                    tempList.add(ImageBounds(MAX_IMAGE_SIZE, (MAX_IMAGE_SIZE / imageRatio).toInt()))
                else
                    tempList.add(ImageBounds(size.width, size.height))
            } else {                    // Vertical (height > width)
                if (size.height > MAX_IMAGE_SIZE)
                    tempList.add(ImageBounds((MAX_IMAGE_SIZE * imageRatio).toInt(), MAX_IMAGE_SIZE))
                else
                    tempList.add(ImageBounds(size.width, size.height))
            }

            Log.d("qwe: ratio", "Before(W/H): ${size.width} / ${size.height} -> ")

        }

        delay(10000)
        return@withContext tempList
    }

    data class ImageBounds(val width: Int, val height: Int)
}