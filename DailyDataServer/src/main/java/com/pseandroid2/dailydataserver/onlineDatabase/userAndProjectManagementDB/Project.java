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
package com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Project is an Entity, which contains basic information for a project.
 * <p>
 * The Project also contains usefully parameters for handling the project, like the participantJoin. It describes who
 * many user already joined the project, plus one.
 */
@Getter
@Setter
@Entity
@Table(name = "Project_Table")
public class Project {
    private @Id
    long projectId;
    private LocalDateTime lastUpdated;
    private int participantJoin;
    private String projectInfo;


    /**
     * the constructor for a project, which should be used, if a new project get instanced.
     *
     * @param projectId   the id for the new Project.
     * @param projectInfo Information for a new Participant, to initialize the project on their device.
     */
    public Project(long projectId, String projectInfo) {
        this.projectId = projectId;
        this.lastUpdated = LocalDateTime.now();
        this.participantJoin = 1;
        this.projectInfo = projectInfo;
    }

    /**
     * the recommended empty constructor
     */
    public Project() {
    }


    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", lastUpdated=" + lastUpdated +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return projectId == project.projectId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId);
    }
}
