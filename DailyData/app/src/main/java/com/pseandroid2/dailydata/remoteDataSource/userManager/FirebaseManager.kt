package com.pseandroid2.dailydata.remoteDataSource.userManager

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import com.google.firebase.auth.GetTokenResult



class FirebaseManager {

    private var idToken: String = ""

    private lateinit var auth: FirebaseAuth

    init {
        // Initialize Firebase Auth
        auth = Firebase.auth

        refreshIdToken()
    }

    private fun refreshIdToken() {
        val user: FirebaseUser? = auth.currentUser // TODO continue here
        if (user == null) {
            idToken = ""
            return
        }

        val task = user.getIdToken(true)
        task.addOnCompleteListener(OnCompleteListener<GetTokenResult> { task ->
            if (task.isSuccessful) {
                val idToken = task.result.token

                // TODO: Send token to your backend via HTTPS
                // ...
            } else {
                val idToken = ""
                // TODO: Handle error -> task.getException();
            }
            Log.d("FirebaseTokenRefresh", "refreshedToken " + idToken)
        })
        while (!task.isComplete) {
            // TODO: Maybe einen Timer für Abbruch
        }
    }

    fun registerUserWithEmailAndPassword(email: String, password: String): FirebaseReturnOptions {
        Log.d("Register User1", "register User Test")
        var returnParameter: FirebaseReturnOptions = FirebaseReturnOptions.NOT_PROCCESSED

        val task = auth.createUserWithEmailAndPassword(email, password);

        while (!task.isComplete) {
            // TODO: Maybe Abbruchtimer einbauen
        }

        if (task.isSuccessful) {
            // Sign in success
            Log.d("FireBase: ", "createUserWithEmail:success")
            returnParameter = FirebaseReturnOptions.REGISTERED
        } else {
            // If sign in fails
            Log.w("FireBase: ", "createUserWithEmail:failure", task.exception)
            returnParameter = FirebaseReturnOptions.REGISTRATION_FAILED
        }
        refreshIdToken()

        return returnParameter
    }

    fun signInWithEmailAndPassword(email: String, password: String): FirebaseReturnOptions {
        val returnParameter = FirebaseReturnOptions.NOT_PROCCESSED

        val task = auth.signInWithEmailAndPassword(email, password);

        task.addOnCompleteListener {
            if (task.isSuccessful) {
                // Sign in success
                Log.d("FireBase: ", "signINWithEmail:success")
                val returnParameter = FirebaseReturnOptions.SINGED_IN
            } else {
                // If sign in fails
                Log.d("FireBase: ", "signINWithEmail:failure", task.exception)
                val returnParameter = FirebaseReturnOptions.SIGN_IN_FAILED
            }
            refreshIdToken()
        }

        while(!task.isComplete) {
        //while(returnParameter == FirebaseReturnOptions.NOT_PROCCESSED) {
            // TODO: Maybe Abbruchtimer einbauen
        }

        return returnParameter
    }

    fun signOut(): FirebaseReturnOptions {
        auth.signOut()

        refreshIdToken()

        return FirebaseReturnOptions.SINGED_OUT
    }

    fun getUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun getToken(): String{
        return idToken
    }
}