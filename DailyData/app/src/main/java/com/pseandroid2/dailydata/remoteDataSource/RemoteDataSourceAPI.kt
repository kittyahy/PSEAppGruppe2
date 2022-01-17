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

package com.pseandroid2.dailydata.remoteDataSource

import com.pseandroid2.dailydata.remoteDataSource.queue.FetchRequestQueueObserver
import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandQueueObserver
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import com.pseandroid2.dailydata.remoteDataSource.userManager.SignInTypes
import com.pseandroid2.dailydata.remoteDataSource.userManager.UserAccount
import java.time.LocalDateTime

class RemoteDataSourceAPI {
    private val userAccount: UserAccount
    private val serverManager: ServerManager

    init {
        userAccount = UserAccount()
        serverManager = ServerManager()
    }

// -----------------------------FireBase-------------------------------
    // -----------------------------UserAccount-------------------------------
    /**
     * @param eMail: Die E-Mail des zu registrierenden Nutzenden
     * @param password: Das Passwort des zu registrierenden Nutzenden
     * @param type: Worüber die Registrierung stattfinden soll (standartmäßig EMail)
     */
    fun registerUser(eMail: String, password: String, type: SignInTypes) : FirebaseReturnOptions {
        return userAccount.registerUser(eMail, password, type)
    }

    /**
     * @param eMail: Die E-Mail des anzumeldenden Nutzenden
     * @param password: Das Passwort des anzumeldenden Nutzenden
     * @param type: Worüber die Anmeldung stattfinden soll (EMail. Falls implementiert auch Google, ...)
     */
    fun signInUser(eMail: String, password: String, type: SignInTypes) : FirebaseReturnOptions {
        return userAccount.signInUser(eMail, password, type)
    }

    /**
     * @return FirebaseReturnOptions: Der Erfolgstatus der Anfrage
     */
    fun signOut() : FirebaseReturnOptions {
        return userAccount.signOut()
    }

    // -----------------------------UserDetails-------------------------------
    /**
     * @return String: Die Firebase_ID des angemeldeten Nutzenden. Wenn kein Nutzender angemeldet ist wird "" zurückgegeben
     */
    fun getUserID(): String {
        return userAccount.getUserID()
    }

    /**
     * @return String: Der Nutzername des angemeldeten Nutzenden. Wenn kein Nutzender angemeldet ist wird "" zurückgegeben
     */
    fun getUserName(): String {
        return userAccount.getUserName()
    }

    /**
     * @return String: Die EMail des angemeldeten Nutzenden, falls vorhanden. Wenn kein Nutzender angemeldet ist wird "" zurückgegeben
     */
    fun getUserEMail(): String {
        return userAccount.getUserEMail()
    }

    /**
     * @return String: Die Url des Nutzerfoto des angemeldeten Nutzenden, falls vorhanden. Wenn kein Nutzender angemeldet ist wird "" zurückgegeben
     */
    fun getUserPhotoUrl(): String {
        return userAccount.getUserPhotoUrl()
    }

    /** // TODO: vllt das hier verbergen und Token nicht an Repository weitergeben
     * @return String: Das FirebaseToken des angemeldeten Nutzenden, falls vorhanden. Wenn kein Nutzender angemeldet ist wird "" zurückgegeben
     */
    // -----------------------------Authentification-------------------------------
    fun getToken(): String {
        return userAccount.getToken()
    }

// -----------------------------ServerAccess-------------------------------
    // -----------------------------GreetingController-------------------------------
    /**
     * @return Boolean: Ist eine Serververbindung möglich wird true ausgegeben, sonst false
     */
    fun connectionToServerPossible(): Boolean {
        return serverManager.greet()

        return false;
    }

    // -----------------------------PostsController-------------------------------
    /**
     * @return Collection<String>: Die Vorschau der Posts auf dem Server (als JSONs)
     */
    fun getAllPostPreview(): Collection<String> {
        // TODO: Implement Method

        return mutableListOf("");
    }

    /**
     * @param fromPost: Die Id des gesuchten Posts
     * @return Collection<String>: Gibt den zur ID entsprechenden detaillierten Post als JSON aus
     */
    fun getPostDetail(fromPost: Int): Collection<String> {
        // TODO: Implement Method

        return mutableListOf("");
    }

    /**
     * @param fromPost: Der Post vom dem das ProjectTemplate heruntergeladen werden soll
     * @return String - Das angefragte ProjectTemplate als JSON Datei
     */
    fun getProjectTemplate(fromPost: Int): String { // TODO: Es existiert kein JSON Rückgabewert, lass dir was neues einfallen
        // TODO: Implement Method

        return "";
    }

    /**
     * @param fromPost: Der Post vom dem das ProjectTemplate heruntergeladen werden soll
     * @param templateNumber: Welches GraphTemplate von dem Post heruntergeladen werden soll
     * @return String - Das angefragte GraphTemplate als JSON Datei
     */
    fun getGraphTemplate(fromPost: Int, templateNumber: Int): String {
        // TODO: Implement Method

        return "";
    }

    // Wish-Kriterium
    /**
     * @param postPreview: Der Post der hinzugefügt werden soll als JSON
     * @param projectTemplate: Das zum Post gehörende ProjectTemplate als JSON
     * @param Collection<String>: Die zum Post gehörenden GraphTemplates als JSON
     */
    fun addPost(postPreview: String, user: String, projectTemplate: String, graphTemplate: Collection<String>) {
        // TODO: Implement Method

        return;
    }

    // Wish-Kriterium
    /**
     * @param postID: Die ID des zu entfernenden Posts
     * @param user:  Die ID vom Nutzenden, der den Post vom Server löschen möchte
     */
    fun removePost(postID: Long, user: String) {
        // TODO: Implement Method

        return;
    }

    // -----------------------------ProjectParticipantsController-------------------------------
    /**
     * @param userToAdd: Die ID vom Nutzenden, der einem Projekt hinzugefügt werden soll
     * @param projectID: Die ID vom Projekt, zu dem der Nutzende hinzugefügt werden soll
     */
    fun addUser(userToAdd: String, projectID: Long): Boolean{
        // TODO: Implement Method

        return false;
    }

    /**
     * @param userToRemove: Die ID vom Nutzenden, der vom Online-Projekt entfernt werden soll
     * @param projectID: Die ID vom Projekt, von dem der Nutzende entfernt werden soll
     */
    fun removeUser(userToRemove: String, projectID: Long): Boolean{
        // TODO: Implement Method

        return false;
    }

    /**
     * @return LONG: Gibt die ID des erstellten OnlineProjekts wieder. Bei Fehler wird -1 ausgegeben
     */
    fun addProject(): Long{
        // TODO: Implement Method

        return -1;
    }

    // -----------------------------DeltaController-------------------------------
    /**
     * @param projectID: Die ID des Projekts, zu welchem die ProjectCommands hochgeladen werden sollen
     * @param projectCommands: Die ProjectCommands die an den Server gesendet werden sollen als JSONs
     * @return Collection<String> ist eine Collection von JSON Dateien, die erfolgreich an den Server übertragen worden konnten // TODO: ändere dies im Entwurfsdokument
     */
    fun sendCommandsToServer(projectID: Long, projectCommands: Collection<String>): Collection<String> {
        // TODO: Implement Method

        return mutableListOf("");
    }

    /**
     * @param projectID: Die ID von dem Projekt, dessen FetchRequests man in die FetchRequestQueue laden will
     */
    fun getDeltasFromServer(projectID: Long): Unit {
        // TODO: Implement Method

        return
    }

    /**
     * @param projectCommand: Der ProjectCommand, der auf den Server hochgeladen werden soll als JSON
     * @param forUser: Die ID des Nutzenden, dessen FetchRequest man beantworten möchte
     * @param initialAddedDate: // TODO
     * @param projectID: Die Projekt ID, zu dem man den ProjectCommand hochladen möchte
     * @param wasAdmin: War der Nutzende zum Zeitpunkt, als der ProjectCommand erstellt wurde, ein Projekt-Administrator des Projektes
     */
    fun provideOldData(projectCommand: String, forUser: String, initialAddedDate: LocalDateTime, initialAddedBy: String, projectID: Long, wasAdmin: Boolean) {
        // TODO: Implement Method

        return;
    }

    /**
     * @param LocalDateTime: Gibt die Zeit an, wie lange ein ProjectCommand auf dem Server sein kann, bevor dieser vom Server gelöscht wird
     */
    fun getRemoveTime(): LocalDateTime {
        // TODO: Implement Method0

        return LocalDateTime.parse("0001-01-01T00:00")
    }

    // -----------------------------FetchRequestController-------------------------------
    /**
     * @param projectID: Die ID des Projekts, zu dem die RequestInfo hochgeladen werden soll
     * @param requestInfo: Die RequestInfo als JSON
     */
    fun demandOldData(projectID: Long, requestInfo: String) {
        // TODO: Implement Method

        return;
    }

    /**
     * @param projectID: Die ID des Projekts, von dem alle FetchRequests geholt werden sollen
     */
    fun getFetchRequests(projectID: Long){ // TODO: Ausgabe ist im Entwurfsheft Collection<FetchRequest>
        // TODO: Implement Method

        return;
    }

    // -----------------------------ObserverLogic-------------------------------
    /**
     * @param observer: Der Observer, der zur FetchRequestQueue hinzugefügt werden soll
     */
    fun addObserverToFetchRequestQueue(observer: FetchRequestQueueObserver) {
        serverManager.addObserverToFetchRequestQueue(observer)
    }

    /**
     * @param observer: Der Observer, der von der FetchRequestQueue entfernt werden soll
     */
    fun unregisterObserverFromFetchRequestQueue(observer: FetchRequestQueueObserver) {
        serverManager.unregisterObserverFromFetchRequestQueue(observer)
    }

    /**
     * @return INT: Die Länge der FetchRequestQueue
     */
    fun getFetchRequestQueueLength(): Int {
        return serverManager.getFetchRequestQueueLength()
    }

    /**
     * @param observer: Der Observer, der zur ProjectCommandQueue hinzugefügt werden soll
     */
    fun addObserverToProjectCommandQueue(observer: ProjectCommandQueueObserver) {
        serverManager.addObserverToProjectCommandQueue(observer)
    }

    /**
     * @param observer: Der Observer, der von der ProjectCommandQueue entfernt werden soll
     */
    fun unregisterObserverFromProjectCommandQueue(observer: ProjectCommandQueueObserver) {
        serverManager.unregisterObserverFromProjectCommandQueue(observer)
    }

    /**
     * @return INT: Die Länge der ProjectCommandQueue
     */
    fun getProjectCommandQueueLength(): Int {
        return serverManager.getProjectCommandQueueLength()
    }
}