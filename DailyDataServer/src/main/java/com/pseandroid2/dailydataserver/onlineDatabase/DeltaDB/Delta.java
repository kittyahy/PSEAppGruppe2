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
package com.pseandroid2.dailydataserver.onlineDatabase.DeltaDB;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;


/**
 * #TODO JavaDoc
 */
@Entity
@Table(name = "Delta_Table")

@IdClass(DeltaID.class)
public class Delta {
    private @Id
    LocalDateTime addedToServer;
    private @Id
    String user;
    private String projectCommand;
    private long project;
    private boolean isAdmin;
    private String requestedBy;


    /**
     * new Delta
     */
    public Delta(String user,String projectCommand, long project, boolean isAdmin){
        addedToServer = LocalDateTime.now();
        this.user = user;
        this.projectCommand = projectCommand;
        this.project = project;
        this.isAdmin = isAdmin;
        requestedBy ="";
    }
    /**
     * Old data
     * @param addedToServer
     * @param user
     * @param projectCommand
     * @param project
     * @param isAdmin
     * @param requestedBy
     */
    public Delta(LocalDateTime addedToServer, String user, String projectCommand, long project, boolean isAdmin, String requestedBy) {
        this.addedToServer = addedToServer;
        this.user = user;
        this.projectCommand = projectCommand;
        this.project = project;
        this.isAdmin = isAdmin;
        this.requestedBy = requestedBy;
    }



    public Delta() {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delta delta = (Delta) o;
        return addedToServer.equals(delta.addedToServer) && user.equals(delta.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addedToServer, user);
    }

    @Override
    public String toString() {
        return "Delta{" +
                "addedToServer=" + addedToServer +
                ", user='" + user + '\'' +
                ", projectCommand='" + projectCommand + '\'' +
                ", project=" + project +
                ", isAdmin=" + isAdmin +
                ", requestedBy='" + requestedBy +
                '}';
    }
}
