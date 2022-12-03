package com.github.godspeed010.martatraintime.common.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Train
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.godspeed010.martatraintime.common.model.Screen

@Composable
fun CustomBottomAppBar(
    modifier: Modifier,
    currentScreen: Screen,
    onClickTrains: () -> Unit = {},
    onClickAlert: () -> Unit = {},
) {
    BottomAppBar(modifier = modifier, cutoutShape = CircleShape) {
        BottomNavigation {
            BottomNavigationItem(
                selected = currentScreen == Screen.TRAINS,
                onClick = onClickTrains,
                icon = {
                    Icon(imageVector = Icons.Outlined.Train, contentDescription = "Trains")
                })
            BottomNavigationItem(
                selected = currentScreen == Screen.ALERT,
                onClick = onClickAlert,
                icon = {
                    Icon(imageVector = Icons.Outlined.Notifications, contentDescription = "Alert")
                })
        }
    }
}