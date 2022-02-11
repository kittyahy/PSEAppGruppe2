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

package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import android.util.Log
import com.google.gson.Gson
import com.pseandroid2.dailydata.model.database.entities.ProjectData
import com.pseandroid2.dailydata.model.project.ProjectBuilder
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddButton
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddColumn
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddGraph
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddMember
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddNotification
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddRow
import com.pseandroid2.dailydata.repository.commandCenter.commands.IllegalOperationException
import com.pseandroid2.dailydata.repository.commandCenter.commands.ProjectCommand
import com.pseandroid2.dailydata.repository.commandCenter.commands.PublishProject
import com.pseandroid2.dailydata.util.Consts.LOG_TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject
import com.pseandroid2.dailydata.model.project.Project as ModelProject

class Project(
    override var id: Int = 0,
    var isOnlineProject: Boolean = false,
    var isAdmin: Boolean = false,
    var title: String = "title missing",
    var description: String = "description missing",
    var wallpaper: Int = 0,
    var table: List<Column> = ArrayList<Column>(),
    var data: List<Row> = ArrayList<Row>(),
    var buttons: List<Button> = ArrayList<Button>(),
    var notifications: List<Notification> = ArrayList<Notification>(),
    var graphs: List<Graph> = ArrayList<Graph>(),
    var members: List<Member> = ArrayList<Member>(),
    val repositoryViewModelAPI: RepositoryViewModelAPI
) : Identifiable, Convertible<ModelProject> {
    //Todo wish Flows erstellen, die Commands erlauben dynamisch ihre isPossible Funktionen upzudaten
    override lateinit var executeQueue: ExecuteQueue
    override lateinit var project: Project
    private val scope = CoroutineScope(Dispatchers.IO)

    private val supportedCommands = listOf<KClass<out ProjectCommand>>(
        AddRow::class,
        AddButton::class,
        AddNotification::class,
        AddGraph::class,
        AddMember::class,
        PublishProject::class
    )
    private val isPossible = mutableMapOf<KClass<out ProjectCommand>, MutableSharedFlow<Boolean>>()

    //addColumns is handled separately, because whether a column might not be added depends on its
    // DataType
    private val isPossibleAddColumn = mutableMapOf<DataType, MutableSharedFlow<Boolean>>()

    init {
        @Suppress("DEPRECATION")
        connectToProject(this)
        for (id in getIntefiableChildred()) {
            @Suppress("DEPRECATION")
            id.connectToProject(this)
        }
        @Suppress("DEPRECATION")
        connectToRepository(repositoryViewModelAPI)
        for (commandClass in supportedCommands) {
            isPossible[commandClass] = MutableSharedFlow<Boolean>(1)
        }
        for (pair in isPossible) {
            runBlocking { //Todo runBlocking weg
                isPossible[AddRow::class]!!.emit(AddRow.isPossible(this@Project))
                isPossible[AddButton::class]!!.emit(AddButton.isPossible(this@Project))
                isPossible[AddNotification::class]!!.emit(AddNotification.isPossible(this@Project))
                isPossible[AddGraph::class]!!.emit(AddGraph.isPossible(this@Project))
                isPossible[AddMember::class]!!.emit(AddMember.isPossible(this@Project))
                isPossible[PublishProject::class]!!.emit(PublishProject.isPossible(this@Project))
            }
        }
        for (type in DataType.values()) {
            isPossibleAddColumn[type] = MutableSharedFlow(1)
            @Suppress("DEPRECATION")
            AddColumn.isPossible(project, type)
        }
    }

    @Deprecated("Internal function, should not be used outside the RepositoryViewModelAPI")
    fun update(projectData: ProjectData) {
        TODO("not yet implemented")
    }

    //TODO Deli: Bitte über allen IsPossible Funktionen in diesem Package einfügen.
    /**
     * If false, it would be imprudent to use the corresponding "manipulation" fun.
     * Thus it should be used to block input options from being used if false.
     * e.g. If manipulationIsPossible.first() is false,
     *      users should not be able to call manipulation().
     */
    fun addGraphIsPossible(): Flow<Boolean> {
        return isPossible[AddGraph::class]!!
    }


    suspend fun addGraph(graph: Graph) {
        isPossible[AddGraph::class]!!.emit(false)
        @Suppress("DEPRECATION")
        executeQueue.add(AddGraph(id, graph))
    }

    fun addRowIsPossible(): Flow<Boolean> {
        return isPossible[AddRow::class]!!
    }

    //@throws IllegalOperationException
    suspend fun addRow(row: Row) {
        isPossible[AddRow::class]!!.emit(false)
        @Suppress("DEPRECATION")
        executeQueue.add(AddRow(id, row))
    }

    fun deleteRowIsPossible(row: Row): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>(1)
        runBlocking {
            flow.emit(true)
        }
        return flow
    }

    //@throws IllegalOperationException
    suspend fun deleteRow(row: Row) {
        if (row in data) {
            row.delete()
        } else {
            throw IllegalOperationException()
        }
    }

    /**
     * If false, it would be imprudent to use the corresponding "manipulation" fun.
     * Thus it should be used to block input options from being used if false.
     * Because the result heavily depends on the columns DataType a map is returned, containing a
     * separate Flow (value) for each DataType (key).
     * e.g. If manipulationIsPossible.first() is false,
     *      users should not be able to call manipulation().
     */
    fun addColumnIsPossible(): Map<DataType, Flow<Boolean>> {
        return isPossibleAddColumn
    }

    //@throws IllegalOperationException
    suspend fun addColumn(column: Column) {
        for (type in DataType.values()) {
            isPossibleAddColumn[type]!!.emit(false)
        }
        @Suppress("DEPRECATION")
        executeQueue.add(AddColumn(id, column))
    }

    fun deleteColumnIsPossible(column: Column): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>(1)
        runBlocking {
            flow.emit(true)
        }
        return flow
    }

    //@throws IllegalOperationException
    suspend fun deleteColumn(column: Column) {
        if (column in table) {
            column.delete()
        } else {
            throw IllegalOperationException()
        }
    }


    fun addButtonIsPossible(): Flow<Boolean> {
        return isPossible[AddButton::class]!!
    }


    //@throws IllegalOperationException
    suspend fun addButton(button: Button) {
        isPossible[AddButton::class]!!.emit(false)
        @Suppress("DEPRECATION")
        executeQueue.add(AddButton(id, button))
    }


    fun deleteButtonIsPossible(button: Button): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>(1)
        runBlocking {
            flow.emit(true)
        }
        return flow
    }


    //@throws IllegalOperationException
    suspend fun deleteButton(button: Button) {
        TODO("deleteButton")
    }

    override fun deleteIsPossible(): Flow<Boolean> {
        TODO("deleteIsPossibleProj")
    }

    //@throws IllegalOperationException
    override suspend fun delete() {
        TODO("deleteProj")
    }

    fun addMemberIsPossible(): Flow<Boolean> {
        return isPossible[AddMember::class]!!
    }

    suspend fun addMember(member: Member) {
        //Todo If bedingung schöner machen, keine Magic numbers und aussagekräftigere Exceptions werfen
        if (member !in members && members.size < 24 && isOnlineProject) {
            isPossible[AddMember::class]!!.emit(false)
            @Suppress("DEPRECATION")
            executeQueue.add(AddMember(id, member))
        } else {
            throw IllegalOperationException()
        }
    }

    fun leaveOnlineProjectIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>(1)
        runBlocking {
            flow.emit(true)
        }
        return flow//return isOnlineProject
    }

    suspend fun leaveOnlineProject() {
        TODO("leaveOnlineProject")
    }

    fun deleteMemberIsPossible(member: Member): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>(1)
        runBlocking {
            flow.emit(true)
        }
        return flow
    }

    suspend fun deleteMember(member: Member) {
        if (member in members && members.size > 1) {
            TODO("deleteMember")
        } else {
            throw IllegalOperationException()
        }
    }

    fun setAdminIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>(1)
        runBlocking {
            flow.emit(true)
        }
        return flow
    }

    suspend fun setAdmin(member: Member) {
        TODO("setAdmin")
    }

    fun changeWallpaperIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>(1)
        runBlocking {
            flow.emit(true)
        }
        return flow
    }


    suspend fun changeWallpaper(image: Int) {
        TODO("changeWallpaper")
    }

    fun setNotificationIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>(1)
        runBlocking {
            flow.emit(true)
        }
        return flow
    }

    suspend fun setNotification(notification: Notification) {
        TODO("setNotification")
    }

    fun deleteNotificationIsPossible(notification: Notification): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>(1)
        runBlocking {
            flow.emit(true)
        }
        return flow
    }

    suspend fun deleteNotification(notification: Notification) {
        notification.delete()
    }

    fun addNotificationIsPossible(): Flow<Boolean> {
        return isPossible[AddNotification::class]!!
    }


    suspend fun addNotification(notification: Notification) {
        isPossible[AddNotification::class]!!.emit(false)
        @Suppress("DEPRECATION")
        executeQueue.add(AddNotification(id, notification))
    }

    fun setNameIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>(1)
        runBlocking {
            flow.emit(true)
        }
        return flow
    }

    suspend fun setName(name: String) {
        TODO("setNameProj")
    }


    fun setDescriptionIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>(1)
        runBlocking {
            flow.emit(true)
        }
        return flow
    }

    @JvmName("setDescription1")
    suspend fun setDescription(description: String) {
        TODO("setDescriptionProj")
    }

    fun publishIsPossible(): Flow<Boolean> {
        return isPossible[PublishProject::class]!!
    }

    suspend fun publish() {
        isPossible[AddGraph::class]!!.emit(false)
        @Suppress("DEPRECATION")
        executeQueue.add(PublishProject(id, this))
    }

    fun setButtonIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>(1)
        runBlocking {
            flow.emit(true)
        }
        return flow
    }

    suspend fun setButton(button: Button) {
        TODO("setButton")
    }

    @Suppress("DEPRECATION")
    override fun connectToRepository(repositoryViewModelAPI: RepositoryViewModelAPI) {
        for (id in getIntefiableChildred()) {
            @Suppress("DEPRECATION")
            id.connectToRepository(repositoryViewModelAPI)
        }
        @Suppress("DEPRECATION")
        super.connectToRepository(repositoryViewModelAPI)
    }

    private fun getIntefiableChildred(): List<Identifiable> {
        val i = ArrayList<Identifiable>()
        i.addAll(table)
        i.addAll(data)
        i.addAll(buttons)
        i.addAll(notifications)
        i.addAll(graphs)
        i.addAll(members)
        return i
    }

    override fun toDBEquivalent(): ModelProject {
        TODO("Not yet implemented")
    }

    override fun addYourself(builder: ProjectBuilder<out ModelProject>) {
        TODO("Not yet implemented")
    }
}