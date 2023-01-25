package com.github.godspeed010.martatraintime.feature_train.presentation.trains

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@Composable
fun TrainsScreen(
    viewModel: TrainViewModel = hiltViewModel()
) {
    val state = viewModel.state
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
        topBar = {
            MainAppBar(
                isSearchSectionVisible = state.isSearchSectionVisible,
                searchTextState = state.searchQuery,
                onTextChange = { viewModel.onEvent(TrainsEvent.Search(it)) },
                onCloseClicked = { viewModel.onEvent(TrainsEvent.ToggleSearchSection) },
                onSearchTriggered = { viewModel.onEvent(TrainsEvent.ToggleSearchSection) },
                onFilterToggled = { viewModel.onEvent(TrainsEvent.FilterToggled) },
                isFilterDropdownExpanded = state.isFilterDropdownExpanded,
                trainOrder = state.trainOrder,
                onOrderChange = { viewModel.onEvent(TrainsEvent.Order(it)) }
            )
        }
    ) { innerPadding ->
        Box(
            Modifier
                .padding(innerPadding)
                .pullRefresh(pullRefreshState)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
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