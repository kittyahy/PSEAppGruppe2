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

package com.pseandroid2.dailydata.model

import com.pseandroid2.dailydata.model.project.ProjectBuilder
import com.pseandroid2.dailydata.model.project.CacheOnlyProject
import com.pseandroid2.dailydata.model.project.SimpleProjectBuilder
import com.pseandroid2.dailydata.model.users.NullUser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ProjectBuilderTester {

    @Test
    fun `test project creation via Builder`() {
        val builder: ProjectBuilder<CacheOnlyProject> = SimpleProjectBuilder()

        val project = builder
            .setId(3)
            .setOnlineId(3)
            .setName("Test")
            .setDescription("Test")
            .build()

        assertEquals(false, project.isOnline)
        assertTrue(project.admin is NullUser)
        assertTrue(project.users.isEmpty())
        @Suppress("Deprecation")
        val skeleton = project.getProjectSkeleton()
        assertEquals(3, skeleton.id)
        assertEquals(3, skeleton.onlineId)
        assertEquals("Test", skeleton.name)
        assertEquals("Test", skeleton.desc)
        assertTrue(project.graphs.isEmpty())
        @Suppress("Deprecation")
        assertTrue(project.settings.getAllSettings().isEmpty())
        assertTrue(project.notifications.isEmpty())
    }
}