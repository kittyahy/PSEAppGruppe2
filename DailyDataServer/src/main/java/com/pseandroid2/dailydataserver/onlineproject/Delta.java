package com.pseandroid2.dailydataserver.onlineproject;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.sql.Date;


/**
 *  downloadedBy funktioniert so nicht.
 */
@Entity
@Table(name= "Delta_Table")
@IdClass(DeltaID.class)
public class Delta {
    private @Id Date addedToServer; //mit idClass
    private @Id String user;
    private String projectCommand;
    private long project;
    private String requestedBy;
    private boolean isAdmin;

    public Date getAddedToServer() {
        return addedToServer;
    }

    public void setAddedToServer(Date addedToServer) {
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
