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

    fun registerUser(eMail: String, password: String, type: SignInTypes) : FirebaseReturnOptions {
        return userAccount.registerUser(eMail, password, type)
    }

    fun signInUser(eMail: String, password: String, type: SignInTypes) : FirebaseReturnOptions {
        return userAccount.signInUser(eMail, password, type)
    }

    fun signOut() : FirebaseReturnOptions {
        return userAccount.signOut()
    }

    // -----------------------------UserDetails-------------------------------
    fun getUserID(): String {
        return userAccount.getUserID()
    }

    fun getUserName(): String {
        return userAccount.getUserName()
    }

    fun getUserEMail(): String {
        return userAccount.getUserEMail()
    }

    fun getUserPhotoUrl(): String {
        return userAccount.getUserPhotoUrl()
    }

    // -----------------------------Authentification-------------------------------
    fun getToken(): String {
        return userAccount.getToken()
    }

// -----------------------------ServerAccess-------------------------------
    // -----------------------------GreetingController-------------------------------
    fun connectionToServerPossible(): Boolean {
        // TODO: Implement Method

        return false;
    }

    // -----------------------------PostsController-------------------------------
    fun getAllPostPreview(): Collection<String> {
        // TODO: Implement Method

        return mutableListOf("");
    }

    fun getPostDetail(fromPost: Int): Collection<String> {
        // TODO: Implement Method

        return mutableListOf("");
    }

    /**
     * @return String - Das ProjectTemplate als JSON Datei
     */
    fun getProjectTemplate(fromPost: Int): String { // TODO: Es existiert kein JSON Rückgabewert, lass dir was neues einfallen
        // TODO: Implement Method

        return "";
    }

    /**
     * @return String - Das GraphTemplate als JSON Datei
     */
    fun getGraphTemplate(fromPost: Int, templateNumber: Int): String {
        // TODO: Implement Method

        return "";
    }

    // Wish-Kriterium
    /**
     * @param postPreview:String ist eine JSON Dateie
     * @param projectTemplate:String ist eine JSON Dateie
     * @param Collection<String> ist eine Collection von JSON Dateien
     */
    fun addPost(postPreview: String, user: String, projectTemplate: String, graphTemplate: Collection<String>) {
        // TODO: Implement Method

        return;
    }

    fun removePost(postID: Long, user: String) {
        // TODO: Implement Method

        return;
    }

    // -----------------------------ProjectParticipantsController-------------------------------
    fun addUser(userToAdd: String, projectID: Long): Boolean{
        // TODO: Implement Method

        return false;
    }

    fun removeUser(userToRemove: String, projectID: Long, user: String): Boolean{
        // TODO: Implement Method

        return false;
    }

    // gibt ProjectID zurück
    fun addProject(user: String): Long{
        // TODO: Implement Method

        return -1;
    }

    // -----------------------------DeltaController-------------------------------
    /**
     * @param projectCommands:Collection<String> ist eine Collection von JSON Dateien
     * @return Collection<String> ist eine Collection von JSON Dateien, die nicht erfolgreich übertragen worden konnten // TODO: Vllt die returnen, die erfolgreich übertragen worden konnten
     */
    fun sendCommandsToServer(projectID: Long, projectCommands: Collection<String>, user: String): Collection<String> {
        // TODO: Implement Method

        return mutableListOf("");
    }

    /**
     * @return Collection<String> ist eine Collection von JSON Dateien
     */
    fun getDeltasFromServer(projectID: Long, user: String): Collection<String> {
        // TODO: Implement Method

        return mutableListOf("");
    }

    /**
     * @param projectCommand:String ist eine JSON Dateien
     */
    fun provideOldData(projectCommand: String, forUser: String, initialAddedDate: LocalDateTime, initialAddedBy: String, projectID: Long, wasAdmin: Boolean) {
        // TODO: Implement Method

        return;
    }

    fun getRemoveTime(): LocalDateTime {
        // TODO: Implement Method0

        return LocalDateTime.parse("0000-00-00 00:00")
    }

    // -----------------------------FetchRequestController-------------------------------
    /**
     * @param requestInfo:String ist eine JSON Datei
     */
    fun demandOldData(user: String, projectID: Long, requestInfo: String) {
        // TODO: Implement Method

        return;
    }

    fun getFetchRequests(user: String, projectID: Long){ // TODO: Ausgabe ist im Entwurfsheft Collection<FetchRequest>
        // TODO: Implement Method

        return;
    }

    // -----------------------------ObserverLogic-------------------------------
    fun addObserverToFetchRequestQueue(observer: FetchRequestQueueObserver) {
        serverManager.addObserverToFetchRequestQueue(observer)
    }
    fun unregisterObserverFromFetchRequestQueue(observer: FetchRequestQueueObserver) {
        serverManager.unregisterObserverFromFetchRequestQueue(observer)
    }

    fun getFetchRequestQueueLength(): Int {
        return serverManager.getFetchRequestQueueLength()
    }

    fun addObserverToProjectCommandQueue(observer: ProjectCommandQueueObserver) {
        serverManager.addObserverToProjectCommandQueue(observer)
    }
    fun unregisterObserverFromProjectCommandQueue(observer: ProjectCommandQueueObserver) {
        serverManager.unregisterObserverFromProjectCommandQueue(observer)
    }

    fun getProjectCommandQueueLength(): Int {
        return serverManager.getProjectCommandQueueLength()
    }
}