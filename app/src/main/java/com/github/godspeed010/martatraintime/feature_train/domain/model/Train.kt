package com.github.godspeed010.martatraintime.feature_train.domain.model

/*
  EXAMPLE:
  "DESTINATION": "Airport",
  "DIRECTION": "S",
  "EVENT_TIME": "2/11/2022 10:45:40 PM",
  "LINE": "GOLD",
  "NEXT_ARR": "10:46:02 PM",
  "STATION": "EAST POINT STATION",
  "TRAIN_ID": "301506",
  "WAITING_SECONDS": "18",
  "WAITING_TIME": "Arriving"
 */
data class Train(
    val destination: String,
    val direction: String,
    val eventTime: String,
    val line: String,
    val nextArr: String,
    val station: String,
    val trainId: String,
    val waitingSeconds: String,
    val waitingTime: String
)