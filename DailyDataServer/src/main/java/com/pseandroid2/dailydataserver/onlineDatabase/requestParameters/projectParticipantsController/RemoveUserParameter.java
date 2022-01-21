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

import com.pseandroid2.dailydataserver.RequestParameter;

/**
 * #TODO Testen, JavaDoc
 */
public class RemoveUserParameter extends RequestParameter {
    private String userToRemove;

    /**
     *
     * @param token
     * @param userToRemove the user, which should be removed.
     */
    public RemoveUserParameter(String token, String userToRemove) {
        super(token);
        this.userToRemove = userToRemove;
    }

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
                "} " + super.toString();
    }
}
