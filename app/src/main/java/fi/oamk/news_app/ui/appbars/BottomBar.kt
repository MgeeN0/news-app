package fi.oamk.news_app.ui.appbars

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import fi.oamk.news_app.R

@Composable
fun BottomBar(navController: NavController) {
    val backStackEntry = navController.currentBackStackEntryAsState() //This entry gives you access to the current NavDestination.
    // The selected state of each BottomNavigationItem can then be determined by comparing the item's route with the route
    // of the current destination and its parent destinations to handle cases when you are using nested navigation using the NavDestination hierarchy.
    val tabs = listOf(
        TabItem("News", R.drawable.newspaper1,route="main"),
        TabItem("Personalized",R.drawable.personalized,route="personalized"),
        TabItem("Weather",R.drawable.weather,route="weather"),
    )
    NavigationBar {
        tabs.forEach{tab ->
            val selected = tab.route ===
                    backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = {navController.navigate(tab.route)},
                label = {Text(tab.title)},
                icon = {
                    Icon(
                    painter = painterResource(tab.icon),
                    contentDescription = "bottom bar icon"
                )
                }
            )
        }
    }
}