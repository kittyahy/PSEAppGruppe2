package com.pseandroid2.dailydataserver.onlineDatabase;

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
 * #TODO JavaDoc
 */
@Entity
@Table(name="FetchRequest_Table")
public class FetchRequest {

    private @Id @GeneratedValue int id;
    private String user;
    private long project;
    private String requestInfo;

    public FetchRequest(String user, long project, String requestInfo) {
        this.user = user;
        this.project = project;
        this.requestInfo = requestInfo;
    }

    public FetchRequest() {
    }

    public int getId() {
        return id;
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

    public String getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
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
}
