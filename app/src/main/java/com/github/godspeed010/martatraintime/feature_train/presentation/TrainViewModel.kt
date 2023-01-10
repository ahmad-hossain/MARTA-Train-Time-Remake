package com.github.godspeed010.martatraintime.feature_train.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
import javax.inject.Inject

const val TAG = "TrainViewModel"

@HiltViewModel
class TrainViewModel @Inject constructor(
    private val trainsUseCases: TrainsUseCases,
) : ViewModel() {

    private val _trainScreenState = mutableStateOf(TrainsState())
    val trainScreenState: State<TrainsState> = _trainScreenState

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    init {
        refreshData()
    }

    fun onEvent(event: TrainsEvent) {
        when (event) {
            is TrainsEvent.Order -> {
                //if order did not change
                if (trainScreenState.value.trainOrder::class == event.trainOrder::class &&
                    trainScreenState.value.trainOrder.orderType == event.trainOrder.orderType
                ) {
                    return
                }

                _trainScreenState.value = trainScreenState.value.copy(
                    displayedTrainList = trainsUseCases.orderTrains(
                        trainScreenState.value.displayedTrainList,
                        event.trainOrder
                    ),
                    trainOrder = event.trainOrder
                )
            }
            is TrainsEvent.FilterToggled -> {
                _trainScreenState.value = trainScreenState.value.copy(
                    isFilterDropdownExpanded = !trainScreenState.value.isFilterDropdownExpanded
                )
            }
            is TrainsEvent.ToggleSearchSection -> {
                //if search section is visible, then we are turning it off now
                // if search section is not visible, we are turning it on
                _trainScreenState.value = trainScreenState.value.copy(
                    searchQuery = "",
                    isSearchSectionVisible = !trainScreenState.value.isSearchSectionVisible
                )

                //set displayedTrainList back to trains when closing SearchSection
                //and order trains using current orderState, as orderState may have changed while
                //viewing filtered trains.
                if (!trainScreenState.value.isSearchSectionVisible) {
                    _trainScreenState.value = trainScreenState.value.copy(
                        displayedTrainList = trainsUseCases.orderTrains(
                            trainScreenState.value.trains,
                            trainScreenState.value.trainOrder
                        )
                    )
                }
            }
            is TrainsEvent.Search -> {
                _trainScreenState.value = trainScreenState.value.copy(
                    searchQuery = event.searchQuery,
                    displayedTrainList = trainsUseCases.searchTrains(
                        trainScreenState.value.trains,
                        event.searchQuery
                    )
                )
            }
            TrainsEvent.RefreshData -> {
                val currentTimeSecs = System.currentTimeMillis() / 1000
                val timeSinceLastRefresh = currentTimeSecs - trainScreenState.value.lastRefreshTimeSecs
                if (timeSinceLastRefresh < REFRESH_COOLDOWN_SECS) {
                    viewModelScope.launch {
                        _trainScreenState.value = trainScreenState.value.copy(isRefreshing = true)
                        delay(1500)
                        _trainScreenState.value = trainScreenState.value.copy(isRefreshing = false)
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
        _trainScreenState.value = trainScreenState.value.copy(isRefreshing = true)
        viewModelScope.launch {
            val orderedTrains = trainsUseCases.orderTrains(
                trains = trainsUseCases.getTrains(),
                trainOrder = TrainOrder.Line(OrderType.Descending),
            )
            val displayedTrainList = when (trainScreenState.value.isSearchSectionVisible) {
                true -> {
                    trainsUseCases.searchTrains(
                        trains = orderedTrains,
                        searchQuery = trainScreenState.value.searchQuery
                    )
                }
                false -> orderedTrains
            }
            _trainScreenState.value = trainScreenState.value.copy(
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
