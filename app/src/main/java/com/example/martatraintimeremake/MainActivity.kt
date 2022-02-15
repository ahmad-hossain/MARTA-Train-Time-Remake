package com.example.martatraintimeremake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.martatraintimeremake.presentation.TrainViewModel
import com.example.martatraintimeremake.presentation.trains.TrainList
import com.example.martatraintimeremake.presentation.trains.TrainsScreen
import com.example.martatraintimeremake.presentation.trains.components.TrainItem
import com.example.martatraintimeremake.ui.theme.MARTATrainTimeRemakeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MARTATrainTimeRemakeTheme {
                App()
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun App() {
    TrainsScreen()
}