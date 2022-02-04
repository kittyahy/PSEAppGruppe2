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
        val type: Class<out ProjectCommand> =
            Class.forName(commandCanonicalName) as Class<out ProjectCommand>
        return Gson().fromJson(commandJson, type)
    }

}