package fi.oamk.news_app.ui.appbars

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fi.oamk.news_app.R
import fi.oamk.news_app.viewmodel.CategoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNewsBar(navController: NavController, categoryViewModel: CategoryViewModel = viewModel()) {
    TopAppBar(
        title = { Text("Top News: " + categoryViewModel.selectedCategory) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        actions = {
            IconButton(onClick = { categoryViewModel.changeExpanded() }) {
                Icon(
                    painter = painterResource(R.drawable.category),
                    contentDescription = "Open menu"
                )
            }
            DropdownMenu(
                expanded = categoryViewModel.expanded,
                onDismissRequest = { categoryViewModel.setFalse() }
            ) {
                DropdownMenuItem(
                    text = { Text("Business") },
                    onClick = { categoryViewModel.selectCategory("business") }
                )
                DropdownMenuItem(
                    text = { Text("Entertainment") },
                    onClick = { categoryViewModel.selectCategory("entertainment")}
                )
                DropdownMenuItem(
                    text = { Text("General") },
                    onClick = { categoryViewModel.selectCategory("general")}
                )
                DropdownMenuItem(
                    text = { Text("Health") },
                    onClick = { categoryViewModel.selectCategory("health")}
                )
                DropdownMenuItem(
                    text = { Text("Science") },
                    onClick = { categoryViewModel.selectCategory("science")}
                )
                DropdownMenuItem(
                    text = { Text("Sports") },
                    onClick = { categoryViewModel.selectCategory("sports")}
                )
                DropdownMenuItem(
                    text = { Text("Technology") },
                    onClick = { categoryViewModel.selectCategory("technology")}
                )
            }
            IconButton(onClick = { navController.navigate("settings") }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Open submenu"
                )
            }
        },

    )
}