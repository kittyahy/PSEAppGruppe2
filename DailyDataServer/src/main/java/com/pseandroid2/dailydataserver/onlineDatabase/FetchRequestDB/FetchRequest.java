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
package com.pseandroid2.dailydataserver.onlineDatabase.FetchRequestDB;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;


/**
 * FetchRequest ist an Entity, which contains the request of an user for old Deltas.
 * <p>
 * With a FetchRequest another participant of the same project know which old Deltas going to be needed by the user.
 * So they could provide those Deltas.
 */
@Getter
@Setter
@Entity
@Table(name = "FetchRequest_Table")
public class FetchRequest {

    private @Id
    @GeneratedValue
    int id;
    private String user;
    private long project;
    private String requestInfo;

    /**
     * The constructor, to create a new FetchRequest.
     *
     * @param user        the user, who needs the old Deltas.
     * @param project     the project, in which the user needs those Deltas.
     * @param requestInfo the information for another participant, which specifies what Deltas they need.
     */
    public FetchRequest(String user, long project, String requestInfo) {
        this.user = user;
        this.project = project;
        this.requestInfo = requestInfo;
    }

    /**
     * Recommended empty constructor
     */
    public FetchRequest() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FetchRequest that = (FetchRequest) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "FetchRequest{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", project=" + project +
                ", requestInfo='" + requestInfo + '\'' +
                '}';
    }
}
