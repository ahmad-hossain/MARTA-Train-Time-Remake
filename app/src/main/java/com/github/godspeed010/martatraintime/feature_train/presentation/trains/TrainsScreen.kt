package com.github.godspeed010.martatraintime.feature_train.presentation.trains

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.godspeed010.martatraintime.feature_train.domain.model.Train
import com.github.godspeed010.martatraintime.feature_train.presentation.TrainViewModel
import com.github.godspeed010.martatraintime.feature_train.presentation.trains.components.MainAppBar
import com.github.godspeed010.martatraintime.feature_train.presentation.trains.components.TrainItem

private val TAG = "TrainsScreen"
@ExperimentalAnimationApi
@Composable
fun TrainsScreen(
    viewModel: TrainViewModel = hiltViewModel()
) {
    val state = viewModel.trainScreenState.value
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MainAppBar(
                isSearchSectionVisible = state.isSearchSectionVisible,
                searchTextState = state.searchQuery,
                onTextChange = {
                    Log.i(TAG, "Search Query: $it")
                    viewModel.onEvent(TrainsEvent.Search(it))
                },
                onCloseClicked = {
                    Log.i(TAG, "Search CLOSED")
                    viewModel.onEvent(TrainsEvent.ToggleSearchSection) },
                onSearchTriggered = {
                    Log.i(TAG, "Search OPENED")
                    viewModel.onEvent(TrainsEvent.ToggleSearchSection) },
                onFilterToggled = {
                    Log.i(TAG, "Filter TOGGLED")
                    viewModel.onEvent(TrainsEvent.FilterToggled)
                },
                isFilterDropdownExpanded = state.isFilterDropdownExpanded,
                trainOrder = state.trainOrder,
                onOrderChange = { viewModel.onEvent(TrainsEvent.Order(it)) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            TrainList(state.displayedTrainList)
        }
    }
}

@Composable
fun TrainList(displayedTrainList: List<Train>) {
    LazyColumn(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(vertical = 6.dp)
    ) {
        items(displayedTrainList) { train ->
            TrainItem(train)
        }
    }
}