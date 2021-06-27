package ru.alexeysekatskiy.amazingdashboard.dialogHelper

import androidx.appcompat.app.AlertDialog
import ru.alexeysekatskiy.amazingdashboard.MainActivity
import ru.alexeysekatskiy.amazingdashboard.R
import ru.alexeysekatskiy.amazingdashboard.databinding.SignDialogBinding

class SignDialogHelper(private val mActivity: MainActivity) {

    fun createSignDialog(index: Int) {
        val builder = AlertDialog.Builder(mActivity)
        val layoutElement = SignDialogBinding.inflate(mActivity.layoutInflater)
        val rootView = layoutElement.root

        if (index == DialogConst.SIGN_UP_STATE) {
            layoutElement.tvSignTitle.text = mActivity.resources.getString(R.string.ac_sign_up)
            layoutElement.btSignUpIn.text = mActivity.resources.getString(R.string.sign_up_action)
        } else {
            layoutElement.tvSignTitle.text = mActivity.resources.getString(R.string.ac_sign_in)
            layoutElement.btSignUpIn.text = mActivity.resources.getString(R.string.sign_in_action)
        }

        builder.setView(rootView)
        builder.show()
    }
}