package com.pseandroid2.dailydata.remoteDataSource

import com.pseandroid2.dailydata.remoteDataSource.Queues.FetchRequestQueueObserver
import com.pseandroid2.dailydata.remoteDataSource.Queues.ProjectCommandQueueObserver

class RemoteDataSourceAPI {
// -----------------------------FireBase-------------------------------
    // -----------------------------UserAccount-------------------------------

    fun registerUser(eMail: String, passwort: String) : Int { // TODO: ersetze Rückgabetypen durch einen Enum
        // TODO: Implement Method

        return -1;
    }

    fun signInUser(eMail: String, passwort: String) : Int { // TODO: ersetze Rückgabetypen durch einen Enum
        // TODO: Implement Method

        return -1;
    }

    fun signOut() : Int { // TODO: ersetze Rückgabetypen durch einen Enum
        // TODO: Implement Method

        return -1;
    }

    // -----------------------------UserDetails-------------------------------
    fun getUserID(): String {
        // TODO: Implement Method

        return "";
    }

    fun getUserName(): String {
        // TODO: Implement Method

        return "";
    }

    fun getUserEMail(): String {
        // TODO: Implement Method

        return "";
    }

    fun getUserPhotoUrl(): String {
        // TODO: Implement Method

        return "";
    }

    // -----------------------------Authentification-------------------------------
    fun getToken(): String {
        // TODO: Implement Method

        return "";
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
    fun provideOldData(projectCommand: String, forUser: String, initialAddedData: Int, initialAddedBy: String, projectID: Long, wasAdmin: Boolean) {
        // TODO: Implement Method

        return;
    }

    fun getRemoveTime(): Int { // TODO: Eigentlich ist der ausgabetyp "Date" -> kläre ob Int ok
        // TODO: Implement Method

        return -1;
    }

    // -----------------------------FetchController-------------------------------
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
    fun addObserverToFetchRequestQueue(observer: FetchRequestQueueObserver, projectID: Long) {
        // TODO: Implement Method

        return;
    }
    fun unregisterObserverFromFetchRequestQueue(observer: FetchRequestQueueObserver, projectID: Long) {
        // TODO: Implement Method

        return;
    }

    fun addObserverToProjectCommandQueue(observer: ProjectCommandQueueObserver, projectID: Long) {
        // TODO: Implement Method

        return;
    }
    fun unregisterObserverFromProjectCommandQueue(observer: ProjectCommandQueueObserver, projectID: Long) {
        // TODO: Implement Method

        return;
    }
}