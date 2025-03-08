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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fi.oamk.news_app.R
import fi.oamk.news_app.viewmodel.CategoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNewsBar(navController: NavController, categoryViewModel: CategoryViewModel = viewModel()) {
    val context = LocalContext.current
    TopAppBar(
        title = { Text(stringResource(R.string.top_news, categoryViewModel.selectedCategory)) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        actions = {
            IconButton(onClick = { categoryViewModel.changeExpanded() }) {
                Icon(
                    painter = painterResource(R.drawable.category),
                    contentDescription = stringResource(R.string.open_menu)
                )
            }
            DropdownMenu(
                expanded = categoryViewModel.expanded,
                onDismissRequest = { categoryViewModel.setFalse() }
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.business)) },
                    onClick = { categoryViewModel.selectCategory(context.getString(R.string.sBusiness)) }
                )
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.entertainment)) },
                    onClick = { categoryViewModel.selectCategory(context.getString(R.string.sEntertainment))}
                )
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.general)) },
                    onClick = { categoryViewModel.selectCategory(context.getString(R.string.sGeneral))}
                )
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.health)) },
                    onClick = { categoryViewModel.selectCategory(context.getString(R.string.sHealth))}
                )
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.science)) },
                    onClick = { categoryViewModel.selectCategory(context.getString(R.string.sScience))}
                )
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.sports)) },
                    onClick = { categoryViewModel.selectCategory(context.getString(R.string.sSports))}
                )
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.technology)) },
                    onClick = { categoryViewModel.selectCategory(context.getString(R.string.sTechnology))}
                )
            }
            IconButton(onClick = { navController.navigate(context.getString(R.string.settings)) }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = stringResource(R.string.open_submenu)
                )
            }
        },

    )
}