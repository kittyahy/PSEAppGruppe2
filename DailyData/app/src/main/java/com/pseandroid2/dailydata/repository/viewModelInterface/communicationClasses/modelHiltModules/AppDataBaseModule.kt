package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.modelHiltModules

import com.pseandroid2.dailydata.DailyDataApp
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.database.daos.GraphDAO
import com.pseandroid2.dailydata.model.database.daos.NotificationsDAO
import com.pseandroid2.dailydata.model.database.daos.ProjectDataDAO
import com.pseandroid2.dailydata.model.database.daos.TableContentDAO
import com.pseandroid2.dailydata.model.database.daos.UIElementDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

//@Module
//@InstallIn(DailyDataApp::class)
object AppDataBaseModule {
/*    private val app: DailyDataApp
        get() {
            TODO()
        }
    private val appDataBase = AppDataBase.getInstance(app.applicationContext)

    @Provides
    fun provideAppDataBase() : AppDataBase{
        return appDataBase
    }
    @Provides
    fun provideProjectDataDAO(): ProjectDataDAO {
        return  appDataBase.projectDataDAO()
    }
    @Provides
    fun provideGraphDAO(): GraphDAO {
        return  appDataBase.graphDAO()
    }
    @Provides
    fun provideNotificationsDAO(): NotificationsDAO {
        return  appDataBase.notificationsDAO()
    }
    @Provides
    fun provideTableContentDAO(): TableContentDAO {
        return  appDataBase.tableContentDAO()
    }
    @Provides
    fun provideUiElementDAO(): UIElementDAO {
        return  appDataBase.uiElementDAO()
    }*/
}