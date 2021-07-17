package ru.alexeysekatskiy.amazingdashboard.accountHelper

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.*
import ru.alexeysekatskiy.amazingdashboard.MainActivity
import ru.alexeysekatskiy.amazingdashboard.R
import ru.alexeysekatskiy.amazingdashboard.constants.FirebaseAuthConstants.ERROR_EMAIL_ALREADY_IN_USE
import ru.alexeysekatskiy.amazingdashboard.constants.FirebaseAuthConstants.ERROR_INVALID_EMAIL
import ru.alexeysekatskiy.amazingdashboard.constants.FirebaseAuthConstants.ERROR_USER_NOT_FOUND
import ru.alexeysekatskiy.amazingdashboard.constants.FirebaseAuthConstants.ERROR_WEAK_PASSWORD
import ru.alexeysekatskiy.amazingdashboard.constants.FirebaseAuthConstants.ERROR_WRONG_PASSWORD
import ru.alexeysekatskiy.amazingdashboard.dialogHelper.GoogleAccConst.GOOGLE_SIGN_IN_REQUEST_CODE

class SignHelper(private val mActivity: MainActivity) {
    private lateinit var googleSignInClient: GoogleSignInClient

    fun signUpWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            mActivity.mAuth.createUserWithEmailAndPassword(email.trim(), password)
                .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result!!.user?.let { mUser -> sendEmailVerification(mUser) }
                    mActivity.uiUpdate(task.result!!.user)
                } else {
                    showToast(mActivity.resources.getString(R.string.sign_up_error))

//                    Log.d("qwe", "Exception: ${task.exception}")
                    val ex = task.exception
                    if (ex is FirebaseAuthUserCollisionException) {
                        when (ex.errorCode) {
                            ERROR_EMAIL_ALREADY_IN_USE -> {
                                showToast(ERROR_EMAIL_ALREADY_IN_USE)
                                linkEmailToGoogle(email, password)
                            }
                        }

                    } else if (ex is FirebaseAuthInvalidCredentialsException) {
                        when (ex.errorCode) {
                            ERROR_INVALID_EMAIL ->
                                showToast(ERROR_INVALID_EMAIL)
                        }
                    }

                    if (ex is FirebaseAuthWeakPasswordException) {
                        Log.d("qwe", "Exception: ${ex.errorCode}")
                        when (ex.errorCode) {
                            ERROR_INVALID_EMAIL ->
                                showToast(ERROR_WEAK_PASSWORD)
                        }
                    }
                }
            }
        }
    }

    private fun linkEmailToGoogle(email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(email, password)
        val currentUser = mActivity.mAuth.currentUser
        if (currentUser != null) {
            currentUser.linkWithCredential(credential)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showToast(mActivity.getString(R.string.link_email_done))
                }
            }
        } else {
            showToast(mActivity.getString(R.string.link_email_error))
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
//                        showToast(mActivity.resources.getString(R.string.sign_in_error))

                    Log.d("qwe", "Exception: ${task.exception}")
                    val ex = task.exception
                        if (ex is FirebaseAuthInvalidCredentialsException) {
                            when (ex.errorCode) {
                                ERROR_INVALID_EMAIL ->
                                    showToast(ERROR_INVALID_EMAIL)
                                ERROR_WRONG_PASSWORD ->
                                    showToast(ERROR_WRONG_PASSWORD)
                            }
                        } else if (ex is FirebaseAuthInvalidUserException) {
                            when (ex.errorCode) {
                                ERROR_USER_NOT_FOUND ->
                                    showToast(mActivity.getString(R.string.error_user_not_found))
                            }
                        }
                    }
                }
        }
    }

    private fun sendEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                showToast(mActivity.resources.getString(R.string.send_verification_done))
            } else {
                showToast(mActivity.resources.getString(R.string.send_verification_error))
            }
        }
    }

    fun signInWithGoogle() {
        googleSignInClient = getSignInClient()
        val intent = googleSignInClient.signInIntent
        mActivity.startActivityForResult(intent, GOOGLE_SIGN_IN_REQUEST_CODE)
    }

    fun signOutGoogle() {
        getSignInClient().signOut()
    }

    private fun getSignInClient(): GoogleSignInClient {
        val gSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(mActivity.getString(R.string.default_web_client_id)).requestEmail().build()

        return GoogleSignIn.getClient(mActivity, gSignInOptions)
    }

    fun signInFirebaseWithGoogle(token: String) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        mActivity.mAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                showToast("Sign in is done")
                mActivity.uiUpdate(task.result!!.user)
            } else {
                showToast(mActivity.resources.getString(R.string.sign_up_error))

                    Log.d("qwe", "Exception: ${task.exception}")
//                val ex = task.exception
//                if (ex is FirebaseAuthUserCollisionException) {
//                    when (ex.errorCode) {
//                        ERROR_EMAIL_ALREADY_IN_USE ->
//                            Toast.makeText(mActivity, ERROR_EMAIL_ALREADY_IN_USE, Toast.LENGTH_LONG).show()
//                    }
//                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show()
    }
}