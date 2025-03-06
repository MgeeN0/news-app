package fi.oamk.news_app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CategoryViewModel: ViewModel() {
    var expanded by mutableStateOf(false)
    var selectedCategory by mutableStateOf("general")

    fun selectCategory(category: String) {
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