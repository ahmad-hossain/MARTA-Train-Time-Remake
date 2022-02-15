package com.example.martatraintimeremake.ui.theme

import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val Red600 = "#e53935".color
val Blue500 = "#2196f3".color
val Amber600 = "#ffb300".color
val Green500 = "#4caf50".color

val String.color
    get() = Color(android.graphics.Color.parseColor(this))