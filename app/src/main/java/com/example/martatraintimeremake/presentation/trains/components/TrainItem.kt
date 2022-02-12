package com.example.martatraintimeremake.presentation.trains.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.martatraintimeremake.domain.model.Train

@Composable
fun TrainItem(
    train: Train,
    modifier: Modifier = Modifier
) {
    val color: Color = when(train.LINE) {
        "GOLD" -> Color.Yellow
        "RED" -> Color.Red
        "GREEN" -> Color.Green
        "BLUE" -> Color.Blue
        else -> Color.White
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(color)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = train.DESTINATION, fontWeight = FontWeight.Bold)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(12.dp),
                    imageVector = Icons.Default.Explore,
                    contentDescription = "Compass Direction"
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = train.DIRECTION)
            }
        }
        //if time in format '2 min', else it is 'Arriving'
        if (train.WAITING_TIME.split(" ").size > 1) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = train.WAITING_TIME.split(" ")[0],
                    fontWeight = FontWeight.Bold,
                )
                Text(text = "min")
            }

        } else {
            Text(text = train.WAITING_TIME)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrainItemPreview() {
    TrainItem(
        train = Train(
            DESTINATION = "Airport",
            DIRECTION = "S",
            EVENT_TIME = "2/11/2022 10:45:40 PM",
            LINE = "GOLD",
            NEXT_ARR = "10:46:02 PM",
            STATION = "EAST POINT STATION",
            TRAIN_ID = "301506",
            WAITING_SECONDS = "18",
            WAITING_TIME = "10 min"
        )
    )

}