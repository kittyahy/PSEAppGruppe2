package com.pseandroid2.dailydata


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Environment
import android.util.Log
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.GrantPermissionRule
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File

class MPChartsTester {
    private lateinit var lineChart: LineChart
    private lateinit var pieChart: PieChart
    private lateinit var context: Context
    private lateinit var values: List<Entry>
    private lateinit var pieValues: List<PieEntry>

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val permissionsRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE)!!

    @ExperimentalCoroutinesApi
    @Before
    fun setup() = runTest {
        launch(Dispatchers.Main) {
            context = ApplicationProvider.getApplicationContext()
            lineChart = LineChart(context)
            pieChart = PieChart(context)
            values = listOf(Entry(100.0f, 100.0f), Entry(200.0f, 200.0f), Entry(300.0f, 300.0f))
            pieValues = listOf(PieEntry(30.0f), PieEntry(60.0f), PieEntry(90.0f))
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test() {
        runBlocking {
            launch(Dispatchers.Main) {
                val lineDataSet = LineDataSet(values, "Test")
                val lineData = LineData(lineDataSet)
                lineChart.data = lineData
                val desc = Description()
                desc.isEnabled = false
                lineChart.description = desc
                lineChart.xAxis.isEnabled = false
                val pieDataSet = PieDataSet(pieValues, "Test")
                pieDataSet.colors = listOf(
                    Color.parseColor("#43fe59"),
                    Color.parseColor("#fe3409"),
                    Color.parseColor("#4320df")
                )
                val pieData = PieData(pieDataSet)
                pieChart.data = pieData
                composeTestRule.setContent {
                    TestComp()
                }

                delay(10000)

                val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Test")
                if (!file.exists() && !file.mkdirs()) {
                    Log.e("XXX", "Directories don't exist and couldn't be created")
                    fail()
                }
                Log.d("XXX", "Path should be: ${file.absolutePath}")
                Log.d(
                    "XXX", "Path is: ${Environment.getExternalStorageDirectory().path}${
                        file.absolutePath.substring(
                            Environment.getExternalStorageDirectory().path.length,
                            file.absolutePath.length
                        )
                    }"
                )

                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    Log.e("XXX", "Permission denied")
                    fail()
                } else {
                    Log.d("XXX", "dir: ${file.absolutePath}")
                    Log.d("XXX", "width: ${lineChart.width}, height: ${lineChart.height}")
                    Log.d("XXX", "dir2: ${Environment.getExternalStorageDirectory().path}")
                    assertEquals(
                        file.absolutePath,
                        Environment.getExternalStorageDirectory().path + file.absolutePath.substring(
                            Environment.getExternalStorageDirectory().path.length,
                            file.absolutePath.length
                        )
                    )
                    val success = lineChart.saveToPath(
                        "TestLine",
                        file.absolutePath.substring(
                            Environment.getExternalStorageDirectory().path.length,
                            file.absolutePath.length
                        )
                    )
                    Log.d("XXX", "success: $success")
                    if (!success) {
                        fail()
                    }
                }
            }
        }
    }

    @Test
    fun testRead() {
        runBlocking {
            launch(Dispatchers.Main) {
                val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Test")
                val bitmap = BitmapFactory.decodeFile(file.absolutePath + "/TestLine.png")
                composeTestRule.setContent {
                    TestComp2(bitmap = bitmap)
                }
                delay(10000)
            }
        }
    }

    @Composable
    fun TestComp() {
        Column {
            AndroidView(
                factory = { lineChart }, modifier = Modifier
                    .width(300.dp)
                    .height(300.dp)
            )
            AndroidView(
                factory = { pieChart }, modifier = Modifier
                    .width(300.dp)
                    .height(300.dp)
            )
        }
    }

    @Composable
    fun TestComp2(bitmap: Bitmap) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Test Bitmap display"
        )
    }
}
