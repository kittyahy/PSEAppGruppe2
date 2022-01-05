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







class FirebaseManager {
    private lateinit var auth: FirebaseAuth

    init {
        // Initialize Firebase Auth
        auth = Firebase.auth

        reloadUser();
    }

    // Prüfe ob ein Firebaseuser angemeldet ist und update den UserManager entsprechen
    private fun reloadUser() {
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            // TODO: Im UserManager den User updaten
        }
    }

    public fun registerUserWithEmailAndPassword(email: String, password: String): FirebaseReturnOptions {

        val returnParameter = FirebaseReturnOptions.NOT_PROCCESSED

        val task = auth.createUserWithEmailAndPassword(email, password);

        task.addOnCompleteListener {
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("FireBase: ", "createUserWithEmail:success")
                val returnParameter = FirebaseReturnOptions.REGISTERED
            } else {
                // If sign in fails, display a message to the user.
                Log.w("FireBase: ", "createUserWithEmail:failure", task.exception)
                val returnParameter = FirebaseReturnOptions.REGISTRATION_FAILED
            }
        }

        while(returnParameter == FirebaseReturnOptions.NOT_PROCCESSED) {

        }
        return returnParameter
    }
}