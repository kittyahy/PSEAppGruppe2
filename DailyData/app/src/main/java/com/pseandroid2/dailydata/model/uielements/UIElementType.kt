package com.pseandroid2.dailydata.model

enum class UIElementType {

    BUTTON {
        override fun toString(): String {
            return BUTTON_STRING
        }
    },
    NUMBER_FIELD {
        override fun toString(): String {
            return NUMBER_FIELD_STRING
        }
    },
    DATE_TIME_PICKER {
        override fun toString(): String {
            return DATE_TIME_PICKER_STRING
        }
    };

    companion object {
        const val BUTTON_STRING = "BUTTON"
        const val NUMBER_FIELD_STRING = "NUMBER_FIELD"
        const val DATE_TIME_PICKER_STRING = "DATE_TIME_PICKER"
        fun fromString(str: String): UIElementType {
            return when (str) {
                BUTTON_STRING -> BUTTON
                NUMBER_FIELD_STRING -> NUMBER_FIELD
                DATE_TIME_PICKER_STRING -> DATE_TIME_PICKER
                else -> {
                    throw IllegalArgumentException()
                }
            }
        }
    }
}