package com.github.godspeed010.martatraintime.feature_train.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.godspeed010.martatraintime.feature_train.domain.model.Train
import com.github.godspeed010.martatraintime.feature_train.domain.use_case.GetTrains
import com.github.godspeed010.martatraintime.feature_train.domain.use_case.TrainsUseCases
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
                    trains = trainsUseCases.orderTrains(
                        trainScreenState.value.trains,
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
        }
    }
}
