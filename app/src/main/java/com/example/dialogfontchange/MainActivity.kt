package com.example.dialogfontchange

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import java.io.File
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Content() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content() {
    val context = LocalContext.current
    var token by remember { mutableIntStateOf(0) }
    MaterialTheme(
        typography = run {
            token.let {}
            val file = File(context.filesDir, "font.ttf").takeIf {
                it.exists()
            }?.let { FontFamily(Font(it)) }
            MaterialTheme.typography.copy(
                bodyMedium = MaterialTheme.typography.bodyMedium.copy(fontFamily = file)
            )
        },
    ) {
        LazyColumn {
            item {
                BasicAlertDialog(onDismissRequest = {}) {
                    CompositionLocalProvider(
                        LocalTextStyle provides MaterialTheme.typography.bodyMedium
                    ) {
                        val resources = LocalResources.current
                        Button(onClick = {
                            val outFile = File(context.filesDir, "font.ttf")
                            resources.openRawResource(
                                listOf(R.raw.estedad, R.raw.blank).random()
                            ).use { input -> outFile.outputStream().use(input::copyTo) }
                            token = Random.nextInt()
                        }) { Text("abc") }
                        Text(token.toString())
                    }
                }
            }
        }
    }
}
