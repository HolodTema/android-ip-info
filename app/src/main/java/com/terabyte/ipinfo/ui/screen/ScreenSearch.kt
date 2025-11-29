package com.terabyte.ipinfo.ui.screen

import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.terabyte.domain.model.IPInfo
import com.terabyte.ipinfo.ui.theme.IPInfoTheme
import com.terabyte.ipinfo.viewModel.MainViewModel
import java.util.Date
import java.util.UUID

@Composable
fun ScreenSearch(viewModel: MainViewModel) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(scrollState)
    ) {
        IpSearchBar()

        val ipInfo = IPInfo(
            id = UUID.randomUUID(),
            ip = "192.168.0.1",
            hostname = "google.com",
            country = "US",
            region = "California",
            city = "LA",
            organization = "Google INC",
            timezone = "UTC-3",
            latitude = 0.0,
            longitude = 0.0,
            infoDate = Date()
        )
        IpInfoCard(ipInfo)
    }
}

@Composable
fun IpSearchBar() {
    Surface(
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 10.dp,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = "Enter IPV4 address",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            )

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IpPartTextField()
                Text(
                    text = ".",
                    fontSize = 36.sp
                )
                IpPartTextField()
                Text(
                    text = ".",
                    fontSize = 36.sp
                )
                IpPartTextField()
                Text(
                    text = ".",
                    fontSize = 36.sp
                )
                IpPartTextField()
            }
        }
    }
}

@Composable
fun IpPartTextField() {
    OutlinedTextField(
        value = "",
        onValueChange = {

        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        placeholder = {
            Text("255")
        },
        modifier = Modifier
            .width(70.dp)
    )
}

@Composable
fun IpInfoCard(ipInfo: IPInfo?) {
    Surface(
        shadowElevation = 10.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = 10.dp)
    ) {
        if (ipInfo == null) {
            Text(
                text = "Search results will be here",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = "Ip information:",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = "ipv4-address: ${ipInfo.ip}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )
                Text(
                    text = "hostname: ${ipInfo.hostname}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
                Text(
                    text = "country: ${ipInfo.country}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
                Text(
                    text = "region: ${ipInfo.region}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
                Text(
                    text = "city: ${ipInfo.city}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
                Text(
                    text = "organization: ${ipInfo.organization}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
                Text(
                    text = "Date: ${ipInfo.infoDate}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
                Text(
                    text = "Timezone: ${ipInfo.timezone}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
                Text(
                    text = "Location: ${ipInfo.latitude}°   ${ipInfo.longitude}°",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Button(
                        onClick = {

                        }
                    ) {
                        Text("Copy")
                    }
                    Button(
                        onClick = {

                        }
                    ) {
                        Text("Share")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun IpSearchBarPreview() {
    IPInfoTheme {
        IpSearchBar()
    }
}

@Preview(showBackground = false)
@Composable
fun IpInfoCardPreview() {
    IPInfoTheme {
        val ipInfo = IPInfo(
            id = UUID.randomUUID(),
            ip = "192.168.0.1",
            hostname = "google.com",
            country = "US",
            region = "California",
            city = "LA",
            organization = "Google INC",
            timezone = "UTC-3",
            latitude = 0.0,
            longitude = 0.0,
            infoDate = Date()
        )
        IpInfoCard(ipInfo)
    }
}

