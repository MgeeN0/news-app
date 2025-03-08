package fi.oamk.news_app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fi.oamk.news_app.BottomNavigationApp
import fi.oamk.news_app.R
import fi.oamk.news_app.ui.appbars.SettingsBar
import fi.oamk.news_app.ui.theme.NewsappTheme
import fi.oamk.news_app.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(navController: NavController, modifier: Modifier, settingsViewModel: SettingsViewModel) {
    NewsappTheme(settingsViewModel.switched) {
        SettingsBar(navController)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.dark_mode)
            )
            Switch(
                checked = settingsViewModel.switched,
                onCheckedChange = {
                    settingsViewModel.switched = it
                }
            )
        }
    }
}