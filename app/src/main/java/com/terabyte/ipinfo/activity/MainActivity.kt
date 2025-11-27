package com.terabyte.ipinfo.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.terabyte.ipinfo.application.MyApplication
import com.terabyte.ipinfo.ui.navigation.BottomNavigationItem
import com.terabyte.ipinfo.ui.screen.ScreenHistory
import com.terabyte.ipinfo.ui.screen.ScreenSearch
import com.terabyte.ipinfo.ui.screen.ScreenSettings
import com.terabyte.ipinfo.ui.theme.IPInfoTheme
import com.terabyte.ipinfo.viewModel.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by lazy {
        val ipInfoRepository = (application as MyApplication).ipInfoRepository
        val settingsRepository = (application as MyApplication).settingsRepository
        val factory = MainViewModel.Factory(ipInfoRepository, settingsRepository)
        ViewModelProvider(this, factory)[MainViewModel::class]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IPInfoTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController)
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavigationHostContainer(navController, innerPadding, viewModel)
                }
            }
        }
    }
}


@Composable
fun NavigationHostContainer(
    navController: NavHostController,
    paddingVals: PaddingValues,
    viewModel: MainViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = "search",
        modifier = Modifier.padding(paddingVals),
        builder = {
            composable("search") {
                ScreenSearch(viewModel)
            }
            composable("history") {
                ScreenHistory(viewModel)
            }
            composable("settings") {
                ScreenSettings(viewModel)
            }
        }
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavigationItem(
            label = "Search",
            icon = Icons.Filled.Search,
            route = "search"
        ),
        BottomNavigationItem(
            label = "History",
            icon = Icons.AutoMirrored.Filled.List,
            route = "history"
        ),
        BottomNavigationItem(
            label = "Settings",
            icon = Icons.Filled.Settings,
            route = "settings"
        )
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                selected = item.route == currentRoute,
                onClick = {
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.label)
                },
                label = {
                    Text(text = item.label)
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White, // Icon color when selected
                    unselectedIconColor = Color.White, // Icon color when not selected
                    selectedTextColor = Color.White, // Label color when selected
                    indicatorColor = Color(0xFF195334) // Highlight color for selected
                )
            )
        }
    }
}

