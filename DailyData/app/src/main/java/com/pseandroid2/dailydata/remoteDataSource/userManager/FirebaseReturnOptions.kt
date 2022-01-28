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

package com.pseandroid2.dailydata.remoteDataSource.userManager

/**
 * All return options that firebase calls can return which will be past to upper classes
 *
 * @return true: The operation succeeded as planned - No error happened
 *         false: something went wrong
 */
enum class FirebaseReturnOptions (val success: Boolean){
    REGISTERED(true),
    REGISTRATION_FAILED(false),
    SINGED_IN(true),
    SIGN_IN_FAILED(false),
    SINGED_OUT(true),
    WRONG_INPUT_PARAMETERS(false),

    TIMEOUT(false) // Firebase took to long to process the request
}