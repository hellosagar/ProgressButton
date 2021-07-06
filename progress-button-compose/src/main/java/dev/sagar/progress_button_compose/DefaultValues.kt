package dev.sagar.progress_button_compose

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val DEFAULT_COLOR = Color(0xFF0052FE)
val PRESSED_COLOR = Color(0xFF0845D1)
val DISABLED_COLOR = Color(0xFF537CD3)
val FINISHED_COLOR = Color(0xFF27AE60)
val CONTENT_COLOR = Color.White

const val TITLE_TEXT = "Button"
const val FINISHED_TEXT = "Finish"

val DEFAULT_ELEVATION = 8.dp
val DEFAULT_ELEVATION_ON_PRESS = 12.dp

val defaultTextStyle =
    TextStyle(color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
