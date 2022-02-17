package com.pseandroid2.dailydata.model

import com.pseandroid2.dailydata.model.project.ProjectBuilder
import com.pseandroid2.dailydata.model.users.NullUser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ProjectBuilderTester {

    @Test
    fun `test project creation via Builder`() {
        val builder: ProjectBuilder<ProjectBuilder> = SimpleProjectBuilder()

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