package com.github.godspeed010.martatraintime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.github.godspeed010.martatraintime.feature_train.presentation.trains.TrainsScreen
import com.github.godspeed010.martatraintime.feature_train.ui.theme.MARTATrainTimeRemakeTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MARTATrainTimeRemakeTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}