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

import android.graphics.Bitmap
import com.pseandroid2.dailydata.model.database.entities.GraphEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectData
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddRow
import com.pseandroid2.dailydata.repository.commandCenter.commands.IllegalOperationException
import com.pseandroid2.dailydata.repository.commandCenter.commands.ProjectCommand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KClass

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
    var members: List<Member> = ArrayList<Member>()
) : Identifiable {
    override lateinit var executeQueue: ExecuteQueue
    private val scope = CoroutineScope(Dispatchers.IO)

    private val IsPossible = mutableMapOf<KClass<out ProjectCommand>, MutableSharedFlow<Boolean>>(
        Pair(AddRow::class, MutableSharedFlow())
    )

    init {
        for (pair in IsPossible) {
            runBlocking { //Todo runBlocking weg
                pair.value.emit(pair.key.members.single {
                    it.name == "IsPossible"
                }.call(this) as Boolean)
            }
        }
    }


    fun update(projectData: ProjectData) {
        TODO("not yet implemented")
    }

    fun addGraphIsPossible(): Flow<Boolean> {
        TODO("addGraphIsPossible")
    }

    //TODO("Robin changes")
    fun addGraph(graph: Graph) {

    }

    fun addRowIsPossible(): Flow<Boolean> {
        return IsPossible[AddRow::class]!!
    }

    //@throws IllegalOperationException
    fun addRow(row: Row) {
        TODO("addRow")
    }

    fun deleteRowIsPossible(row: Row): Flow<Boolean> {
        TODO("deleteRowIsPossible")
    }

    //@throws IllegalOperationException
    fun deleteRow(row: Row) {
        if (row in data) {
            scope.launch { row.delete() }
        } else {
            throw IllegalOperationException()
        }
    }

    fun addColumnIsPossible(): Flow<Boolean> {
        TODO("addColumnIsPossible")
    }

    //@throws IllegalOperationException
    fun addColumn(column: Column) {
        TODO("addColumn")
    }

    fun deleteColumnIsPossible(column: Column): Flow<Boolean> {
        TODO("deleteColumnIsPossible")
    }

    //@throws IllegalOperationException
    fun deleteColumn(column: Column) {
        if (column in table) {
            scope.launch { column.delete() }
        } else {
            throw IllegalOperationException()
        }
    }


    fun addButtonIsPossible(): Flow<Boolean> {
        TODO("addButtonIsPossible")
    }

    //TODO("Robin changes")
    //@throws IllegalOperationException
    fun addButton(button: Button) {
        TODO("addButton")
    }


    fun deleteButtonIsPossible(button: Button): Flow<Boolean> {
        TODO("deleteButtonIsPossible")
    }

    //TODO("Robin changes")
    //@throws IllegalOperationException
    fun deleteButton(button: Button) {
        TODO("deleteButton")
    }

    override fun deleteIsPossible(): Flow<Boolean> {
        TODO("deleteIsPossibleProj")
    }

    //@throws IllegalOperationException
    override suspend fun delete() {
        TODO("deleteProj")
    }


    fun setCellIsPossible(): Flow<Boolean> {
        TODO("setCellIsPossible")
    }
    //@throws IllegalOperationException
    fun setCell(indexRow: Int, indexColumn: Int, content: String) {
        if (indexRow >= 0 && indexRow < data.size) {
            data[indexRow].setCell(indexColumn, content)
        }
        throw IllegalOperationException()
    }

    fun addMemberIsPossible(): Flow<Boolean> {
        TODO("addMemberIsPossible")
    }

    fun addMember(member: Member) {
        //Todo If bedingung schöner machen, keine Magic numbers und aussagekräftigere Exceptions werfen
        if (member !in members && members.size < 25 && isOnlineProject) {
            TODO("addMember")
        } else {
            throw IllegalOperationException()
        }
    }

    fun leaveOnlineProjectIsPossible(): Flow<Boolean> {
        TODO("leaveOnlineProjectIsPossible")//return isOnlineProject
    }

    fun leaveOnlineProject() {
        TODO("leaveOnlineProject")
    }

    fun deleteMemberIsPossible(member: Member): Flow<Boolean> {
        TODO("deleteMemberIsPossible")
    }

    fun deleteMember(member: Member) {
        if (member in members && members.size > 1) {
            TODO("deleteMember")
        } else {
            throw IllegalOperationException()
        }
    }

    fun setAdminIsPossible(): Flow<Boolean> {
        TODO("setAdminIsPossible")
    }

    fun setAdmin(member: Member) {
        TODO("setAdmin")
    }

    fun changeWallpaperIsPossible(): Flow<Boolean> {
        TODO("changeWallpaperIsPossible")
    }

    //TODO("Robin changes")
    fun changeWallpaper(image: Int) {
        TODO("changeWallpaper")
    }

    fun setNotificationIsPossible(): Flow<Boolean> {
        TODO("setNotificationIsPossible")
    }

    fun setNotification(notification: Notification) {
        TODO("setNotification")
    }

    fun deleteNotificationIsPossible(notification: Notification): Flow<Boolean> {
        TODO("deleteNotificationIsPossible")
    }

    fun deleteNotification(notification: Notification) {
        scope.launch { notification.delete() }
    }

    fun addNotificationIsPossible(): Flow<Boolean> {
        TODO("addNotificationIsPossible")
    }

    //TODO("Robin changes")
    fun addNotification(notification: Notification) {

    }

    fun setNameIsPossible(): Flow<Boolean> {
        TODO("setNameIsPossible")
    }

    fun setName(name: String) {
        TODO("setNameProj")
    }


    fun setDescriptionIsPossible(): Flow<Boolean> {
        TODO("setDescriptionIsPossible")
    }

    @JvmName("setDescription1")
    fun setDescription(description: String) {
        TODO("setDescriptionProj")
    }

    fun publishIsPossible(): Flow<Boolean> {
        TODO("publishIsPossibleProj")
    }

    fun publish() {
        TODO("Proj")
    }

    fun setButtonIsPossible(): Flow<Boolean> {
        TODO("setButtonIsPossible")
    }

    fun setButton(button: Button) {
        TODO("setButton")
    }

    override fun connectToDB(executeQueue: ExecuteQueue) {
        val i = ArrayList<Identifiable>()
        i.addAll(table)
        i.addAll(data)
        i.addAll(buttons)
        i.addAll(notifications)
        i.addAll(graphs)
        i.addAll(members)
        super.connectToDB(executeQueue)
    }
}