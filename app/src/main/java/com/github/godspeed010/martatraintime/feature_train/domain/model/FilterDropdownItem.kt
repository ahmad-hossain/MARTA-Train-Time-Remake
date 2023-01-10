package com.github.godspeed010.martatraintime.feature_train.domain.model

data class FilterDropdownItem(
    val textRes: Int,
    val isSelected: Boolean,
    val onSelect: () -> Unit,
)
