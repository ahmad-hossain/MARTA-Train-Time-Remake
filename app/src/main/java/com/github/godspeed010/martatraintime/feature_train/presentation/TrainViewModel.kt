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
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "TrainViewModel"

@HiltViewModel
class TrainViewModel @Inject constructor(
    private val trainsUseCases: TrainsUseCases,
) : ViewModel() {

    private val _trainScreenState = mutableStateOf(TrainsState())
    val trainScreenState: State<TrainsState> = _trainScreenState

    init {
        getAllTrains()
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
            is TrainsEvent.ToggleOrderSection -> {
                _trainScreenState.value = trainScreenState.value.copy(
                    isOrderSectionVisible = !trainScreenState.value.isOrderSectionVisible
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
        }
    }

    private fun getAllTrains() {
        viewModelScope.launch {
            _trainScreenState.value = trainScreenState.value.copy(
                trains = trainsUseCases.orderTrains(
                    trains = trainsUseCases.getTrains(),
                    trainOrder = TrainOrder.Line(OrderType.Descending)
                )
            )
            //set the displayed list to be the parsed Train list
            _trainScreenState.value = trainScreenState.value.copy(
                displayedTrainList = trainScreenState.value.trains
            )
        }
    }
}
