package com.github.godspeed010.martatraintime.feature_train.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Red600 = "#e53935".color
val Blue500 = "#2196f3".color
val Amber600 = "#ffb300".color
val Green500 = "#4caf50".color

val String.color
    get() = Color(android.graphics.Color.parseColor(this))