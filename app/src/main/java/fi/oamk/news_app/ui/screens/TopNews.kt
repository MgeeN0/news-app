package fi.oamk.news_app.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopNews(modifier: Modifier) {
    Text(
        modifier = modifier.padding(vertical = 10.dp, horizontal = 10.dp),
        text = "Top news will be here"
    )
}