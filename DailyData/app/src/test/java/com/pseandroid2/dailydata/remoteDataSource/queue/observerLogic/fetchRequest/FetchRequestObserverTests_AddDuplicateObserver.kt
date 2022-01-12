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

package com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.fetchRequest

import com.pseandroid2.dailydata.remoteDataSource.queue.FetchRequestQueue
import com.pseandroid2.dailydata.remoteDataSource.queue.observerLogic.UpdatedByObserver_ForTesting
import org.junit.Assert
import org.junit.Test

class FetchRequestObserverTests_AddDuplicateObserver {
    @Test
    fun addDuplicateObserver() {
        // Erstelle Observer
        var toUpdate = UpdatedByObserver_ForTesting()
        var observer = FetchRequestQueueObserver_ForTesting(toUpdate)

        // Füge Observer hinzu
        var fetchRequestQueue = FetchRequestQueue()
        // Add Observer twice
        fetchRequestQueue.registerObserver(observer)
        fetchRequestQueue.registerObserver(observer)

        // Prüfe ob update() bei Observer ausgeführt wird
        Assert.assertEquals(toUpdate.isUpdated(), false)
        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 0)
        var fetchRequest = "Fetch Request: 1"
        fetchRequestQueue.addFetchRequest(fetchRequest)
        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 1)
        Assert.assertEquals(toUpdate.getUpdated(), 1)


        // Dies sollte nun den Controller entfernen: -> Update sollte nicht nochmal ausgeführt werden
        fetchRequestQueue.unregisterObserver(observer)
        var fetchRequest2 = "Fetch Request: 2"
        fetchRequestQueue.addFetchRequest(fetchRequest2)
        Assert.assertEquals(1, toUpdate.getUpdated())
    }
}