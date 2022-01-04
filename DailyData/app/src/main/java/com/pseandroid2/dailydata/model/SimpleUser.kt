package com.pseandroid2.dailydata.model

class SimpleUser(id: String, name: String) : User {
    var userID: String = id
    var userName: String = name

    override fun getId(): String {
        return userID
    }

    override fun getName(): String {
        return userName
    }
}