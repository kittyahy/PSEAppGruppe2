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
package com.pseandroid2.dailydataserver.onlineDatabase.requestParameters.projectParticipantsController;

/**
 * #TODO Testen
 */

/**
 */
public class RemoveUserParameter  {
    private String userToRemove;

    /**
     * The parameters for demandOldData. Constructor.
     *
     * @param userToRemove the user, which should be removed.
     */
    public RemoveUserParameter( String userToRemove) {
        this.userToRemove = userToRemove;
    }

    /**
     * getters and setters
     */
    public String getUserToRemove() {
        return userToRemove;
    }

    public void setUserToRemove(String userToRemove) {
        this.userToRemove = userToRemove;
    }

    @Override
    public String toString() {
        return "RemoveUserParameter{" +
                "userToRemove='" + userToRemove + '\'' +
                "} ";
    }
}
