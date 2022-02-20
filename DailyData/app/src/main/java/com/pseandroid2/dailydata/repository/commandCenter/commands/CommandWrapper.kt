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

package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.google.gson.Gson

class CommandWrapper(command: ProjectCommand) {

    private val commandCanonicalName: String = canonicalNameFrom(command)
    private val commandJson: String = jsonFrom(command)


    companion object {
        @JvmStatic
        private fun canonicalNameFrom(command: ProjectCommand): String {
            return command.javaClass.canonicalName!!
        }

        @JvmStatic
        private fun jsonFrom(command: ProjectCommand): String {
            return Gson().toJson(command)
        }

        @JvmStatic
        fun fromJson(commandWrapperJsonString: String): ProjectCommand {
            val wrapper: CommandWrapper =
                Gson().fromJson(commandWrapperJsonString, CommandWrapper::class.java)
            return wrapper.unwrap()
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }

    private fun unwrap(): ProjectCommand {
        //Cast cannot fail, because constructor ensures that commandCanonicalName is the canonical name of a child of ProjectCommand
        @Suppress("Unchecked_Cast")
        val type: Class<out ProjectCommand> =
            Class.forName(commandCanonicalName) as Class<out ProjectCommand>
        return Gson().fromJson(commandJson, type)
    }

}