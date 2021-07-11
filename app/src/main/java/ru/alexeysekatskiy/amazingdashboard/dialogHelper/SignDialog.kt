package ru.alexeysekatskiy.amazingdashboard.dialogHelper

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import ru.alexeysekatskiy.amazingdashboard.MainActivity
import ru.alexeysekatskiy.amazingdashboard.R
import ru.alexeysekatskiy.amazingdashboard.accountHelper.SignHelper
import ru.alexeysekatskiy.amazingdashboard.databinding.SignDialogBinding

class SignDialog(private val mActivity: MainActivity) {
    private val signHelper = SignHelper(mActivity)


    fun createSignDialog(index: Int) {
        val builder = AlertDialog.Builder(mActivity)
        val layoutElement = SignDialogBinding.inflate(mActivity.layoutInflater)
        val rootView = layoutElement.root
        builder.setView(rootView)
        val dialog = builder.create()
        setDialogState(index, layoutElement)

        layoutElement.btSignUpIn.setOnClickListener {
            signUpInOnClickListener(index, layoutElement, dialog)
        }
        layoutElement.btForgetPassword.setOnClickListener {
            resetPasswordOnClickListener(layoutElement, dialog)
        }

        dialog.show()
    }

    private fun resetPasswordOnClickListener(layoutElement: SignDialogBinding, dialog: AlertDialog) {
        if (layoutElement.etSignEmail.text.isNotEmpty()) {
            mActivity.mAuth.sendPasswordResetEmail(layoutElement.etSignEmail.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    Toast.makeText(
                        mActivity, mActivity.getString(R.string.reset_password_sent), Toast.LENGTH_LONG
                    ).show()
            }
            dialog.dismiss()
        } else {
            Toast.makeText(
                    mActivity, "Для восстановления пароля, пожалуйста, введите ваш email адресс", Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun signUpInOnClickListener(index: Int, layoutElement: SignDialogBinding, dialog: AlertDialog) {
        dialog.dismiss()
        if (index == DialogConst.SIGN_UP_STATE) {
            signHelper.signUpWithEmail(
                    layoutElement.etSignEmail.text.toString(),
                    layoutElement.etSignPassword.text.toString())
        } else if (index == DialogConst.SIGN_IN_STATE) {
            signHelper.signInWithEmail(
                    layoutElement.etSignEmail.text.toString(),
                    layoutElement.etSignPassword.text.toString())
        }
    }

    private fun setDialogState(index: Int, layoutElement: SignDialogBinding) {
        if (index == DialogConst.SIGN_UP_STATE) {
            layoutElement.tvSignTitle.text = mActivity.resources.getString(R.string.ac_sign_up)
            layoutElement.btSignUpIn.text = mActivity.resources.getString(R.string.sign_up_action)
        } else if (index == DialogConst.SIGN_IN_STATE) {
            layoutElement.tvSignTitle.text = mActivity.resources.getString(R.string.ac_sign_in)
            layoutElement.btSignUpIn.text = mActivity.resources.getString(R.string.sign_in_action)
            layoutElement.btForgetPassword.visibility = View.VISIBLE
        }
    }


}