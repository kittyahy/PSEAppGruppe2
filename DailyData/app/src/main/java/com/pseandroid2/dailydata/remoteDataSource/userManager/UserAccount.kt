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

    /**
     * @param eMail: Die E-Mail des zu registrierenden Nutzenden
     * @param password: Das Passwort des zu registrierenden Nutzenden
     * @param type: Worüber die Registrierung stattfinden soll (standartmäßig EMail)
     */
    fun registerUser(eMail: String, password: String, type: SignInTypes): FirebaseReturnOptions {
        // Hier können dann später, falls mehere Anmeldemöglichkeiten zur Verfüngung stehen, die jeweils ausgewählte Anmeldemöglichkeit ausgewählt werden
        when(type)
        {
            SignInTypes.EMAIL -> return firebaseManager.registerUserWithEmailAndPassword(eMail, password)
            SignInTypes.GOOGLE -> Log.d("SignIn: ", "SignInType not implemented") // TODO: Wenn später noch google Anmeldung hinzugefügt werden sollen
        }
        return FirebaseReturnOptions.REGISTRATION_FAILED
    }

    /**
     * @param eMail: Die E-Mail des anzumeldenden Nutzenden
     * @param password: Das Passwort des anzumeldenden Nutzenden
     * @param type: Worüber die Anmeldung stattfinden soll (EMail. Falls implementiert auch Google, ...)
     */
    fun signInUser(eMail: String, password: String, type: SignInTypes): FirebaseReturnOptions {
        // Hier können dann später, falls mehere Anmeldemöglichkeiten zur Verfüngung stehen, die jeweils ausgewählte Anmeldemöglichkeit ausgewählt werden
        when(type)
        {
            SignInTypes.EMAIL -> return firebaseManager.signInWithEmailAndPassword(eMail, password)
            SignInTypes.GOOGLE -> Log.d("SignIn: ", "SignInType not implemented") // TODO: Wenn später noch google Anmeldung hinzugefügt werden sollen
        }
        return FirebaseReturnOptions.REGISTRATION_FAILED
    }

    /**
     * @return FirebaseReturnOptions: Der Erfolgstatus der Anfrage
     */
    fun signOut(): FirebaseReturnOptions {
        return firebaseManager.signOut()
    }

    /**
     * @return String: Die Firebase_ID des angemeldeten Nutzenden. Wenn kein Nutzender angemeldet ist wird "" zurückgegeben
     */
    fun getUserID(): String {
        return firebaseManager.getUserID()
    }

    /**
     * @return String: Der Nutzername des angemeldeten Nutzenden. Wenn kein Nutzender angemeldet ist wird "" zurückgegeben
     */
    fun getUserName(): String {
        return firebaseManager.getUserName()
    }

    /**
     * @return String: Die EMail des angemeldeten Nutzenden, falls vorhanden. Wenn kein Nutzender angemeldet ist wird "" zurückgegeben
     */
    fun getUserEMail(): String {
        return getUserEMail()
    }

    /**
     * @return String: Die Url des Nutzerfoto des angemeldeten Nutzenden, falls vorhanden. Wenn kein Nutzender angemeldet ist wird "" zurückgegeben
     */
    fun getUserPhotoUrl(): String {
        return firebaseManager.getUserPhotoUrl()
    }

    /**
     * @return String: Das FirebaseToken des angemeldeten Nutzenden, falls vorhanden. Wenn kein Nutzender angemeldet ist wird "" zurückgegeben
     */
    fun getToken(): String {
        return firebaseManager.getToken()
    }
}