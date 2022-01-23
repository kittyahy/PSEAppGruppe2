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

import java.io.Serializable;
import java.util.Objects;

/**
 * #TODO JavaDoc
 */

public class ProjectParticipantsID implements Serializable {
    private String user;
    private long project;


    public ProjectParticipantsID(String user, long project) {
        this.user = user;
        this.project = project;
    }

    public ProjectParticipantsID() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        ProjectParticipantsID proPaId = (ProjectParticipantsID) o;
        return user.equals(proPaId.user) && proPaId.project == project;
    }

    @Override
    public int hashCode() {
        return Objects.hash(project, user);
    }
}
