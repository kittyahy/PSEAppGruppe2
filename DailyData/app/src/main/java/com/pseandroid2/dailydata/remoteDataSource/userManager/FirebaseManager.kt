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

    /**
     * Erzeugt ein neues Firebase Authentifizierungs-Token
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
     * @param eMail: Die E-Mail des zu registrierenden Nutzenden
     * @param password: Das Passwort des zu registrierenden Nutzenden
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
     * @param eMail: Die E-Mail des anzumeldenden Nutzenden
     * @param password: Das Passwort des anzumeldenden Nutzenden
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
     * @return FirebaseReturnOptions: Der Erfolgstatus der Anfrage
     */
    fun signOut(): FirebaseReturnOptions {
        auth.signOut()

        refreshIdToken()

        return FirebaseReturnOptions.SINGED_OUT
    }

    /**
     * @return String: Die Firebase_ID des angemeldeten Nutzenden. Wenn kein Nutzender angemeldet ist wird "" zurückgegeben
     */
    fun getUser(): FirebaseUser? {
        return auth.currentUser
    }

    /**
     * @return String: Das FirebaseToken des angemeldeten Nutzenden, falls vorhanden. Wenn kein Nutzender angemeldet ist wird "" zurückgegeben
     */
    fun getToken(): String{
        return idToken
    }

    fun getUserID(): String {
        if (auth.currentUser == null) {
            return ""
        }
        return auth.currentUser!!.uid ?:  ""
    }

    /**
     * @return String: Der Nutzername des angemeldeten Nutzenden. Wenn kein Nutzender angemeldet ist wird "" zurückgegeben
     */
    fun getUserName(): String {
        val user = getUser()
        if (user == null || user.displayName == null) {
            return ""
        }
        return user.displayName.toString() // TODO, prüfe ob das richtig ist
    }

    /**
     * @return String: Die EMail des angemeldeten Nutzenden, falls vorhanden. Wenn kein Nutzender angemeldet ist wird "" zurückgegeben
     */
    fun getUserEMail(): String {
        val user = getUser()
        if (user == null || user.email == null) {
            return ""
        }
        return user.email.toString() // TODO, prüfe ob das richtig ist
    }

    /**
     * @return String: Die Url des Nutzerfoto des angemeldeten Nutzenden, falls vorhanden. Wenn kein Nutzender angemeldet ist wird "" zurückgegeben
     */
    fun getUserPhotoUrl(): String {
        val user = getUser()
        if (user == null || user.photoUrl == null) {
            return ""
        }
        return user.photoUrl.toString() // TODO, prüfe ob das richtig ist
    }
}