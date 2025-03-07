package fi.oamk.news_app.viewmodel

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel

class SearchOptionsViewModel: ViewModel() {
    var searchPhrase by mutableStateOf("")
    var expanded by mutableStateOf(false)
    var selectedSorting by mutableStateOf("relevancy")
    var searchBar by mutableStateOf("Search news")
    var language by mutableStateOf("en")
    var languageExpanded by mutableStateOf(false)
    var sortTextFieldSize by mutableStateOf(Size.Zero)
    var languageTextFieldSize by mutableStateOf(Size.Zero)
    var canSearch by mutableStateOf(false) //this lets user refresh articles once he changes sorting or language, if request was sent before



    fun setExpanded() {
        expanded = !expanded
    }

    fun setLExpanded() {
        languageExpanded = !expanded
    }

    fun setFalse() {
        expanded = false
    }

    fun setLFalse() {
        languageExpanded = false
    }

    fun selectSorting(sort: String) {
        selectedSorting = sort
        Log.d("SORTING",selectedSorting)
    }

    fun selectLanguage(language_: String) {
        language = language_
        Log.d("LANGUAGE",language)
    }

    fun getIcon(expanded: Boolean): ImageVector {
        if (expanded)
            return Icons.Filled.KeyboardArrowUp
        return Icons.Filled.KeyboardArrowDown
    }

    fun getText(text: String): String {
        return when(text) {
            "relevancy" -> "Relevancy"
            "popularity" -> "Popularity"
            "publishedAt" -> "Upload date"
            "de" -> "German"
            "en" -> "English"
            "es" -> "Spanish"
            "fr" -> "French"
            "it" -> "Italian"
            "nl" -> "Dutch"
            "no" -> "Norwegian"
            "pt" -> "Portuguese"
            "ru" -> "Russian"
            "sv" -> "Swedish"
            "zh" -> "Chinese"
            else -> "None"
        }
    }


}