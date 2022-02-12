package com.example.martatraintimeremake.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.martatraintimeremake.domain.model.Train
import com.example.martatraintimeremake.domain.use_case.GetTrains
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "TrainViewModel"

@HiltViewModel
class TrainViewModel @Inject constructor(
    private val getTrains: GetTrains
) : ViewModel() {

    private val _trainListState = mutableStateOf(listOf<Train>())
    val trainListState: State<List<Train>> = _trainListState

    init {
        viewModelScope.launch {
            _trainListState.value = getTrains()
            Log.d(TAG, "Retrieved ${trainListState.value.size} trains")
            Log.d(TAG, "First train is ${trainListState.value[0]}")
        }
    }

}