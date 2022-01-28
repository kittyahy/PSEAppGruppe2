package com.pseandroid2.dailydata.util

import android.content.Context
import android.os.Environment
import java.io.File

class IOUtil {

    companion object {
        fun getSDRelativePath(context: Context): String {
            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Graphs")
            return Environment.getExternalStorageDirectory().absolutePath //TODO
        }
    }

}