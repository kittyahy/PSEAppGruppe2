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

class UserAccount {
    private val firebaseManager: FirebaseManager

    init {
        // Initialize FirebaseManager
        firebaseManager = FirebaseManager()
    }

    fun registerUser(eMail: String, password: String, type: SignInTypes): FirebaseReturnOptions {
        // Hier können dann später, falls mehere Anmeldemöglichkeiten zur Verfüngung stehen, die jeweils ausgewählte Anmeldemöglichkeit ausgewählt werden
        when(type)
        {
            SignInTypes.EMAIL -> return firebaseManager.registerUserWithEmailAndPassword(eMail, password)
            SignInTypes.GOOGLE -> Log.d("SignIn: ", "SignInType not implemented") // TODO: Wenn später noch google Anmeldung hinzugefügt werden sollen
        }
        return FirebaseReturnOptions.REGISTRATION_FAILED
    }

    fun signInUser(eMail: String, password: String, type: SignInTypes): FirebaseReturnOptions {
        // Hier können dann später, falls mehere Anmeldemöglichkeiten zur Verfüngung stehen, die jeweils ausgewählte Anmeldemöglichkeit ausgewählt werden
        when(type)
        {
            SignInTypes.EMAIL -> return firebaseManager.signInWithEmailAndPassword(eMail, password)
            SignInTypes.GOOGLE -> Log.d("SignIn: ", "SignInType not implemented") // TODO: Wenn später noch google Anmeldung hinzugefügt werden sollen
        }
        return FirebaseReturnOptions.REGISTRATION_FAILED
    }

    fun signOut(): FirebaseReturnOptions {
        return firebaseManager.signOut()
    }


    fun getUserID(): String {
        val user = firebaseManager.getUser() ?: return ""
        return user.uid
    }

    fun getUserName(): String {
        val user = firebaseManager.getUser()
        if (user == null || user.displayName == null) {
            return ""
        }
        return user.displayName.toString() // TODO, prüfe ob das richtig ist
    }

    fun getUserEMail(): String {
        val user = firebaseManager.getUser()
        if (user == null || user.email == null) {
            return ""
        }
        return user.email.toString() // TODO, prüfe ob das richtig ist
    }

    fun getUserPhotoUrl(): String {
        val user = firebaseManager.getUser()
        if (user == null || user.photoUrl == null) {
            return ""
        }
        return user.photoUrl.toString() // TODO, prüfe ob das richtig ist
    }

    fun getToken(): String {
        return firebaseManager.getToken()
    }
}