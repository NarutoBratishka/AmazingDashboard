package ru.alexeysekatskiy.amazingdashboard.utils

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.fxn.pix.Options
import com.fxn.pix.Pix
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.alexeysekatskiy.amazingdashboard.activities.EditAdsActivity

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

    fun onGetImages(resultCode: Int, requestCode: Int, data: Intent?, edAct: EditAdsActivity) {
        if (resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_CODE_GET_IMAGES) {
            data?.let {
                val returnValue: List<String>? = it.getStringArrayListExtra(Pix.IMAGE_RESULTS)

                if (returnValue?.size!! > 1 && edAct.selectImageFrag == null) {
                    edAct.openChooseImageFrag(returnValue)
                } else if (returnValue.size == 1 && edAct.selectImageFrag == null) {
                    CoroutineScope(Dispatchers.Main).launch {
                        val bitmapList = ImageManager.imageResize(returnValue)
                        edAct.imageAdapter.update(bitmapList)
                    }
                } else if (edAct.selectImageFrag != null) {
                    edAct.selectImageFrag?.updateAdapter(returnValue)
                }; it
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_GET_SINGLE_IMAGE) {
            data?.let {
                val returnValue: List<String>? = it.getStringArrayListExtra(Pix.IMAGE_RESULTS)

                if (returnValue?.size!! != 0 && edAct.selectImageFrag != null) {
                    edAct.selectImageFrag?.setSingleImage(returnValue[0], edAct.editImagePos)
                }
            }
        }
    }
}