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

package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Convertible
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@InternalCoroutinesApi
abstract class FlowAdapter<I, O>(private val flow: Flow<List<I>>) {
    private val sharedFlow = MutableSharedFlow<List<O>>()

    init {
        GlobalScope.launch {
            adapt()
        }
    }

    @JvmName("getFlow1")
    fun getFlow(): Flow<List<O>> {
        return sharedFlow
    }

    @InternalCoroutinesApi
    open suspend fun adapt() {
        flow.collect { list ->
            val listO = ArrayList<O>()
            for (i: I in list) {
                val o = provide(i)
                listO.add(o)
            }
            sharedFlow.emit(listO)
        }
    }

    abstract fun provide(i: I): O
}