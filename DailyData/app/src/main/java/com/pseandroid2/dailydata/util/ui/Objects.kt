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

package com.pseandroid2.dailydata.util.ui

import android.media.Image
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

data class TableColumn(val id : Int, val name: String, val unit : String, val dataType : DataType)
data class TableButton(val id : Int, val name: String, val column : TableColumn, val value : Int)
data class Notification(val message : String, val time : String)
data class Row(var elements : List<String>)
data class Data(var columns: List<String>, var rows: List<Row>)

data class GraphTemplate(val title : String, val image : Int)
data class ProjectTemplate(val title : String, val image : Int, val graphTemplates: List<GraphTemplate>)

enum class DataType(val representation : String) {
    WHOLE_NUMBER("Whole Number"), FLOATING_POINT_NUMBER("Floating Point Number"), TIME("Time"), STRING("String");

    companion object {
        fun fromString(rep : String) : DataType {
            for (enum in values()) {
                if (enum.representation == rep){
                    return enum
                }
            }
            return WHOLE_NUMBER
        }
    }

}

enum class Wallpapers(val value : Color) {
    ORANGE(Color(0xFFF57C00)),
    GREEN(Color(0xFF388E3C)),
    BLUE(Color(0xFF2196F3))
}

enum class Graphs(val representation: String) {
    LINE_CHART("Line chart"),
    PIE_CHART("Pie chart")
}

data class Post(
    val id: Int,
    val image: Image?,
    val description: String,
    val template: Template
)

class Template() {
}