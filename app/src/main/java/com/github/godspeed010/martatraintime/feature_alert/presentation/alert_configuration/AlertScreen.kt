package com.github.godspeed010.martatraintime.feature_alert.presentation.alert_configuration

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.godspeed010.martatraintime.R
import com.github.godspeed010.martatraintime.common.components.CustomBottomAppBar
import com.github.godspeed010.martatraintime.common.model.Screen
import com.github.godspeed010.martatraintime.feature_alert.presentation.AlertViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

private const val TAG = "AlertScreen"

@OptIn(ExperimentalPermissionsApi::class)
@Destination
@Composable
fun AlertScreen(
    navigator: DestinationsNavigator, viewModel: AlertViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val scaffoldState = rememberScaffoldState()
    val permissionsState =
        rememberMultiplePermissionsState(permissions = viewModel.requiredPermissions,
            onPermissionsResult = {
                if (it.containsValue(false)) {
                    Log.i(TAG, "A Permission was not granted")
                    navigator.popBackStack()
                }
            })

    LaunchedEffect(Unit) {
        permissionsState.launchMultiplePermissionRequest()
    }

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
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(value = state.latitudeText,
                label = { Text("Latitude") },
                onValueChange = { viewModel.onEvent(AlertEvent.LatitudeEntered(it)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(value = state.longitudeText,
                label = { Text("Longitude") },
                onValueChange = { viewModel.onEvent(AlertEvent.LongitudeEntered(it)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            val ctx = LocalContext.current
            Button(
                onClick = { viewModel.onEvent(AlertEvent.StartClicked(ctx)) },
                enabled = state.isButtonEnabled,
            ) {
                Text("Start")
            }
        }
    }
}