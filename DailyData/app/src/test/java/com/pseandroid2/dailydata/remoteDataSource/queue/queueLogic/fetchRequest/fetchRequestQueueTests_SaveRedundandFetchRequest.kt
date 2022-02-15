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

package com.pseandroid2.dailydata.remoteDataSource.queue.queueLogic.fetchRequest

import com.pseandroid2.dailydata.remoteDataSource.queue.FetchRequestQueue
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.FetchRequest
import org.junit.Assert
import org.junit.Test

class fetchRequestQueueTests_SaveRedundandFetchRequest {
    @Test
    fun saveOneFetchRequest() {
        val fetchRequestQueue = FetchRequestQueue()

        var fetchRequest1 = FetchRequest(requestInfo = "Fetch Request: 1")
        var fetchRequest2 = FetchRequest(requestInfo = "Fetch Request: 2")

        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 0)

        fetchRequestQueue.addFetchRequest(fetchRequest1)
        fetchRequestQueue.addFetchRequest(fetchRequest2)
        fetchRequestQueue.addFetchRequest(fetchRequest1)
        fetchRequestQueue.addFetchRequest(fetchRequest2)

        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 2)

        val returnedFetchRequest = fetchRequestQueue.getFetchRequest()
        Assert.assertEquals(fetchRequest1, returnedFetchRequest)

        val returnedFetchRequest2 = fetchRequestQueue.getFetchRequest()
        Assert.assertEquals(fetchRequest2, returnedFetchRequest2)

        Assert.assertEquals(fetchRequestQueue.getQueueLength(), 0)
    }
}