package com.github.godspeed010.martatraintime.feature_train.presentation.trains.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
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
import kotlin.math.max

@Composable
fun TrainItem(
    train: Train,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(6.dp),
        elevation = 10.dp
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                //color box with cardinal direction inside
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            color = when (train.line) {
                                "GOLD" -> Amber600
                                "RED" -> Red600
                                "GREEN" -> Green500
                                "BLUE" -> Blue500
                                else -> Color.White
                            }
                        )
                        .padding(6.dp)
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(constraints)

                            val maxSize = max(placeable.height, placeable.width)

                            layout(maxSize, maxSize) {
                                placeable.placeRelative((maxSize - placeable.width) / 2, (maxSize - placeable.height) / 2)
                            }
                        }
                ) {
                    Text(
                        text = train.direction,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                //station name
                Text(text = train.station, fontWeight = FontWeight.Bold)
            }

            //if time in format '2 min' display on two lines,
            // else it is 'Arriving' so display on one line
            if (train.waitingTime.split(" ").size > 1) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val (waitTime, timeUnit) = train.waitingTime.split(" ")
                    Text(
                        text = waitTime,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(text = timeUnit)
                }

            } else {
                Text(
                    text = train.waitingTime,
                    fontWeight = FontWeight.Bold
                )
            }
        }
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