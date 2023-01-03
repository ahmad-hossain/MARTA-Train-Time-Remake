package com.github.godspeed010.martatraintime.feature_train.domain.model

import com.google.gson.annotations.SerializedName

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
    @SerializedName("DESTINATION")
    val destination: String,
    @SerializedName("DIRECTION")
    val direction: String,
    @SerializedName("EVENT_TIME")
    val eventTime: String,
    @SerializedName("LINE")
    val line: String,
    @SerializedName("NEXT_ARR")
    val nextArr: String,
    @SerializedName("STATION")
    val station: String,
    @SerializedName("TRAIN_ID")
    val trainId: String,
    @SerializedName("WAITING_SECONDS")
    val waitingSeconds: String,
    @SerializedName("WAITING_TIME")
    val waitingTime: String
)