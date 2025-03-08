package fi.oamk.news_app.ui.screens

import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import fi.oamk.news_app.R
import fi.oamk.news_app.model.Article
import fi.oamk.news_app.model.Content
import fi.oamk.news_app.ui.appbars.TopNewsBar
import fi.oamk.news_app.viewmodel.ArticlesViewModel
import fi.oamk.news_app.viewmodel.CategoryViewModel
import fi.oamk.news_app.viewmodel.NewsUiState
import kotlin.math.min


@Composable
fun TopNews(navController: NavController,modifier: Modifier, articlesViewModel: ArticlesViewModel = viewModel(), categoryViewModel: CategoryViewModel = viewModel())
{
    val selectedCategory = categoryViewModel.selectedCategory
    if(!categoryViewModel.hasSentRequest) {
        articlesViewModel.getArticlesList(selectedCategory)
        categoryViewModel.hasSentRequest = true
    }
    TopNewsBar(navController)
        ArticlesScreen(modifier,articlesViewModel.articleUiState)

}

@Composable
fun ArticlesScreen(modifier: Modifier,uiState: NewsUiState) {
    //Log.d("Articles Screen","hello")
    when (uiState) {
        is NewsUiState.Loading -> LoadingScreen()
        is NewsUiState.Success -> NewsCards(modifier,uiState.articles)
        is NewsUiState.Error -> ErrorScreen(Modifier.padding(start = 80.dp, top = 130.dp))
    }
}

@Composable
fun NewsCards(modifier: Modifier,response: Article) {
    LazyColumn (
        modifier = modifier
    ){
        items(response.articles) { article ->
            //Log.d("CARDS","cards display")
            if(article.source.id === null) {
                //Log.d("isNULL","source id is null")
                article.source.id = stringResource(R.string.unknown)
            }
            if(article.description === null) {
                //Log.d("isNULL","desc is null")
                article.description = " "
            }
            if(article.title === null) {
                //Log.d("isNULL","title is null")
                article.description = " "
            }
            if(article.source.name === null) {
                //Log.d("isNULL","source name is null")
                article.description = " "
            }
            ArticleCard(article)
        }
    }
}

@Composable
fun ArticleCard(article:Content) {
    val uriHandler = LocalUriHandler.current
    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .fillMaxWidth()
            .clickable { uriHandler.openUri(article.url) },
        shape = RoundedCornerShape(CornerSize(10.dp)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
                AsyncImage(
                    model = article.urlToImage,
                    placeholder = painterResource(R.drawable.image_icon),
                    error = painterResource(R.drawable.image_icon),
                    contentDescription = "Image"
                )
            article.title?.let { Text(text = it,style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.padding(top=6.dp)) }
            article.description?.let { Text(text = it,style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSecondary, modifier = Modifier.padding(top=5.dp, bottom=20.dp)) }
            HorizontalDivider( modifier = Modifier.padding(end=280.dp),thickness = 2.dp, color = MaterialTheme.colorScheme.primary)
            Row {
                article.source.name?.let { Text(text = it, fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.padding(top=3.dp, bottom=5.dp)) }
                article.publishedAt?.let { Text(text = it.substring(0, 10), fontSize = 10.sp, color = MaterialTheme.colorScheme.tertiary, modifier = Modifier
                    .padding(top = 3.dp, bottom = 5.dp)
                    .fillMaxWidth(), textAlign = TextAlign.Right) }
            }
        }

    }

    //article.source.id?.let { Log.d("SOURCE ID", it) }
}

@Composable
fun LoadingScreen() {
    Text(
        modifier = Modifier.padding(start = 8.dp,top = 130.dp),
        text = stringResource(R.string.loading)
    )
}
@Composable
fun ErrorScreen(modifier:Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(CornerSize(10.dp)),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            disabledContentColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onErrorContainer,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = stringResource(R.string.error_retrieving_data_from_api)
        )
    }
}