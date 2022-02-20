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

package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

interface ContainsOperations {
    val isIllegalOperation: Map<String, Flow<Boolean>>
        @Suppress("DEPRECATION")
        get() = mutableIllegalOperation

    @Deprecated("Internal function, should not be used outside the RepositoryViewModelAPI")
    val mutableIllegalOperation: MutableMap<String, MutableSharedFlow<Boolean>>

    fun addFlows(vararg containsOperations: ContainsOperations) {
        for (entry in containsOperations) {
            @Suppress("DEPRECATION")
            mutableIllegalOperation.putAll(entry.mutableIllegalOperation)
        }
    }
}