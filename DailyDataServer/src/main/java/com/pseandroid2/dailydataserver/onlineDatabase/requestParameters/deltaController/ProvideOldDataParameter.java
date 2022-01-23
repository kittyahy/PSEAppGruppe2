package com.pseandroid2.dailydataserver.onlineDatabase.requestParameters.deltaController;
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
import com.pseandroid2.dailydataserver.Communication.requestBodyLogic.RequestParameter;

import java.sql.Date;

/**
 * #TODO Testen
 */

/**
 * The parameters for {@link com.pseandroid2.dailydataserver.onlineDatabase.DeltaController#provideOldData(long,String,ProvideOldDataParameter) provideOldData}
 */
public class ProvideOldDataParameter extends RequestParameter {
    private String command;
    private String forUser;
    private Date initialAdded;
    private String initialAddedBy;
    private boolean wasAdmin;

    /**
     * @param command        the command for the delta (provided by the client)
     * @param forUser        which User needs the delta, Uid, (provided by the client)
     * @param initialAdded   when was the Delta initially added (provided by the client)
     * @param initialAddedBy who added the Delta initially (provided by the client)
     * @param wasAdmin       was the person at this time admin (provided by the client)
     */
    public ProvideOldDataParameter(String token, String command, String forUser, Date initialAdded, String initialAddedBy, boolean wasAdmin) {
        super(token);
        this.command =command;
        this.forUser = forUser;
        this.initialAdded = initialAdded;
        this.initialAddedBy = initialAddedBy;
        this.wasAdmin = wasAdmin;
    }

    /**
     * getters and setters
     */

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getForUser() {
        return forUser;
    }

    public void setForUser(String forUser) {
        this.forUser = forUser;
    }

    public Date getInitialAdded() {
        return initialAdded;
    }

    public void setInitialAdded(Date initialAdded) {
        this.initialAdded = initialAdded;
    }

    public String getInitialAddedBy() {
        return initialAddedBy;
    }

    public void setInitialAddedBy(String initialAddedBy) {
        this.initialAddedBy = initialAddedBy;
    }

    public boolean isWasAdmin() {
        return wasAdmin;
    }

    public void setWasAdmin(boolean wasAdmin) {
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
                "} " + super.toString();
    }
}
