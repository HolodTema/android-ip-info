package com.terabyte.ipinfo.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun ScreenHistory(viewModel: MainViewModel) {
    val listIpInfo by viewModel.stateFlowSearchHistory.collectAsStateWithLifecycle()

    if (listIpInfo.isEmpty()) {
        TextNoHistory()
    }
    else {
        Column(

        ) {
            SurfaceAmountHistoryItems(listIpInfo.size)
            ListIPInfoSearchHistory(listIpInfo)
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
fun ListIPInfoSearchHistory(listIpInfo: List<IPInfo>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(listIpInfo) {
            ListItemIpInfo(it)
        }
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
fun ListItemIpInfo(ipInfo: IPInfo) {
    val textNoData = stringResource(R.string.no_data)

    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    val textDate = dateFormat.format(ipInfo.infoDate)

    val textLocation = if (ipInfo.latitude == null || ipInfo.longitude == null) {
        textNoData
    }
    else {
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
            Text(
                text = stringResource(R.string.ip_information),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(
                text = "${stringResource(R.string.ip_address)} ${ipInfo.ip ?: textNoData}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
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
                text = "${stringResource(R.string.date_of_search)} ${textDate}",
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
        }
    }
}

