package com.pseandroid2.dailydata.remoteDataSource.UserManager

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserAccount {
    private val firebaseManager: FirebaseManager

    init {
        // Initialize FirebaseManager
        firebaseManager = FirebaseManager()
    }

    fun registerUser(eMail: String, password: String): FirebaseReturnOptions {
        // Hier können dann später, falls mehere Anmeldemöglichkeiten zur Verfüngung stehen, die jeweils ausgewählte Anmeldemöglichkeit ausgewählt werden
        return firebaseManager.registerUserWithEmailAndPassword(eMail, password)
    }

    fun signInUser(eMail: String, password: String): FirebaseReturnOptions {
        return firebaseManager.signInWithEmailAndPassword(eMail, password)
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