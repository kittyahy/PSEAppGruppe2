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
import javax.persistence.IdClass;
import java.util.Objects;

/**
 * #TODO JavaDoc
 */
@Getter
@Setter
@Entity
@IdClass(ProjectParticipantsID.class)
public class ProjectParticipants {
    private @Id
    String user;
    private @Id
    long project;
    private Role role;
    private int numberOfJoin;

    public ProjectParticipants(String user, long project, Role role, int numberOfJoin) {
        this.user = user;
        this.project = project;
        this.role = role;
        this.numberOfJoin = numberOfJoin;
    }

    public ProjectParticipants() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectParticipants that = (ProjectParticipants) o;
        return project == that.project && user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, project);
    }

    @Override
    public String toString() {
        return "ProjectParticipants{" +
                "user='" + user + '\'' +
                ", project=" + project +
                ", role=" + role +
                ", numberOfJoin=" + numberOfJoin +
                '}';
    }

}
