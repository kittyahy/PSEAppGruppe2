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

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The primary key for a Delta
 * <p>
 * Defines, how the primary key of Deltas needs to be handled.
 */
@Getter
@Setter
public class DeltaID implements Serializable {
    private LocalDateTime addedToServer;
    private String user;

    /**
     * Recommended empty Constructor
     */
    public DeltaID() {
    }

    /**
     * The constructor, to create a new valid Delta Id.
     *
     * @param addedToServer the time a Delta was initially added to the server.
     * @param user          the user, which added the Delta.
     */
    public DeltaID(LocalDateTime addedToServer, String user) {
        this.addedToServer = addedToServer;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;
        DeltaID deltaID = (DeltaID) o;
        return addedToServer.equals(deltaID.addedToServer) && user.equals(deltaID.user);

    }

    @Override
    public int hashCode() {
        return Objects.hash(addedToServer, user);
    }

    @Override
    public String toString() {
        return "DeltaID{" +
                "addedToServer=" + addedToServer +
                ", user='" + user + '\'' +
                '}';
    }
}
