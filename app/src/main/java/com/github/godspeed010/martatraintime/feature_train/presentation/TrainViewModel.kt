package com.github.godspeed010.martatraintime.feature_train.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.godspeed010.martatraintime.feature_train.domain.use_case.TrainsUseCases
import com.github.godspeed010.martatraintime.feature_train.domain.util.OrderType
import com.github.godspeed010.martatraintime.feature_train.domain.util.TrainOrder
import com.github.godspeed010.martatraintime.feature_train.presentation.trains.TrainsEvent
import com.github.godspeed010.martatraintime.feature_train.presentation.trains.TrainsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TrainViewModel @Inject constructor(
    private val trainsUseCases: TrainsUseCases,
) : ViewModel() {

    var state by mutableStateOf(TrainsState())
        private set

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    init {
        refreshData()
    }

    fun onEvent(event: TrainsEvent) {
        Timber.d("${event::class.simpleName} : $event")

        when (event) {
            is TrainsEvent.Order -> {
                //if order did not change
                if (state.trainOrder::class == event.trainOrder::class &&
                    state.trainOrder.orderType == event.trainOrder.orderType
                ) {
                    return
                }

                state = state.copy(
                    displayedTrainList = trainsUseCases.orderTrains(
                        state.displayedTrainList,
                        event.trainOrder
                    ),
                    trainOrder = event.trainOrder
                )
            }
            is TrainsEvent.FilterToggled -> {
                state = state.copy(
                    isFilterDropdownExpanded = !state.isFilterDropdownExpanded
                )
            }
            is TrainsEvent.ToggleSearchSection -> {
                //if search section is visible, then we are turning it off now
                // if search section is not visible, we are turning it on
                state = state.copy(
                    searchQuery = "",
                    isSearchSectionVisible = !state.isSearchSectionVisible
                )

                //set displayedTrainList back to trains when closing SearchSection
                //and order trains using current orderState, as orderState may have changed while
                //viewing filtered trains.
                if (!state.isSearchSectionVisible) {
                    state = state.copy(
                        displayedTrainList = trainsUseCases.orderTrains(
                            state.trains,
                            state.trainOrder
                        )
                    )
                }
            }
            is TrainsEvent.Search -> {
                state = state.copy(
                    searchQuery = event.searchQuery,
                    displayedTrainList = trainsUseCases.searchTrains(
                        state.trains,
                        event.searchQuery
                    )
                )
            }
            TrainsEvent.RefreshData -> {
                val currentTimeSecs = System.currentTimeMillis() / 1000
                val timeSinceLastRefresh = currentTimeSecs - state.lastRefreshTimeSecs
                if (timeSinceLastRefresh < REFRESH_COOLDOWN_SECS) {
                    viewModelScope.launch {
                        state = state.copy(isRefreshing = true)
                        delay(1500)
                        state = state.copy(isRefreshing = false)
                        _toastMessage.emit("Please wait ${REFRESH_COOLDOWN_SECS}s before refreshing again")
                    }
                    return
                }
                refreshData()
                viewModelScope.launch { _toastMessage.emit("Successfully refreshed data") }
            }
        }
    }

    private fun refreshData() {
        state = state.copy(isRefreshing = true)
        viewModelScope.launch {
            val orderedTrains = trainsUseCases.orderTrains(
                trains = trainsUseCases.getTrains(),
                trainOrder = TrainOrder.Line(OrderType.Descending),
            )
            val displayedTrainList = when (state.isSearchSectionVisible) {
                true -> {
                    trainsUseCases.searchTrains(
                        trains = orderedTrains,
                        searchQuery = state.searchQuery
                    )
                }
                false -> orderedTrains
            }
            state = state.copy(
                lastRefreshTimeSecs = System.currentTimeMillis() / 1000,
                isRefreshing = false,
                trains = orderedTrains,
                displayedTrainList = displayedTrainList,
            )
        }
    }

    companion object {
        const val REFRESH_COOLDOWN_SECS = 60
    }
}
