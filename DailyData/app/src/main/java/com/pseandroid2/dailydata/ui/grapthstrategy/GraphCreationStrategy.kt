package com.pseandroid2.dailydata.ui.grapthstrategy

import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.settings.Settings
import com.pseandroid2.dailydata.model.transformation.TransformationFunction

abstract class GraphCreationStrategy {
    abstract fun createTransformation(xAxis: Int?): TransformationFunction<*>

    abstract fun createBaseSettings(name: String?): Settings

    abstract fun createGraph(
        transformation: Project.DataTransformation<out Any>,
        settings: Settings
    ): Graph<*, *>
}