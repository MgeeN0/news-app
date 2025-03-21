package fi.oamk.news_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fi.oamk.news_app.ui.appbars.BottomBar
import fi.oamk.news_app.ui.screens.Personalized
import fi.oamk.news_app.ui.screens.TopNews
import fi.oamk.news_app.ui.screens.SettingsScreen
import fi.oamk.news_app.ui.theme.NewsappTheme
import fi.oamk.news_app.viewmodel.SettingsViewModel
import okhttp3.internal.http2.Settings


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = viewModel()
            NewsappTheme (darkTheme = settingsViewModel.switched) {
                BottomNavigationApp(settingsViewModel)
                }
            }
        }
    }

@Composable
fun BottomNavigationApp(settingsViewModel: SettingsViewModel) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(navController)
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        NavHost (
            navController = navController,
            startDestination = "main"
        ) {
            composable(route = "main") {
               TopNews(navController,modifier.padding(top = 65.dp))
            }
            composable(route = "search") {
                Personalized(navController,modifier.padding(start = 8.dp, end = 8.dp, top = 65.dp))
            }
            composable(route = "settings") {
                SettingsScreen(navController,modifier.padding(top = 65.dp),settingsViewModel)
            }
        }
    }
}
