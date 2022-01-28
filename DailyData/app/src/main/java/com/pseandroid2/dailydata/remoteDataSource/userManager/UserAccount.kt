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
import javax.inject.Inject

/**
 * Manages all the user related features
 */
class UserAccount @Inject constructor(fm: FirebaseManager) {
    private val firebaseManager: FirebaseManager = fm // Get the FirebaseManager via dependency injection

    /**
     * Registers a new user with the requested sign in type.
     * Note: The registration will also fail, if there already exists an account with the registration parameters
     *
     * @param eMail: The email of the user that should be registered
     * @param password: The password of the user that should be registered
     * @param type: Through which method should the user be register (eg email)
     */
    fun registerUser(eMail: String, password: String, type: SignInTypes): FirebaseReturnOptions {
        // Choose the correct registration method
        when(type)
        {
            SignInTypes.EMAIL -> return firebaseManager.registerUserWithEmailAndPassword(eMail, password)
            SignInTypes.GOOGLE -> Log.d("SignIn: ", "SignInType not implemented") // TODO: Wenn später noch google Anmeldung hinzugefügt werden sollen
        }
        return FirebaseReturnOptions.REGISTRATION_FAILED
    }

    /**
     * Signs in an already existing user with the passed sign in parameters
     *
     * @param eMail: The email of the user that should be signed in
     * @param password: The password of the user that should be signed in
     * @param type: Through which method should the user be signed in (eg email)
     */
    fun signInUser(eMail: String, password: String, type: SignInTypes): FirebaseReturnOptions {
        // choose the correct sign in method
        when(type)
        {
            SignInTypes.EMAIL -> return firebaseManager.signInWithEmailAndPassword(eMail, password)
            SignInTypes.GOOGLE -> Log.d("SignIn: ", "SignInType not implemented") // TODO: Wenn später noch google Anmeldung hinzugefügt werden sollen
        }
        return FirebaseReturnOptions.REGISTRATION_FAILED
    }

    /**
     * Signs out the currently logged in user
     *
     * @return FirebaseReturnOptions: The success status of the request
     */
    fun signOut(): FirebaseReturnOptions {
        return firebaseManager.signOut()
    }

    /**
     * Get the id of the currently signed in user
     *
     * @return String: The firebase ID of the signed in user. If no user is signed in return ""
     */
    fun getUserID(): String {
        return firebaseManager.getUserID()
    }

    /**
     * Get the username of the currently signed in user
     *
     * @return String: The username of the signed in user. If no user is signed in return ""
     */
    fun getUserName(): String {
        return firebaseManager.getUserName()
    }

    /**
     * Get the email of the currently signed in user
     *
     * @return String: The email of the signed in user (if existing). If no user is signed in return ""
     */
    fun getUserEMail(): String {
        return firebaseManager.getUserEMail()
    }

    /**
     * Get the user photo url of the currently signed in user
     *
     * @return String: The photoURL of the signed in user (if existing). If no user is signed in return ""
     */
    fun getUserPhotoUrl(): String {
        return firebaseManager.getUserPhotoUrl()
    }

    /**
     * Get an authentication token
     *
     * @return String: The token of the signed in user. If no user is signed in return ""
     */
    fun getToken(): String {
        return firebaseManager.getToken()
    }
}