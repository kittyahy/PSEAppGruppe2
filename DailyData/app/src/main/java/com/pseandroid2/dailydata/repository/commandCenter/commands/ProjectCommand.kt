package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import java.time.LocalDateTime

/**
 * Subclasses are encapsulate actions changes a specific local project (identified via project id).
 * They can be transformed into a String sent to other devices and executed there as well.
 * To prevent unwanted changes certain information is set as part of this transmission:
 * How the project is identified online via onlineProjectID.
 * When it went online (wentOnline)
 * How long it stayed on the Server at max (serverRemoveTime)
 * Who first performed the action (commandByUser) and whether this person had administrator rights
 * at this time (isProjectAdmin).

 */
abstract class ProjectCommand(
    var projectID: Int,
    var wentOnline: LocalDateTime? = null,
    var serverRemoveTime: LocalDateTime? = null,
    var commandByUser: String? = null,
    var createdByAdmin: Boolean = false,
    var repositoryViewModelAPI: RepositoryViewModelAPI
) {
    companion object {
        /**
         * Shows whether it is impossible to perform the command action on the given project.
         * Must ALWAYS be overridden in the subclass. Default is calling this fun in the superclass.
         */
        fun isIllegal(project: Project): Boolean {
            return true
        }

    }

    /**
     * Must be set to true if a command obj was received from the server and not created by the
     * devices user.
     */
    var cameFromServer = false

    /**
     * Contains all the actions that are performed locally on the repositoryViewModelAPI by this
     * command and specifies the publishQueue to which the command might be added afterwards.
     */
    open suspend fun execute() {
        if (publish()) {
            TODO("PublishQueue should be exposed from RVMAPI")
            //publishQueue.add(this)
        }
    }

    /**

     * Specifies the condition on which to add the command to the publish queue. May be overridden
     * if a command needs special conditions to be added.
     * Conditions may depend on the given publishQueue and repositoryViewModelAPI.

     */
    open suspend fun publish(): Boolean {
        return !cameFromServer
    }
}