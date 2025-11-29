package com.terabyte.ipinfo.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.terabyte.domain.model.IPInfo
import com.terabyte.ipinfo.viewModel.MainViewModel

@Composable
fun ScreenHistory(viewModel: MainViewModel) {
    val listIpInfo by viewModel.stateFlowSearchHistory.collectAsStateWithLifecycle()

    if (listIpInfo.isEmpty()) {
        TextNoHistory()
    }
    else {
        SurfaceAmountHistoryItems(listIpInfo.size)
        ListIPInfoSearchHistory(listIpInfo)
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

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SurfaceAmountHistoryItems(listIpInfo.size)

        LazyColumn {
            items(listIpInfo) {
                ListItemIPInfo(it)
            }
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
                "Amount history items: ${amount}",
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun ListItemIPInfo(ipInfo: IPInfo) {
    Text("${ipInfo.ip}")
}

