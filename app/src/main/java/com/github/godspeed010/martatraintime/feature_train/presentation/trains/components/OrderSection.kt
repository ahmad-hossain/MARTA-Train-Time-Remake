package com.github.godspeed010.martatraintime.feature_train.presentation.trains.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.godspeed010.martatraintime.feature_train.domain.util.OrderType
import com.github.godspeed010.martatraintime.feature_train.domain.util.TrainOrder

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    trainOrder: TrainOrder = TrainOrder.Line(OrderType.Descending),
    onOrderChange: (TrainOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Station",
                selected = trainOrder is TrainOrder.Station,
                onSelect = { onOrderChange(TrainOrder.Station(trainOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Line",
                selected = trainOrder is TrainOrder.Line,
                onSelect = { onOrderChange(TrainOrder.Line(trainOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Direction",
                selected = trainOrder is TrainOrder.Direction,
                onSelect = { onOrderChange(TrainOrder.Direction(trainOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Wait Time",
                selected = trainOrder is TrainOrder.WaitTime,
                onSelect = { onOrderChange(TrainOrder.WaitTime(trainOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = trainOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(trainOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = trainOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(trainOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}