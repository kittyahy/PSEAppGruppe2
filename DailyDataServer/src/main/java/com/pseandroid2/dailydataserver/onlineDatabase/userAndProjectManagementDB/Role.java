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


/**
 * An Enum which declares roles for project Participants.
 * Different roles can have different abilities and permissions for the project and user management as well as for
 * changing the table on the clients' device.
 */
public enum Role {

    /**
     * The standard role for project Participants.
     */
    PARTICIPANT,
    /**
     * there is only one admin per time for an project. They have more abilties and permissions for the user and
     * project management and the table.
     */
    ADMIN
}
