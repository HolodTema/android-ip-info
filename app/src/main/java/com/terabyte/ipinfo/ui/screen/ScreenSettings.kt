package com.terabyte.ipinfo.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.terabyte.ipinfo.ui.theme.IPInfoTheme
import com.terabyte.ipinfo.viewModel.MainViewModel

@Composable
fun ScreenSettings(viewModel: MainViewModel) {
    val isDarkTheme by viewModel.stateFlowIsDarkTheme.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(scrollState)
    ) {
        SurfaceSettingsAppearance(isDarkTheme) {
            viewModel.saveDarkTheme(it)
        }

        SurfaceSettingsAppData {
            viewModel.deleteAllIpInfo()
        }

        SurfaceSettingsAboutApp()
    }
}

@Composable
fun SurfaceSettingsAppearance(
    isDarkTheme: Boolean,
    onDarkModeCheckedChanged: (Boolean) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 10.dp,
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = "Appearance",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                Text(
                    text = "Dark theme:",
                    fontSize = 18.sp
                )
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = onDarkModeCheckedChanged
                )
            }
        }
    }
}

@Composable
fun SurfaceSettingsAppData(
    onDeleteSearchHistory: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 10.dp,
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = "App data",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            Button(
                onClick = onDeleteSearchHistory,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                ),
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.End)
            ) {
                Text("Delete search history")
            }
        }
    }
}

@Composable
fun SurfaceSettingsAboutApp() {
    Surface(
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 10.dp,
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = 10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = "About app",
                fontSize = 14.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScreenSettingsPreview() {
    IPInfoTheme {
        ScreenSettings(viewModel = viewModel())
    }
}
