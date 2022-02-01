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

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;


/**
 * Delta is an entity, which stores a project Command and data, which belongs to it.
 * <p>
 * The project Command contains the changes, which is going to be executed from a participant.
 * If a project Participant gets a Delta, they don't need the information "requested By", "project" and who already
 * has downloaded it.
 * The primary key are split in addedTOServer and user, because one user is not able to create two Deltas at exactly
 * one time.
 * The most of the time, one need only one of those attributes to find do something which the Deltas, therefore, the
 * id is created as ID Class.
 */
@Getter
@Setter
@Entity
@Table(name = "Delta_Table")

@IdClass(value = DeltaID.class)
public class Delta {
    @Id
    private
    LocalDateTime addedToServer;
    @Id
    private
    String user;
    private String projectCommand;
    private long project;
    private boolean isAdmin;
    private String requestedBy;


    /**
     * Constructor for  New Delta.
     * <p>
     * This constructor should be used to create a new Delta, which is not requested by anybody.
     * (If a participant (the user) makes a change on this table, they can share it which the other participants.
     *
     * @param user           the user, who wants to share his changes.
     * @param projectCommand the command, what user had changed.
     * @param project        the project, to which belongs this Delta.
     * @param isAdmin        declares, if the user war admin at the time, when the Delta was created. (So other users
     *                       can verfiy, that user make the change)
     */
    public Delta(String user, String projectCommand, long project, boolean isAdmin) {
        this.addedToServer = LocalDateTime.now();
        this.user = user;
        this.projectCommand = projectCommand;
        this.project = project;
        this.isAdmin = isAdmin;
        this.requestedBy = "";
    }

    /**
     * Constructor for Old Deltas.
     * <p>
     * This constructor should be used, when a user reuploads an old Delta for another participant. They can recreate
     * all old details with it.
     *
     * @param addedToServer  the time, when the Delta initially was added to the server. (so the user can recreate,
     *                       when the change was made)
     * @param user           the user, who initially made the change.
     * @param projectCommand the command, what had been changed.
     * @param project        the project, which the Delta belongs to.
     * @param isAdmin        declares, if the user was admin to the time, they made the change. (if a user made a
     *                       change, for which they had to be admin, and to the time, the Delta was reuploaded they
     *                       aren't admin anymore)
     * @param requestedBy    The participant, who needs this Delta
     */
    public Delta(LocalDateTime addedToServer, String user, String projectCommand, long project, boolean isAdmin,
                 String requestedBy) {
        this.addedToServer = addedToServer;
        this.user = user;
        this.projectCommand = projectCommand;
        this.project = project;
        this.isAdmin = isAdmin;
        this.requestedBy = requestedBy;
    }


    /**
     * Needed empty Constructor
     */
    public Delta() {
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
