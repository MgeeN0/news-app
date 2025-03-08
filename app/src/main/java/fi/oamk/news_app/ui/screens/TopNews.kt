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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
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
fun TopNews(modifier: Modifier,articlesViewModel: ArticlesViewModel = viewModel(),categoryViewModel: CategoryViewModel = viewModel())
{
    val selectedCategory = categoryViewModel.selectedCategory
    if(!categoryViewModel.hasSentRequest) {
        articlesViewModel.getArticlesList(selectedCategory)
        categoryViewModel.hasSentRequest = true
    }
    Log.d("CATEGORY",selectedCategory)
    TopNewsBar()
        ArticlesScreen(modifier,articlesViewModel.articleUiState)
    Log.d("Top News","hello")

}

@Composable
fun ArticlesScreen(modifier: Modifier,uiState: NewsUiState) {
    Log.d("Articles Screen","hello")
    when (uiState) {
        is NewsUiState.Loading -> LoadingScreen()
        is NewsUiState.Success -> NewsCards(modifier,uiState.articles)
        is NewsUiState.Error -> ErrorScreen()
    }
}

@Composable
fun NewsCards(modifier: Modifier,response: Article) {
    LazyColumn (
        modifier = modifier
    ){
        items(response.articles) { article ->
            /*
            Text(
                text = article.title,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
            HorizontalDivider(color = Color.LightGray, thickness = 2.dp)
             */
            Log.d("CARDS","cards display")
            if(article.source.id === null) {
                Log.d("isNULL","source id is null")
                article.source.id = "unknown"
            }
            if(article.description === null) {
                Log.d("isNULL","desc is null")
                article.description = " "
            }
            if(article.title === null) {
                Log.d("isNULL","title is null")
                article.description = " "
            }
            if(article.source.name === null) {
                Log.d("isNULL","source name is null")
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
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            .fillMaxWidth().clickable { uriHandler.openUri(article.url) },
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
            article.title?.let { Text(text = it,style = MaterialTheme.typography.titleLarge, color = Color.Black, modifier = Modifier.padding(top=6.dp)) }
            article.description?.let { Text(text = it,style = MaterialTheme.typography.bodyLarge, color = Color.DarkGray, modifier = Modifier.padding(top=5.dp, bottom=20.dp)) }
            HorizontalDivider( modifier = Modifier.padding(end=280.dp),thickness = 2.dp, color = MaterialTheme.colorScheme.primary)
            Row {
                article.source.name?.let { Text(text = it, fontSize = 12.sp, color = Color.DarkGray, modifier = Modifier.padding(top=3.dp, bottom=5.dp)) }
                article.publishedAt?.let { Text(text = it.substring(0, 10), fontSize = 10.sp, color = MaterialTheme.colorScheme.tertiary, modifier = Modifier.padding(top=3.dp, bottom=5.dp).fillMaxWidth(), textAlign = TextAlign.Right) }
            }
        }

    }

    article.source.id?.let { Log.d("SOURCE ID", it) }
}

@Composable
fun LoadingScreen() {
    Text(
        modifier = Modifier.padding(start = 8.dp,top = 130.dp),
        text = "Loading...")
}
@Composable
fun ErrorScreen() {
    Text(
        modifier = Modifier.padding(start = 8.dp,top = 130.dp),
        text = "Error retrieving data from API.")
}