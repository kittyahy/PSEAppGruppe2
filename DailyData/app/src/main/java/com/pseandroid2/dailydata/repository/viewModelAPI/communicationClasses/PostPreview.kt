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

import android.graphics.Bitmap
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.forRepoReturns.PostPreviewWithPicture
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import kotlinx.coroutines.flow.Flow

class PostPreview(
    var title: String,
    var image: Bitmap,
    override var id: Int,
    var remoteDataSourceAPI: RemoteDataSourceAPI
) : Identifiable {
    constructor(
        postPreviewWithPicture: PostPreviewWithPicture,
        remoteDataSourceAPI: RemoteDataSourceAPI
    ) : this(
        postPreviewWithPicture.preview,
        postPreviewWithPicture.previewPicture,
        postPreviewWithPicture.id,
        remoteDataSourceAPI
    )

    override lateinit var executeQueue: ExecuteQueue
    lateinit var viewModelProject: ViewModelProject

    suspend fun getPostDetail(): Collection<PostEntry> {
        val postDetail = ArrayList<PostEntry>()
        val serverList = remoteDataSourceAPI.getPostDetail(id)
        for (serverDetail in serverList) {
            postDetail.add(PostEntry(serverDetail))
        }
        return postDetail
    }

    override fun deleteIsPossible(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun delete() {
        TODO("Not yet implemented")
    }
}