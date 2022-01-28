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

import android.os.CountDownTimer
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * Carries out firebase authentication calls and has firebase parameter like the current user of the authentication token
 * @param timeout: The time firebase calls can take until they are canceled by a Timeout (is passed for testing timeout in tests)
 */
class FirebaseManager(timeout: Long?) {
    private var idToken: String = ""
    private val timeoutTime: Long = timeout ?: 20000


    // Initialize Firebase Auth
    private var auth: FirebaseAuth = Firebase.auth

    init {
        refreshIdToken(true)
    }

    /**
     * Registers a new user with the requested sign in type.
     * Note: The registration will also fail, if there already exists an account with the registration parameters
     *
     * @param email: The email of the user that should be registered
     * @param password: The password of the user that should be registered
     */
    fun registerUserWithEmailAndPassword(email: String, password: String): FirebaseReturnOptions {
        if (email == "" || password == "") {
            return FirebaseReturnOptions.WRONG_INPUT_PARAMETERS
        }
        var returnParameter = FirebaseReturnOptions.TIMEOUT
        val startTime = System.currentTimeMillis()

        val task = auth.createUserWithEmailAndPassword(email, password)

        while (!task.isComplete) {
            // TODO: Test Counter
            if(System.currentTimeMillis() - startTime >= timeoutTime) {
                return returnParameter
            }
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
        refreshIdToken(true)

        return returnParameter
    }

    /**
     * Signs in an already existing user with email and password
     *
     * @param email: The email of the user that should be signed in
     * @param password: The password of the user that should be signed in
     */
    fun signInWithEmailAndPassword(email: String, password: String): FirebaseReturnOptions {
        if (email == "" || password == "") {
            return FirebaseReturnOptions.WRONG_INPUT_PARAMETERS
        }

        var returnParameter = FirebaseReturnOptions.TIMEOUT
        var startTime = System.currentTimeMillis()

        val task = auth.signInWithEmailAndPassword(email, password)

        while(!task.isComplete) {
            // TODO: Test timeout
            if (System.currentTimeMillis() - startTime > timeoutTime) {
                return returnParameter
            }
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
        refreshIdToken(true)

        return returnParameter
    }

    /**
     * Signs out the currently logged in user
     *
     * @return FirebaseReturnOptions: The success status of the request
     */
    fun signOut(): FirebaseReturnOptions {
        auth.signOut()

        refreshIdToken(true)

        return FirebaseReturnOptions.SINGED_OUT
    }

    /**
     * Get the id of the currently signed in user
     *
     * @return String: The firebase ID of the signed in user. If no user is signed in return ""
     */
    fun getUserID(): String {
        if (auth.currentUser == null) {
            return ""
        }
        return auth.currentUser!!.uid
    }

    /**
     * Get the username of the currently signed in user
     *
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
     * Get the email of the currently signed in user
     *
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
     * Get the user photo url of the currently signed in user
     *
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
     * Get an authentication token
     *
     * @return String: The token of the signed in user. If no user is signed in return ""
     */
    fun getToken(): String{
        refreshIdToken(false)
        return idToken
    }

    /**
     * Returns a firebase authentication token
     *
     * @param forceRefresh: Should the new firebase token be forcefully refreshed
     * @return //TODO
     */
    private fun refreshIdToken(forceRefresh: Boolean) {
        val user: FirebaseUser? = auth.currentUser
        var startTime = System.currentTimeMillis()

        idToken = ""
        if (user == null) {
            return
        }

        val task = user.getIdToken(forceRefresh)


        while (!task.isComplete) {
            // TODO Test timeout
            if (System.currentTimeMillis() - startTime > timeoutTime) {
                return
            }
        }

        if (task.isSuccessful) {
            if (task.result != null) {
                idToken = task.result.token ?: ""
            }
        } else {
            Log.e("FirebaseTokenRefresh", "tokenRefreshError")
        }
        Log.d("FirebaseTokenRefresh", "refreshedToken $idToken")
    }
}