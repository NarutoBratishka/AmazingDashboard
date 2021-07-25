package ru.alexeysekatskiy.amazingdashboard.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fxn.pix.Pix
import com.fxn.utility.PermUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.alexeysekatskiy.amazingdashboard.R
import ru.alexeysekatskiy.amazingdashboard.adapters.ImageAdapter
import ru.alexeysekatskiy.amazingdashboard.databinding.ActivityEditAdsBinding
import ru.alexeysekatskiy.amazingdashboard.dialogs.DialogSpinnerHelper
import ru.alexeysekatskiy.amazingdashboard.fragments.FragCloseInterface
import ru.alexeysekatskiy.amazingdashboard.fragments.ImageListFragment
import ru.alexeysekatskiy.amazingdashboard.utils.ImageManager
import ru.alexeysekatskiy.amazingdashboard.utils.ImagePicker
import ru.alexeysekatskiy.amazingdashboard.utils.LocalityHelper


class EditAdsActivity : AppCompatActivity(), FragCloseInterface {
    private var selectImageFrag: ImageListFragment? = null
    lateinit var rootElement: ActivityEditAdsBinding
    private lateinit var imageAdapter: ImageAdapter
    var editImagePos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        rootElement = ActivityEditAdsBinding.inflate(layoutInflater)
        val rootView = rootElement.root
        super.onCreate(savedInstanceState)
        setContentView(rootView)
        init()
    }

    private fun init() {
        imageAdapter = ImageAdapter()
        rootElement.vpImages.adapter = imageAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_CODE_GET_IMAGES) {
            data?.let {
                val returnValue: List<String>? = it.getStringArrayListExtra(Pix.IMAGE_RESULTS)

                if (returnValue?.size!! > 1 && selectImageFrag == null) {
                    openChooseImageFrag(returnValue)
                } else if (returnValue.size == 1 && selectImageFrag == null) {
                    CoroutineScope(Dispatchers.Main).launch {
                        val bitmapList = ImageManager.imageResize(returnValue)
                        imageAdapter.update(bitmapList)
                    }
                } else if (selectImageFrag != null) {
                    selectImageFrag?.updateAdapter(returnValue)
                }; it
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_CODE_GET_SINGLE_IMAGE) {
            data?.let {
                val returnValue: List<String>? = it.getStringArrayListExtra(Pix.IMAGE_RESULTS)

                if (returnValue?.size!! != 0 && selectImageFrag != null) {
                    selectImageFrag?.setSingleImage(returnValue[0], editImagePos)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ImagePicker.getImages(this, 3)
                } else {
                    Toast.makeText(
                        this,
                        "Approve permissions to open Pix ImagePicker",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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

    fun onClickGetImages(view: View) {
        if (imageAdapter.mainList.size == 0)
            ImagePicker.getImages(this, 3)
        else {
            openChooseImageFrag(null)
            selectImageFrag?.updateAdapterFromEdit(imageAdapter.mainList)
        }
    }

    override fun onFragClose(list: List<Bitmap>) {
        imageAdapter.update(list)
        rootElement.scrollviewMain.visibility = View.VISIBLE
        selectImageFrag = null
    }

    private fun openChooseImageFrag(newList: List<String>?) {
        selectImageFrag = ImageListFragment(this, newList)

        rootElement.scrollviewMain.visibility = View.GONE
        val fragMan = supportFragmentManager.beginTransaction()
        fragMan.replace(R.id.place_holder, selectImageFrag!!)
        fragMan.commit()
    }
}