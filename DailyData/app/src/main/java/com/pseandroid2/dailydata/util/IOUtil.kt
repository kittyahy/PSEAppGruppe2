package com.pseandroid2.dailydata.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import com.pseandroid2.dailydata.DailyDataApp
import com.pseandroid2.dailydata.ui.project.data.input.Table
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class IOUtil {

    companion object {
        const val DEFAULT_WIDTH = 1024
        const val DEFAULT_HEIGHT = 1024

        fun getSDRelativePath(folder: String, context: Context): String {
            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), folder)
            return file.absolutePath.substring(
                Environment.getExternalStorageDirectory().absolutePath.length,
                file.absolutePath.length
            )
        }

        fun saveToFile(
            view: View,
            folder: String,
            fileName: String,
            context: Context,
            width: Int = DEFAULT_WIDTH,
            height: Int = DEFAULT_HEIGHT
        ) {
            //Get File path
            val dir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), folder)
            if (!dir.exists() && !dir.mkdirs()) {
                Log.e(Consts.LOG_TAG, "Folder could not be created")
                return
            }
            val file = File(dir, "$fileName.png")

            //Setup the View
            view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            view.measure(
                View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
            )
            view.layout(0, 0, view.measuredWidth, view.measuredHeight)

            //Setup Canvas and Bitmap
            val canvas = Canvas()
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)

            canvas.setBitmap(bitmap)
            view.draw(canvas)

            //Save the Bitmap to the File
            try {
                val os: FileOutputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, os)
                os.flush()
                os.close()
            } catch (e: IOException) {
                Log.e(
                    Consts.LOG_TAG,
                    (e.message
                        ?: "An Error occurred during saving operations to file ${file.canonicalPath}")
                )
            }
        }

        fun getGraphImage(name: String, context: Context): Bitmap? {
            TODO()
        }
    }
}