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
package com.pseandroid2.dailydataserver.onlineDatabase.requestParameters.deltaController;

import com.pseandroid2.dailydataserver.Communication.RequestParameter;

/**
 * #TODO Testen
 */

/**
 * The parameters for {@link com.pseandroid2.dailydataserver.onlineDatabase.DeltaController#saveDelta(long, String, SaveDeltaParameter) saveDelta()}
 */
public class SaveDeltaParameter extends RequestParameter {
    private String command;


    /**
     * The parameters for saveDeltas. Constructor.
     *
     * @param token   the token to verify the user (provided by the user)
     * @param command the command which should be saved (provided by the user)
     */
    public SaveDeltaParameter(String token, String command) {
        super(token);
        this.command = command;
    }

    /**
     * Getter and Setters
     */

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "SaveDeltaParameter{" +
                "command='" + command + '\'' +
                "} " + super.toString();
    }


}
