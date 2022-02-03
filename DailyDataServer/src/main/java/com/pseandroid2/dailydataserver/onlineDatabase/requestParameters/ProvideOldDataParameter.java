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
package com.pseandroid2.dailydataserver.onlineDatabase.requestParameters;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


/**
 * A class which specifies all Parameter, which are recommended for the body of provideOldDelta.
 */
@Getter
@Setter
public class ProvideOldDataParameter {
    private String command;
    private String forUser;
    private LocalDateTime initialAdded;
    private String initialAddedBy;
    private boolean wasAdmin;

    /**
     * The constructor to create  parameters for the method provideOldDelta.
     *
     * @param command        the command what the user, who initially  added the Delta, changed.
     * @param forUser        the user, which needs the old Deltas.
     * @param initialAdded   when was the Delta initially added to the server.
     * @param initialAddedBy the user, who added the Delta initially.
     * @param wasAdmin       was the person at this time admin, when they added the Delta.
     */
    public ProvideOldDataParameter(String command, String forUser, LocalDateTime initialAdded, String initialAddedBy,
                                   boolean wasAdmin) {
        this.command = command;
        this.forUser = forUser;
        this.initialAdded = initialAdded;
        this.initialAddedBy = initialAddedBy;
        this.wasAdmin = wasAdmin;
    }


    @Override
    public String toString() {
        return "ProvideOldDataParameter{" +
                "command='" + command + '\'' +
                ", forUser='" + forUser + '\'' +
                ", initialAdded=" + initialAdded +
                ", initialAddedBy='" + initialAddedBy + '\'' +
                ", wasAdmin=" + wasAdmin +
                "} ";
    }
}
