package fi.oamk.news_app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SearchOptionsViewModel: ViewModel() {
    var searchPhrase by mutableStateOf("")
    var expanded by mutableStateOf(false)
    var selectedSorting by mutableStateOf("relevancy")
    var searchBar by mutableStateOf("Search news")
    var language by mutableStateOf("us")
    var languageExpanded by mutableStateOf(false)



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
    }

    fun selectLanguage(language_: String) {
        language = language_
    }

}