package com.example.martatraintimeremake.domain.model

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
    val DESTINATION: String,
    val DIRECTION: String,
    val EVENT_TIME: String,
    val LINE: String,
    val NEXT_ARR: String,
    val STATION: String,
    val TRAIN_ID: String,
    val WAITING_SECONDS: String,
    val WAITING_TIME: String
)