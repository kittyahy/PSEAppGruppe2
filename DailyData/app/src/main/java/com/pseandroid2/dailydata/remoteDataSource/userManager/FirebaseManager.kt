/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/

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
                Log.d("FireBase: ", "signINWithEmail:failure", task.exception)
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