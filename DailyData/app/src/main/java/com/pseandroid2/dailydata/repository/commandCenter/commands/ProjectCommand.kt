package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project
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
    var projectID: Int? = null,
    var onlineProjectID: Long? = null, //Todo Überall noch setzen
    var wentOnline: LocalDateTime? = null,
    var serverRemoveTime: LocalDateTime? = null,
    var commandByUser: String? = null,
    var isProjectAdmin: Boolean = true
) {
    companion object {
        /**
         * Shows whether it is impossible to perform the command action on the given project.
         * Must ALWAYS be overridden in the subclass. Default is calling this fun in the superclass.
         */
        fun isPossible(project: Project): Boolean {
            return true
        }
        /**
         * Shows whether the implemented command should be send to the server, if performed in an
         * online project.
         */
        const val publishable: Boolean = true
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
    open suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        if (publish(repositoryViewModelAPI, publishQueue)) {
            publishQueue.add(this)
        }
    }

    /**

     * Specifies the condition on which to add the command to the publish queue. May be overridden
     * if a command needs special conditions to be added.
     * Conditions may depend on the given publishQueue and repositoryViewModelAPI.

     */
    fun publish(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ): Boolean {
        return onlineProjectID != null && !cameFromServer && publishable
    }
}