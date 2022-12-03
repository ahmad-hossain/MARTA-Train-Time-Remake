package com.github.godspeed010.martatraintime.feature_alert.presentation.alert_configuration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.godspeed010.martatraintime.common.components.CustomBottomAppBar
import com.github.godspeed010.martatraintime.common.model.Screen
import com.github.godspeed010.martatraintime.feature_alert.presentation.AlertViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.github.godspeed010.martatraintime.R

@Destination
@Composable
fun AlertScreen(
    navigator: DestinationsNavigator, viewModel: AlertViewModel = hiltViewModel()
) {
    val state = viewModel.alertScreenState.value
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(id = R.string.app_name))
                },
            )
        },
        bottomBar = {
            CustomBottomAppBar(
                modifier = Modifier,
                currentScreen = Screen.ALERT,
                onClickTrains = { navigator.popBackStack() },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                TextField(
                    value = state.latitude.toString(),
                    onValueChange = { viewModel.onEvent(AlertEvent.LatitudeEntered(it)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                TextField(
                    value = state.longitude.toString(),
                    onValueChange = { viewModel.onEvent(AlertEvent.LongitudeEntered(it)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            Button(onClick = { viewModel.onEvent(AlertEvent.StartClicked) }) {
                Text("Start")
            }
        }
    }
}