package com.github.godspeed010.martatraintime.feature_train.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.godspeed010.martatraintime.feature_train.domain.model.Train
import com.github.godspeed010.martatraintime.feature_train.domain.use_case.GetTrains
import com.github.godspeed010.martatraintime.feature_train.domain.util.OrderType
import com.github.godspeed010.martatraintime.feature_train.domain.util.SortingHelper.appropriateSortingValue
import com.github.godspeed010.martatraintime.feature_train.domain.util.TrainOrder
import com.github.godspeed010.martatraintime.feature_train.presentation.trains.TrainsEvent
import com.github.godspeed010.martatraintime.feature_train.presentation.trains.TrainsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "TrainViewModel"

@HiltViewModel
class TrainViewModel @Inject constructor(
    private val getTrains: GetTrains
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
                    trains = orderTrains(trainOrder = event.trainOrder),
                    trainOrder = event.trainOrder
                )
            }
            is TrainsEvent.ToggleOrderSection -> {
                _trainScreenState.value = trainScreenState.value.copy(
                    isOrderSectionVisible = !trainScreenState.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getAllTrains() {
        viewModelScope.launch {
            _trainScreenState.value = trainScreenState.value.copy(
                trains = orderTrains(
                    trains = getTrains(),
                    trainOrder = TrainOrder.Line(OrderType.Descending)
                )
            )
        }
    }

    private fun orderTrains(
        trains: List<Train> = trainScreenState.value.trains,
        trainOrder: TrainOrder
    ): List<Train> {

        return when (trainOrder.orderType) {
            is OrderType.Ascending -> {
                when (trainOrder) {
                    is TrainOrder.Station -> trains.sortedBy { it.STATION.lowercase() }
                    is TrainOrder.Line -> trains.sortedBy { it.LINE.lowercase() }
                    is TrainOrder.Direction -> trains.sortedBy { it.DIRECTION.lowercase() }
                    is TrainOrder.WaitTime -> trains.sortedBy { it.WAITING_TIME.appropriateSortingValue() }
                }
            }
            is OrderType.Descending -> {
                when (trainOrder) {
                    is TrainOrder.Station -> trains.sortedByDescending { it.STATION.lowercase() }
                    is TrainOrder.Line -> trains.sortedByDescending { it.LINE.lowercase() }
                    is TrainOrder.Direction -> trains.sortedByDescending { it.DIRECTION.lowercase() }
                    is TrainOrder.WaitTime -> trains.sortedByDescending { it.WAITING_TIME.appropriateSortingValue() }
                }
            }
        }
    }
}
