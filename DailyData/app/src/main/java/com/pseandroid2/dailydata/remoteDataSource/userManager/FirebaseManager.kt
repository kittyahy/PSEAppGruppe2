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

import android.provider.Settings.Global.getString
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import com.google.firebase.auth.GetTokenResult
import com.pseandroid2.dailydata.R

class FirebaseManager {

    private var idToken: String = ""

    // Initialize Firebase Auth
    private var auth: FirebaseAuth = Firebase.auth

    init {
        refreshIdToken()
    }

    /**
     * Creates a new firebase authentication Token
     */
    private fun refreshIdToken() {
        val user: FirebaseUser? = auth.currentUser // TODO continue here
        if (user == null) {
            idToken = ""
            return
        }

        val task = user.getIdToken(true)

        while (!task.isComplete) {
            // TODO: Maybe einen Timer für Abbruch
        }

        idToken = ""
        if (task.isSuccessful) {
            if (task.result != null) {
                idToken = task.result.token ?: ""
            }

            // TODO: Send token to your backend via HTTPS
            // ...
        } else {

            // TODO: Handle error -> task.getException();
        }
        Log.d("FirebaseTokenRefresh", "refreshedToken " + idToken)
    }

    /**
     * @param eMail: The email of the user that should be registered
     * @param password: The password of the user that should be registered
     */
    fun registerUserWithEmailAndPassword(email: String, password: String): FirebaseReturnOptions {
        if (email == "" || password == "") {
            return FirebaseReturnOptions.WRONG_INPUT_PARAMETERS
        }

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

    /**
     * @param eMail: The email of the user that should be signed in
     * @param password: The password of the user that should be signed in
     */
    fun signInWithEmailAndPassword(email: String, password: String): FirebaseReturnOptions { // TODO: Baue Tests, sodass valide Eingabe Parameter da sind (email und password valide und != 0)
        if (email == "" || password == "") {
            return FirebaseReturnOptions.WRONG_INPUT_PARAMETERS
        }

        var returnParameter = FirebaseReturnOptions.NOT_PROCCESSED

        val task = auth.signInWithEmailAndPassword(email, password);

        while(!task.isComplete) {
            // TODO: Maybe Abbruchtimer einbauen
        }
        if (task.isSuccessful) {
            // Sign in success
            Log.d("FireBase: ", "signINWithEmail:success")
            returnParameter = FirebaseReturnOptions.SINGED_IN
        } else {
            // If sign in fails
            Log.d("FireBase: ", "signINWithEmail:failure", task.exception)
            returnParameter = FirebaseReturnOptions.SIGN_IN_FAILED
        }
        refreshIdToken()

        return returnParameter
    }

    /**
     * @return FirebaseReturnOptions: The success status of the request
     */
    fun signOut(): FirebaseReturnOptions {
        auth.signOut()

        refreshIdToken()

        return FirebaseReturnOptions.SINGED_OUT
    }

    /**
     * @return String: The firebase ID of the signed in user. If no user is signed in return ""
     */
    fun getUserID(): String {
        if (auth.currentUser == null) {
            return ""
        }
        return auth.currentUser!!.uid ?:  ""
    }

    /**
     * @return String: The username of the signed in user. If no user is signed in return ""
     */
    fun getUserName(): String {
        val user = auth.currentUser
        if (user == null || user.displayName == null) {
            return ""
        }
        return user.displayName?:""
    }

    /**
     * @return String: The email of the signed in user (if existing). If no user is signed in return ""
     */
    fun getUserEMail(): String {
        val user = auth.currentUser
        if (user == null || user.email == null) {
            return ""
        }
        return user.email?:""
    }

    /**
     * @return String: The photoURL of the signed in user (if existing). If no user is signed in return ""
     */
    fun getUserPhotoUrl(): String {
        val user = auth.currentUser
        if (user == null || user.photoUrl == null) {
            return ""
        }
        return user.photoUrl.toString() // Converts Uri to String
    }

    /**
     * @return String: The token of the signed in user. If no user is signed in return ""
     */
    fun getToken(): String{
        return idToken
    }
}