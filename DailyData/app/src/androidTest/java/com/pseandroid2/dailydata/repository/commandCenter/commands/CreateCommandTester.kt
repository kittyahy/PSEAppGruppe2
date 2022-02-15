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

package com.pseandroid2.dailydata.repository.commandCenter.commands

/*
class CreateCommandTester {
    private var testOnlineID: Long = 0
    private lateinit var db: AppDataBase
    private lateinit var remoteDataSourceAPI: RemoteDataSourceAPI

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()
        remoteDataSourceAPI = mockk<RemoteDataSourceAPI>()
        every { remoteDataSourceAPI.createNewOnlineProject("") } returns testOnlineID
    }/*
    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @Test
    fun integrationTest() = runTest{
        val repository = RepositoryViewModelAPI(db, remoteDataSourceAPI)
        val ph = repository.projectHandler
        val flow = ph.projectPreviewFlow.getFlow()
        val project1 = async { flow.first() }
        val deferred: Deferred<Int> = ph.newProjectAsync(
            "user",
            "description",
            0,
            listOf(Column(0, "column", "unit", DataType.TIME)),
            listOf(Button(0, "button", 0, 0)),
            ArrayList<Notification>(),
            ArrayList<Graph>()
        )
        val id = deferred.await()
        val project = project1.await()
        assertEquals(project[0], ph.getProjectByID(id))
    }*/

}
*/
