package com.pseandroid2.dailydata.util.ui

data class TableColumn(val id : Int, val name: String, val unit : String, val dataType : DataType)
data class TableButton(val id : Int, val name: String, val column : TableColumn, val value : Int)

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