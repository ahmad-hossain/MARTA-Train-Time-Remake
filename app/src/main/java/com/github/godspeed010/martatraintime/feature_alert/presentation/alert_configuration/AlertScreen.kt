package com.github.godspeed010.martatraintime.feature_alert.presentation.alert_configuration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.godspeed010.martatraintime.feature_alert.presentation.AlertViewModel

@Composable
fun AlertScreen(
    viewModel: AlertViewModel = hiltViewModel()
) {
    val state = viewModel.alertScreenState.value
    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState,
        topBar = { TopAppBar { Text("Foo") } }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Row {
                TextField(
                    value = state.latitude.toString(),
                    onValueChange = { viewModel.onEvent(AlertEvent.LatitudeEntered(it)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            Button(onClick = { viewModel.onEvent(AlertEvent.StartClicked) }) {
                Text("Start")
            }
        }
    }
}

@Preview
@Composable
fun AlertScreenPreview() {
    AlertScreen()
}