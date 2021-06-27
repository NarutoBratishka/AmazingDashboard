package ru.alexeysekatskiy.amazingdashboard.accountHelper

import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import ru.alexeysekatskiy.amazingdashboard.MainActivity
import ru.alexeysekatskiy.amazingdashboard.R

class SignHelper(private val mActivity: MainActivity) {

    fun signUpWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            mActivity.mAuth.createUserWithEmailAndPassword(email.trim(), password)
                .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result!!.user?.let { mUser -> sendEmailVerification(mUser) }
                    mActivity.uiUpdate(task.result!!.user)
                } else {
                    Toast.makeText(mActivity,
                        mActivity.resources.getString(R.string.sign_up_error),
                        Toast.LENGTH_LONG)
                    .show()
                }
            }
        }
    }

    fun signInWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            mActivity.mAuth.signInWithEmailAndPassword(email.trim(), password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        task.result!!.user?.let { mUser ->
//                            sendEmailVerification(mUser)
                        }
                        mActivity.uiUpdate(task.result!!.user)
                    } else {
                        Toast.makeText(mActivity,
                            mActivity.resources.getString(R.string.sign_in_error),
                            Toast.LENGTH_LONG)
                        .show()
                    }
                }
        }
    }

    private fun sendEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(mActivity,
                    mActivity.resources.getString(R.string.send_verification_done),
                    Toast.LENGTH_LONG)
                .show()
            } else {
                Toast.makeText(mActivity,
                    mActivity.resources.getString(R.string.send_verification_error),
                    Toast.LENGTH_LONG)
                .show()
            }
        }
    }
}