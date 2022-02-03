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

package com.pseandroid2.dailydata.di

import android.app.Application
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.UserAccount
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application) : AppDataBase {
        return AppDataBase.getInstance(app)
    }
    @Provides
    @Singleton
    fun provideRemoteDataSourceAPI(): RemoteDataSourceAPI {
        return RemoteDataSourceAPI(
            UserAccount(FirebaseManager(null)),
            ServerManager(RESTAPI()))
    }
    @Provides
    @Singleton
    fun provideRepositoryViewModelAPI(database : AppDataBase, remoteDataBase : RemoteDataSourceAPI): RepositoryViewModelAPI {
        return RepositoryViewModelAPI(database, remoteDataBase) //hab ich geändert - robin
    }
}