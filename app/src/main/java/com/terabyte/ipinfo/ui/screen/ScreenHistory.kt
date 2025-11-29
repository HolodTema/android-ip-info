package com.terabyte.ipinfo.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.terabyte.domain.model.IPInfo
import com.terabyte.ipinfo.R
import com.terabyte.ipinfo.viewModel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID

@Composable
fun ScreenHistory(viewModel: MainViewModel) {
    val listIpInfo by viewModel.stateFlowSearchHistory.collectAsStateWithLifecycle()
    val stateExpandedItemId = viewModel.stateFlowExpandedIpInfoId.collectAsStateWithLifecycle()

    val changeExpandedItemIdListener = viewModel::changeExpandedItemId

    if (listIpInfo.isEmpty()) {
        TextNoHistory()
    } else {
        Column(

        ) {
            SurfaceAmountHistoryItems(listIpInfo.size)
            ListIPInfoSearchHistory(listIpInfo, stateExpandedItemId, changeExpandedItemIdListener)
        }

    }
}


@Composable
fun TextNoHistory() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            "There you will see the history of your IP-address search",
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun SurfaceAmountHistoryItems(amount: Int) {
    Surface(
        shadowElevation = 10.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                "Amount history items: $amount",
                fontSize = 16.sp
            )
        }
    }
}


@Composable
fun ListIPInfoSearchHistory(
    listIpInfo: List<IPInfo>,
    stateExpandedItemId: State<UUID?>,
    onExpandedItemIdChanged: (UUID?) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(listIpInfo) {
            if (stateExpandedItemId.value == null) {
                ListItemIpInfoCompact(it) {
                    onExpandedItemIdChanged(it.id)
                }
            } else if (stateExpandedItemId.value == it.id) {
                ListItemIpInfoExpanded(it) {
                    onExpandedItemIdChanged(null)
                }
            } else {
                ListItemIpInfoCompact(it) {
                    onExpandedItemIdChanged(it.id)
                }
            }
        }
    }
}


@Composable
fun ListItemIpInfoExpanded(ipInfo: IPInfo, onButtonCompactClicked: () -> Unit) {
    val textNoData = stringResource(R.string.no_data)

    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    val textDate = dateFormat.format(ipInfo.infoDate)

    val textLocation = if (ipInfo.latitude == null || ipInfo.longitude == null) {
        textNoData
    } else {
        "${ipInfo.latitude}°   ${ipInfo.longitude}°"
    }

    Surface(
        shadowElevation = 10.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                Row {
                    IconButton(
                        onClick = {

                        },

                        ) {
                        Icon(
                            painterResource(R.drawable.ic_copy),
                            contentDescription = "copy",
                            modifier = Modifier
                                .size(26.dp)
                        )
                    }
                    IconButton(
                        onClick = {

                        },

                        ) {
                        Icon(
                            painterResource(R.drawable.ic_share),
                            contentDescription = "share",
                            modifier = Modifier
                                .size(26.dp)
                        )
                    }
                    IconButton(
                        onClick = {

                        },

                        ) {
                        Icon(
                            painterResource(R.drawable.ic_delete),
                            contentDescription = "delete",
                            modifier = Modifier
                                .size(26.dp)
                        )
                    }
                }
                IconButton(
                    onClick = onButtonCompactClicked,

                    ) {
                    Icon(
                        painterResource(R.drawable.ic_compact),
                        contentDescription = "compact",
                        modifier = Modifier
                            .size(26.dp)
                    )
                }
            }
            Text(
                text = "${stringResource(R.string.ip_address)} ${ipInfo.ip ?: textNoData}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
            Text(
                text = "${stringResource(R.string.host)} ${ipInfo.hostname ?: textNoData}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
            Text(
                text = "${stringResource(R.string.country)} ${ipInfo.country ?: textNoData}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
            Text(
                text = "${stringResource(R.string.region)} ${ipInfo.region ?: textNoData}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
            Text(
                text = "${stringResource(R.string.city)} ${ipInfo.city ?: textNoData}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
            Text(
                text = "${stringResource(R.string.organization)} ${ipInfo.organization ?: textNoData}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
            Text(
                text = "${stringResource(R.string.timezone)} ${ipInfo.timezone ?: textNoData}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
            Text(
                text = "${stringResource(R.string.location)} $textLocation",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
            Text(
                text = "${stringResource(R.string.date_of_search)} ${textDate}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
        }
    }
}


@Composable
fun ListItemIpInfoCompact(ipInfo: IPInfo, onButtonExpandClicked: () -> Unit) {
    val textNoData = stringResource(R.string.no_data)

    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    val textDate = dateFormat.format(ipInfo.infoDate)

    Surface(
        shadowElevation = 10.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = "${stringResource(R.string.ip_address)} ${ipInfo.ip ?: textNoData}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
                Text(
                    text = "${stringResource(R.string.host)} ${ipInfo.hostname ?: textNoData}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
                Text(
                    text = "${stringResource(R.string.country)} ${ipInfo.country ?: textNoData}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
            }
            IconButton(
                onClick = onButtonExpandClicked
            ) {
                Icon(
                    painterResource(R.drawable.ic_expand),
                    contentDescription = "expand",
                    modifier = Modifier
                        .size(26.dp)
                )
            }
        }
    }
}

