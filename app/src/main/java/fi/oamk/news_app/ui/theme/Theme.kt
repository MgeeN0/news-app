package fi.oamk.news_app.ui.theme

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import fi.oamk.news_app.viewmodel.SearchOptionsViewModel
import fi.oamk.news_app.viewmodel.SettingsViewModel

private val DarkColorScheme = darkColorScheme(
    primary = lightGreenv2,
    primaryContainer = veryDarkGreen,
    onPrimary = Color.White,
    onPrimaryContainer = Color(0xFFB3F1BE),
    secondary = lightGreyGreenv2,
    secondaryContainer = darkGreyGreen,
    onSecondary = Color.LightGray,
    tertiary = lightBluev2,
    tertiaryContainer = darkBluev2,
    errorContainer = darkRedv2,
    onErrorContainer = lightRedv2
)

private val LightColorScheme = lightColorScheme(
    /*
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    primary = darkGreen,
    secondary = greyGreen,
    tertiary = darkBlue

     */
    primary = darkGreen,
    primaryContainer = lightGreen,
    onPrimary = Color.Black,
    onPrimaryContainer = Color(0xFF16512C),
    secondary = greyGreen,
    secondaryContainer = lightGreyGreen,
    onSecondary = Color.DarkGray,
    tertiary = darkBlue,
    tertiaryContainer = lightBlue,
    background = veryLightGreen,
    errorContainer = lightRed,
    onErrorContainer = darkRed



            /*
    primaryContainer = lightGreen,
    secondaryContainer = lightGreyGreen,
    tertiaryContainer = lightBlue

             */

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun isDarkMode(settingsViewModel: SettingsViewModel = viewModel()): Boolean {
    return settingsViewModel.switched
}

@Composable
fun NewsappTheme(
    darkTheme: Boolean,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    settingsViewModel: SettingsViewModel = viewModel(),
    content: @Composable () -> Unit
) {
    Log.d("DARK MODE",darkTheme.toString())
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    var colorScheme1 = LightColorScheme
    if(darkTheme)
    {
        colorScheme1 = DarkColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}