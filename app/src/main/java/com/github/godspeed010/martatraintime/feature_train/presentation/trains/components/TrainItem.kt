package com.github.godspeed010.martatraintime.feature_train.presentation.trains.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.godspeed010.martatraintime.feature_train.domain.model.Train
import com.github.godspeed010.martatraintime.feature_train.ui.theme.Amber600
import com.github.godspeed010.martatraintime.feature_train.ui.theme.Blue500
import com.github.godspeed010.martatraintime.feature_train.ui.theme.Green500
import com.github.godspeed010.martatraintime.feature_train.ui.theme.Red600

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainItem(
    train: Train,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier.clip(RoundedCornerShape(8.dp)),
        tonalElevation = 8.dp,
        leadingContent = {
            Text(
                modifier = Modifier
                    .background(
                        color = when (train.line) {
                            "GOLD" -> Amber600
                            "RED" -> Red600
                            "GREEN" -> Green500
                            "BLUE" -> Blue500
                            else -> Color.Gray
                        }, shape = CircleShape
                    )
                    .squareLayout()
                    .padding(8.dp), text = train.direction, color = Color.Black
            )
        },
        headlineText = { Text(train.station) },
        trailingContent = {
            val bodyLargeFontSize = MaterialTheme.typography.bodyLarge.fontSize
            val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black
            //if time in format '2 min' display on two lines,
            // else it is 'Arriving' so display on one line
            if (train.waitingTime.split(" ").size > 1) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val (waitTime, timeUnit) = train.waitingTime.split(" ")
                    Text(
                        fontSize = bodyLargeFontSize,
                        text = waitTime,
                        color = textColor,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = timeUnit,
                        color = textColor,
                        fontSize = bodyLargeFontSize,
                    )
                }

            } else {
                Text(
                    text = train.waitingTime,
                    fontSize = bodyLargeFontSize,
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            }
        },
    )
}

fun Modifier.squareLayout() = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)

    val maxSize = maxOf(placeable.height, placeable.width)

    layout(maxSize, maxSize) {
        placeable.placeRelative(
            (maxSize - placeable.width) / 2, (maxSize - placeable.height) / 2
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TrainItemPreview() {
    TrainItem(
        train = Train(
            destination = "Airport",
            direction = "S",
            eventTime = "2/11/2022 10:45:40 PM",
            line = "GOLD",
            nextArr = "10:46:02 PM",
            station = "EDGEWOOD CANDLER PARK STATION TESTING ab",
            trainId = "301506",
            waitingSeconds = "18",
            waitingTime = "10 min"
        )
    )

}