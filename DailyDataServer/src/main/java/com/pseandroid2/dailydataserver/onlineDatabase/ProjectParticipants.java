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
package com.pseandroid2.dailydataserver.onlineDatabase;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Objects;

/**
 * #TODO JavaDoc
 */
@Entity
@Table(name="ProjectParticipants_Table")
@IdClass(ProjectParticipantsID.class)
public class ProjectParticipants {
    private @Id String user;
    private @Id long project;
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


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getProject() {
        return project;
    }

    public void setProject(long project) {
        this.project = project;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getNumberOfJoin() {
        return numberOfJoin;
    }

    public void setNumberOfJoin(int numberOfJoin) {
        this.numberOfJoin = numberOfJoin;
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
}
