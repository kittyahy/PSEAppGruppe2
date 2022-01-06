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