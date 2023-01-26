package com.github.godspeed010.martatraintime.feature_train.presentation.trains.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.godspeed010.martatraintime.R
import com.github.godspeed010.martatraintime.feature_train.domain.model.FilterDropdownItem
import com.github.godspeed010.martatraintime.feature_train.domain.util.OrderType
import com.github.godspeed010.martatraintime.feature_train.domain.util.TrainOrder

private val TopAppBarHeight = 64.dp

@Composable
fun MainAppBar(
    isSearchSectionVisible: Boolean,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchTriggered: () -> Unit,
    onFilterToggled: () -> Unit,
    isFilterDropdownExpanded: Boolean,
    trainOrder: TrainOrder,
    onOrderChange: (TrainOrder) -> Unit
) {
    when (isSearchSectionVisible) {
        false -> {
            DefaultAppBar(
                onSearchClicked = onSearchTriggered,
                onFilterToggled = onFilterToggled,
                isFilterDropdownExpanded = isFilterDropdownExpanded,
                trainOrder = trainOrder,
                onOrderChange = onOrderChange,
            )
        }
        true -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(
    onSearchClicked: () -> Unit,
    onFilterToggled: () -> Unit,
    isFilterDropdownExpanded: Boolean,
    trainOrder: TrainOrder,
    onOrderChange: (TrainOrder) -> Unit
) {
    val filterDropdownItems = listOf(
        FilterDropdownItem(
            textRes = R.string.direction,
            isSelected = trainOrder is TrainOrder.Direction,
            onSelect = { onOrderChange(TrainOrder.Direction(trainOrder.orderType)) }
        ),
        FilterDropdownItem(
            textRes = R.string.line,
            isSelected = trainOrder is TrainOrder.Line,
            onSelect = { onOrderChange(TrainOrder.Line(trainOrder.orderType)) }
        ),
        FilterDropdownItem(
            textRes = R.string.station,
            isSelected = trainOrder is TrainOrder.Station,
            onSelect = { onOrderChange(TrainOrder.Station(trainOrder.orderType)) }
        ),
        FilterDropdownItem(
            textRes = R.string.wait_time,
            isSelected = trainOrder is TrainOrder.WaitTime,
            onSelect = { onOrderChange(TrainOrder.WaitTime(trainOrder.orderType)) }
        ),
    )

    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        actions = {
            IconButton(onClick = onSearchClicked) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                )
            }
            Box {
                IconButton(onClick = onFilterToggled) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Filter Icon",
                    )
                }
                DropdownMenu(
                    expanded = isFilterDropdownExpanded,
                    onDismissRequest = onFilterToggled
                ) {
                    filterDropdownItems.forEach {
                        DropdownMenuItem(
                            onClick = it.onSelect,
                            text = {
                                DefaultRadioButton(
                                    text = stringResource(id = it.textRes),
                                    selected = it.isSelected,
                                    onSelect = it.onSelect
                                )
                            }
                        )
                    }

                    // TODO move logic to ViewModel. No need to send params in these events
                    val oppositeOrderType = trainOrder.orderType.oppositeOrderType()
                    DropdownMenuItem(
                        onClick = { onOrderChange(trainOrder.copy(oppositeOrderType)) },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = stringResource(id = R.string.ascending)
                                )
                                Spacer(Modifier.width(8.dp))
                                Checkbox(
                                    checked = trainOrder.orderType == OrderType.Ascending,
                                    onCheckedChange = {
                                        onOrderChange(trainOrder.copy(oppositeOrderType))
                                    },
                                )
                            }
                        }
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit
) {
    val focusRequester = FocusRequester()
    LaunchedEffect(true) {
        focusRequester.requestFocus()
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TopAppBarHeight),
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search here...",
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { keyboardController?.hide() }
            ),
        )
    }
}

@Preview
@Composable
fun PreviewDefaultAppBar() {
    DefaultAppBar(
        onSearchClicked = {},
        onFilterToggled = {},
        isFilterDropdownExpanded = false,
        trainOrder = TrainOrder.Direction(OrderType.Ascending),
        onOrderChange = {}
    )
}

@Preview
@Composable
fun PreviewSearchAppBar() {
    SearchAppBar(
        text = "",
        onTextChange = {},
        onCloseClicked = {}
    )
}