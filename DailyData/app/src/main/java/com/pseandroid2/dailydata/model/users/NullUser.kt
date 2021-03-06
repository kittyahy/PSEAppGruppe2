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

package com.pseandroid2.dailydata.model.users

import java.lang.NullPointerException

/**
 * This class defines the behave, if a user could be null and what happens, if the methods get called.
 */
class NullUser : User {
    companion object {
        const val NPE_STRING =
            "This User has not been set (perhaps you tried to save a Project to the " +
                    "database without setting an acutal User as Admin?)"
    }

    override fun getId(): String {
        throw NullPointerException(NPE_STRING)
    }

    override fun getName(): String {
        throw NullPointerException(NPE_STRING)
    }
}