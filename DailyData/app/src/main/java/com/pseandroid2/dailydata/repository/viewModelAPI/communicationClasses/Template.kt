package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.forRepoReturns.TemplateDetailWithPicture

abstract class Template {
    companion object {
        fun from(templateDetailWithPicture: TemplateDetailWithPicture): Template {
            if (templateDetailWithPicture.projectTemplate) {
                ProjectTemplate(templateDetailWithPicture, graphTemplates                )
            }
        }
    }

}