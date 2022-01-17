package com.pseandroid2.dailydataserver.onlineproject;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;


/**
 * #TODO JavaDoc
 */
@Entity
@Table(name= "Delta_Table")
@IdClass(DeltaID.class)
public class Delta {
    private @Id
    LocalDateTime addedToServer; //mit idClass
    private @Id String user;
    private String projectCommand;
    private long project;
    private String requestedBy;
    private boolean isAdmin;

    @OneToMany
    private Set<ProjectParticipants> downloadedBy;

    public Delta(LocalDateTime addedToServer, String user, String projectCommand, long project) {
        this.addedToServer = addedToServer;
        this.user = user;
        this.projectCommand = projectCommand;
        this.project = project;
    }

    public Delta() {

    }

    public Delta(LocalDateTime addedToServer, String user, String projectCommand, long project, String requestedBy, boolean isAdmin) {
        this.addedToServer = addedToServer;
        this.user = user;
        this.projectCommand = projectCommand;
        this.project = project;
        this.requestedBy = requestedBy;
        this.isAdmin = isAdmin;
    }

    public Set<ProjectParticipants> getDownloadedBy() {
        return downloadedBy;
    }

    public void setDownloadedBy(Set<ProjectParticipants> downloadedBy) {
        this.downloadedBy = downloadedBy;
    }


    public LocalDateTime getAddedToServer() {
        return addedToServer;
    }

    public void setAddedToServer(LocalDateTime addedToServer) {
        this.addedToServer = addedToServer;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProjectCommand() {
        return projectCommand;
    }

    public void setProjectCommand(String projectCommand) {
        this.projectCommand = projectCommand;
    }

    public long getProject() {
        return project;
    }

    public void setProject(long project) {
        this.project = project;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
