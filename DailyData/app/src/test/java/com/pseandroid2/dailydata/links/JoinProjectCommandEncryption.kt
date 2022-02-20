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

package com.pseandroid2.dailydata.links

import com.pseandroid2.dailydata.ui.link.appLinks.JoinProjectLinkManager
import org.junit.Assert
import org.junit.Test

class JoinProjectCommandEncryption {
    private val joinProjectLinkManager = JoinProjectLinkManager()

    private val linkStart =
        "https://dailydata.page.link/?link=https://www.dailydata.com/?projectid%3D"
    private val linkEnd = "&apn=com.pseandroid2.dailydata"

    @Test
    fun basicEncryption() {
        val toEncrypt: Long = 1
        val encrypted = "KgNZ"
        val createdLink =
            "https://dailydata.page.link/?link=https://www.dailydata.com/?projectid%3DKgNZ&apn=com.pseandroid2.dailydata"
        Assert.assertEquals(createdLink, joinProjectLinkManager.createLink(toEncrypt))
        Assert.assertEquals(toEncrypt, joinProjectLinkManager.decodePostID(encrypted))
    }

    @Test
    fun encryptZero() {
        val toEncrypt: Long = 0
        Assert.assertEquals("", joinProjectLinkManager.createLink(toEncrypt))
    }

    @Test
    fun encryptNegative() {
        val toEncrypt: Long = -1
        val encrypted = joinProjectLinkManager.createLink(toEncrypt)
        Assert.assertEquals(encrypted, joinProjectLinkManager.createLink(toEncrypt))
        Assert.assertEquals(toEncrypt, joinProjectLinkManager.decodePostID(encrypted))
    }

    @Test
    fun basicLink() {
        val encryptedOne = "KgNZ"
        val expectedLink = linkStart + encryptedOne + linkEnd
        Assert.assertEquals(expectedLink, expectedLink, joinProjectLinkManager.createLink(1))
        Assert.assertEquals(1, joinProjectLinkManager.decodePostID(encryptedOne))
    }

    @Test
    fun encryptProjectIDZero() {
        val toEncrypt: Long = 0
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
        Assert.assertEquals(
            joinProjectLinkManager.createLink(1),
            joinProjectLinkManager.createLink(1)
        )
    }

    @Test
    fun decryptEmptyProjectID() {
        Assert.assertEquals(-1, joinProjectLinkManager.decodePostID(""))
    }

    @Test
    fun decryptInvalidProjectID() {
        Assert.assertEquals(-1, joinProjectLinkManager.decodePostID("IchbinUngueltig"))
    }
}