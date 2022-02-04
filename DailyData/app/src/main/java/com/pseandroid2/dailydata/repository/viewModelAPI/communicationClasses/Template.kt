package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.forRepoReturns.TemplateDetailWithPicture

/**
 * Template to create a Project or part thereof from.
 */
abstract class Template {
    companion object {
        /**
         * Converter fun to instantiate from RDS class.
         */
        fun from(templateDetailWithPicture: TemplateDetailWithPicture): Template {
            if (templateDetailWithPicture.projectTemplate) {
                ProjectTemplate(templateDetailWithPicture, graphTemplates                )
            }
        }
    }

}