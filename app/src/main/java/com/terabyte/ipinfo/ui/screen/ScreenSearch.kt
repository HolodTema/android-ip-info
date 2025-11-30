package com.terabyte.ipinfo.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.terabyte.data.LOG_TAG
import com.terabyte.ipinfo.R
import com.terabyte.domain.model.IPInfo
import com.terabyte.ipinfo.ui.theme.IPInfoTheme
import com.terabyte.ipinfo.viewModel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@Composable
fun ScreenSearch(viewModel: MainViewModel) {
    val scrollState = rememberScrollState()
    val ipInfo by viewModel.stateFlowIpInfo.collectAsStateWithLifecycle()
    val resources = LocalResources.current
    val toastCopied = Toast.makeText(LocalContext.current, stringResource(R.string.toast_copied), Toast.LENGTH_SHORT)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(scrollState)
    ) {
        IpSearchBar { ip ->
            Log.d(LOG_TAG, "ip text: $ip")
            viewModel.getIpInfo(ip)
        }

        IpInfoCard(ipInfo) {
            viewModel.copyIPInfoToClipboard(resources, it)
            toastCopied.show()
        }
    }
}

@Composable
fun IpSearchBar(onButtonSearchClicked: (String) -> Unit) {
    val ipPart1 = rememberSaveable { mutableStateOf("0") }
    val ipPart2 = rememberSaveable { mutableStateOf("0") }
    val ipPart3 = rememberSaveable { mutableStateOf("0") }
    val ipPart4 = rememberSaveable { mutableStateOf("0") }

    Surface(
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 10.dp,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
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
                IpPartTextField(ipPart1)
                Text(
                    text = ".",
                    fontSize = 36.sp
                )
                IpPartTextField(ipPart2)
                Text(
                    text = ".",
                    fontSize = 36.sp
                )
                IpPartTextField(ipPart3)
                Text(
                    text = ".",
                    fontSize = 36.sp
                )
                IpPartTextField(ipPart4)
            }

            Button(
                onClick = {
                    val ip = "${ipPart1.value}.${ipPart2.value}.${ipPart3.value}.${ipPart4.value}"
                    onButtonSearchClicked(ip)
                },
                enabled = isIpPartsValid(ipPart1.value, ipPart2.value, ipPart3.value, ipPart4.value),
                modifier = Modifier
                    .padding(top = 20.dp)
            ) {
                Text(
                    text = "Search!"
                )
            }
        }
    }
}

@Composable
fun IpPartTextField(stateText: MutableState<String>) {
    OutlinedTextField(
        value = stateText.value,
        onValueChange = {
            if (it.length <= 3) {
                stateText.value = it
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        colors = OutlinedTextFieldDefaults.colors(
            errorTextColor = Color.Red,
            errorBorderColor = Color.Red,
        ),
        isError = !isIpPartValid(stateText.value),
        modifier = Modifier
            .width(70.dp)
    )
}

@Composable
fun IpInfoCard(ipInfo: IPInfo?, onCopyButtonClicked: (IPInfo)->Unit) {
    val textNoData = stringResource(R.string.no_data)
    val textDate = if (ipInfo == null) {
        textNoData
    }
    else {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        dateFormat.format(ipInfo.infoDate)
    }
    val textLocation = if (ipInfo == null || ipInfo.latitude == null || ipInfo.longitude == null) {
        textNoData
    }
    else {
        "${ipInfo.latitude}°   ${ipInfo.longitude}°"
    }

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
                    .padding(10.dp)
            )
        } else {
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
                    text = "${stringResource(R.string.date_of_search)} $textDate",
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
                            onCopyButtonClicked(ipInfo)
                        }
                    ) {
                        Text(stringResource(R.string.copy))
                    }
                    Button(
                        onClick = {

                        }
                    ) {
                        Text(stringResource(R.string.share))
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
        IpSearchBar {

        }
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
        IpInfoCard(ipInfo) {

        }
    }
}

private fun isIpPartValid(ipPart: String): Boolean {
    if (ipPart.length > 1 && ipPart.startsWith("0")) {
        return false
    }
    try {
        val ipPartInt = ipPart.toInt()
        return (ipPartInt >= 0) && (ipPartInt <= 255)
    } catch (e: Exception) {
        return false
    }
}

private fun isIpPartsValid(
    ipPart1: String,
    ipPart2: String,
    ipPart3: String,
    ipPart4: String
): Boolean {
    return isIpPartValid(ipPart1) &&
            isIpPartValid(ipPart2) &&
            isIpPartValid(ipPart3) &&
            isIpPartValid(ipPart4)
}


