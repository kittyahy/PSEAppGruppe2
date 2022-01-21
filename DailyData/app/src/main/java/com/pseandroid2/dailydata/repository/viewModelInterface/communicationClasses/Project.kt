package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses

import android.graphics.drawable.Drawable
import com.pseandroid2.dailydata.model.database.entities.GraphEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectData

class Project(
    override var id: Long = 0,
    var isOnlineProject: Boolean = false,
    var isAdmin: Boolean = false,
    var title: String = "title missing",
    var description: String= "description missing",
    var wallpaper: String = "wallpaper missing",
    var table: List<Column> = ArrayList<Column>(),
    var data: List<Row> = ArrayList<Row>(),
    var buttons: List<Button> = ArrayList<Button>(),
    var notifications: List<Notification> = ArrayList<Notification>(),
    var graphs: List<Graph> = ArrayList<Graph>(),
    var members: List<Member> = ArrayList<Member>()
): Identifiable {
    fun update(graphEntity: GraphEntity){
        TODO("not yet implemented")
    }
    fun update(notification: com.pseandroid2.dailydata.model.notifications.Notification){
        TODO("not yet implemented")
    }
    fun update(projectData: ProjectData){
        TODO("not yet implemented")
    }
    //Todo nothing mit typ für settings ersetzen
    fun update(settings: Nothing){
        TODO("not yet implemented")
    }
}