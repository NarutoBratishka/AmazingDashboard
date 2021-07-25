package ru.alexeysekatskiy.amazingdashboard.dialogs

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import ru.alexeysekatskiy.amazingdashboard.databinding.ProgressDialogLayoutBinding

object ProgressDialog {

    fun createProgressDialog(context: Activity): AlertDialog {
        val builder = AlertDialog.Builder(context)
        val layoutElement = ProgressDialogLayoutBinding.inflate(context.layoutInflater)
        val rootView = layoutElement.root
        builder.setView(rootView)
        val dialog = builder.create()

        dialog.setCancelable(false)
        dialog.show()

        return dialog
    }

}