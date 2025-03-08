package fi.oamk.news_app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CategoryViewModel: ViewModel() {
    var expanded by mutableStateOf(false)
    var selectedCategory by mutableStateOf("general")
    var hasSentRequest by mutableStateOf(false) //this ensures request is sent only once after launching the app or switching to the news screen

    fun selectCategory(category: String) {
        hasSentRequest = false
        selectedCategory = category
        //expanded = !expanded
    }

    fun changeExpanded() {
        expanded = !expanded
    }

    fun setFalse() {
        expanded = false
    }
}