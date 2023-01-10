package com.github.godspeed010.martatraintime.feature_train.presentation.trains

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.godspeed010.martatraintime.feature_train.presentation.TrainViewModel
import com.github.godspeed010.martatraintime.feature_train.presentation.trains.components.MainAppBar
import com.github.godspeed010.martatraintime.feature_train.presentation.trains.components.TrainItem

private val TAG = "TrainsScreen"
@OptIn(ExperimentalMaterialApi::class)
@ExperimentalAnimationApi
@Composable
fun TrainsScreen(
    viewModel: TrainViewModel = hiltViewModel()
) {
    val state = viewModel.trainScreenState.value
    val scaffoldState = rememberScaffoldState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { viewModel.onEvent(TrainsEvent.RefreshData) }
    )

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

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
    ) { innerPadding ->
        Box(Modifier.pullRefresh(pullRefreshState)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(vertical = 6.dp)
            ) {
                items(state.displayedTrainList) { train ->
                    TrainItem(train)
                }
            }
            PullRefreshIndicator(
                refreshing = state.isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}