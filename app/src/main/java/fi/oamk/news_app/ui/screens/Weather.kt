package fi.oamk.news_app.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Weather(modifier: Modifier) {
    Text(
        modifier = modifier.padding(vertical = 10.dp, horizontal = 10.dp),
        text = "Weather data"
    )
}