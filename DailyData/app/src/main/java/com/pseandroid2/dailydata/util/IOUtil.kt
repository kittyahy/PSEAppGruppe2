package com.pseandroid2.dailydata.util

import android.content.Context
import android.os.Environment
import java.io.File

class IOUtil {

    companion object {
        fun getSDRelativePath(folder: String, context: Context): String {
            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), folder)
            return file.absolutePath.substring(
                Environment.getExternalStorageDirectory().absolutePath.length,
                file.absolutePath.length
            )
        }
    }

}