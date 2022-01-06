package com.pseandroid2.dailydata.remoteDataSource.UserManager

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import com.google.android.gms.tasks.OnSuccessListener
import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.GetTokenResult



class FirebaseManager {
    private lateinit var auth: FirebaseAuth
    private var idToken: String = ""

    init {
        // Initialize Firebase Auth

        Log.d("FireBase: ", Firebase.auth.toString())
        auth = Firebase.auth

        refreshIdToken()
    }

    private fun refreshIdToken() {
        val user: FirebaseUser? = auth.currentUser // TODO continue here
        if (user == null) {
            idToken = ""
            return
        }

        val requestComplete: Boolean = false

        user.getIdToken(true)
            .addOnCompleteListener(OnCompleteListener<GetTokenResult> { task ->
                if (task.isSuccessful) {
                    val idToken = task.result.token
                    // TODO: Send token to your backend via HTTPS
                    // ...
                } else {
                    val idToken = ""
                    // TODO: Handle error -> task.getException();
                }
                val requestComplete = true
            })
        while (!requestComplete) {
            // TODO: Maybe einen Timer für Abbruch
        }
    }

    fun registerUserWithEmailAndPassword(email: String, password: String): FirebaseReturnOptions {
        Log.d("Register User1","register User Test")
        val returnParameter = FirebaseReturnOptions.NOT_PROCCESSED

        val task = auth.createUserWithEmailAndPassword(email, password);

        task.addOnCompleteListener {
            if (task.isSuccessful) {
                // Sign in success
                Log.d("FireBase: ", "createUserWithEmail:success")
                val returnParameter = FirebaseReturnOptions.REGISTERED
            } else {
                // If sign in fails
                Log.w("FireBase: ", "createUserWithEmail:failure", task.exception)
                val returnParameter = FirebaseReturnOptions.REGISTRATION_FAILED
            }
            refreshIdToken()
        }

        while(returnParameter == FirebaseReturnOptions.NOT_PROCCESSED) {
            // TODO: Maybe Abbruchtimer einbauen
        }
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
                Log.w("FireBase: ", "signINWithEmail:failure", task.exception)
                val returnParameter = FirebaseReturnOptions.SIGN_IN_FAILED
            }
            refreshIdToken()
        }

        while(returnParameter == FirebaseReturnOptions.NOT_PROCCESSED) {
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