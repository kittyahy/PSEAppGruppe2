package com.pseandroid2.dailydata.links

import android.util.Log
import com.pseandroid2.dailydata.remoteDataSource.appLinks.JoinProjectLinkManager
import org.junit.Assert
import org.junit.Test

class JoinProjectCommandEncryption {
    val joinProjectLinkManager = JoinProjectLinkManager()
    val linkBeginning = "https://https://www.dailydata.com/?projectid="

    /*
    @Test
    fun basicEncryption() {
        var toEncrypt: Long = 1
        var link = joinProjectLinkManager.createLink(toEncrypt)
        Assert.assertEquals(encrypted, joinProjectLinkManager.createLink(toEncrypt))
        Assert.assertEquals(toEncrypt, joinProjectLinkManager.getProjectID(encrypted))
    }

    @Test
    fun encryptZero() {
        var toEncrypt: Long = 0
        var encrypted = joinProjectLinkManager.createLink(toEncrypt)
        Assert.assertEquals("", joinProjectLinkManager.createLink(toEncrypt))
    }

    @Test
    fun encryptNegative() {
        var toEncrypt: Long = -1
        var encrypted = joinProjectLinkManager.createLink(toEncrypt)
        Assert.assertEquals(encrypted, joinProjectLinkManager.createLink(toEncrypt))
        Assert.assertEquals(toEncrypt, joinProjectLinkManager.getProjectID(encrypted))
    }
    */

    @Test
    fun basicLink() {
        val encryptedOne = "KgNZ"
        val expectedLink = linkBeginning+encryptedOne
        Assert.assertEquals(expectedLink, expectedLink, joinProjectLinkManager.createLink(1))
        Assert.assertEquals(1, joinProjectLinkManager.decodePostID(encryptedOne))
    }

    @Test
    fun encryptProjectIDZero() {
        var toEncrypt: Long = 0
        Assert.assertEquals("", joinProjectLinkManager.createLink(toEncrypt))
    }

    @Test
    fun encryptNegativeProjectID() {
        Assert.assertEquals("", joinProjectLinkManager.createLink(-1))
        Assert.assertEquals("", joinProjectLinkManager.createLink(-10))
        Assert.assertEquals("", joinProjectLinkManager.createLink(-100))
    }

    @Test
    fun consistentLinks() {
        // Check if the links stay the same with the same projectID
        Assert.assertEquals(joinProjectLinkManager.createLink(1), joinProjectLinkManager.createLink(1))
    }
}