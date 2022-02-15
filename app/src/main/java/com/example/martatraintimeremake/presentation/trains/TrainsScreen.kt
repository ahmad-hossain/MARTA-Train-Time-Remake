package com.example.martatraintimeremake.presentation.trains

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.martatraintimeremake.presentation.TrainViewModel
import com.example.martatraintimeremake.presentation.trains.components.OrderSection
import com.example.martatraintimeremake.presentation.trains.components.TrainItem

@ExperimentalAnimationApi
@Composable
fun TrainsScreen(
    viewModel: TrainViewModel = hiltViewModel()
) {
    val state = viewModel.trainScreenState.value
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "All Trains",
                    style = MaterialTheme.typography.h4
                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(TrainsEvent.ToggleOrderSection)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort"
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    trainOrder = state.trainOrder,
                    onOrderChange = {
                        viewModel.onEvent(TrainsEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            TrainList(viewModel)
        }
    }
}

@Composable
fun TrainList(viewModel: TrainViewModel) {
    val trainListState = viewModel.trainScreenState.value

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(vertical = 6.dp)
    ) {
        items(trainListState.trains) { train ->
            TrainItem(train)
        }
    }
}