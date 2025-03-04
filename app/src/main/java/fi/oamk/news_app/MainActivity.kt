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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fi.oamk.news_app.ui.appbars.BottomBar
import fi.oamk.news_app.ui.appbars.TopBar
import fi.oamk.news_app.ui.screens.Personalized
import fi.oamk.news_app.ui.screens.TopNews
import fi.oamk.news_app.ui.screens.Weather
import fi.oamk.news_app.ui.theme.NewsappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsappTheme {
                BottomNavigationApp()
                }
            }
        }
    }

@Composable
fun BottomNavigationApp() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar()
        },
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
               TopNews(modifier)
            }
            composable(route = "personalized") {
                Personalized(modifier)
            }
            composable(route = "weather") {
                Weather(modifier)
            }
        }
    }
}
