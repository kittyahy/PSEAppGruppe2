package com.pseandroid2.dailydata.repository.commandCenter.commands

class CommandWrapper(
    val commandCanonicalName: String,
    val commandJson: String
) {
    constructor(command: ProjectCommand) : this(canonicalNameFrom(command), jsonFrom(command))

    companion object {
        @JvmStatic
        private fun canonicalNameFrom(command: ProjectCommand): String {
            return TODO()
        }

        @JvmStatic
        private fun jsonFrom(command: ProjectCommand): String {
            return TODO()
        }
    }

    fun toJson(): String {
        return TODO()
    }

    fun unwrap(): ProjectCommand {
        return TODO()
    }

}