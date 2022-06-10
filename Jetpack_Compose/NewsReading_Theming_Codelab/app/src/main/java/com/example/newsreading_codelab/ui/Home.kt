package com.example.newsreading_codelab.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.icu.text.MessagePattern
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import androidx.compose.ui.unit.dp
import com.example.newsreading_codelab.R
import com.example.newsreading_codelab.ui.data.Post
import com.example.newsreading_codelab.ui.data.PostRepo
import com.example.newsreading_codelab.ui.theme.JetnewsTheme
import com.example.newsreading_codelab.ui.theme.NewsReading_CodelabTheme
import java.util.*

@Composable
fun HomeApp() {

    val featured = remember {
        PostRepo.getFeaturedPost()
    }

    val posts = remember {
        PostRepo.getPosts()
    }

    MaterialTheme {
        Scaffold(
            topBar = {
                AppBar()
            }
        ) { innerPadding ->
            LazyColumn(contentPadding = innerPadding) {
                item {
                    Header(text = stringResource(id = R.string.top))

                }

                item {
                    FeaturedPost(post = featured, modifier = Modifier.padding(16.dp))
                }

                item {
                    Header(text = stringResource(id = R.string.popular))
                }

                items(posts) { post ->
                    PostItem(post = post)
                    Divider(startIndent = 72.dp)

                }
            }

        }
    }

}

// App Bar
@Composable
fun AppBar() {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_title))
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Rounded.Palette,
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        },
        backgroundColor = MaterialTheme.colors.primarySurface
    )
}

// Header
@Composable
fun Header(
    text: String,
    modifier: Modifier = Modifier
) {

    Surface(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
        contentColor = MaterialTheme.colors.primary,
        modifier = modifier
    ) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = text,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { heading() }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

    }


}

// Feature Post
@Composable
fun FeaturedPost(
    post: Post,
    modifier: Modifier = Modifier
) {
    Card(modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
        ) {
            Image(
                painter = painterResource(id = post.imageId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .heightIn(min = 180.dp)
                    .fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = post.title,
                modifier = Modifier.padding(16.dp)
            )

            Text(
                text = post.metadata.author.name,
                modifier = Modifier.padding(16.dp)
            )
            PostMetadata(post = post, Modifier.padding(16.dp))
            Spacer(modifier = Modifier.height(16.dp))
        }

    }

}

@Composable
fun PostMetadata(
    post: Post,
    modifier: Modifier = Modifier
) {
    val divider = "  •  "
    val tagDivider = "  "
    val text = buildAnnotatedString {
        append(post.metadata.date)
        append(divider)
        append(stringResource(id = R.string.read_time, post.metadata.readTimeMinutes))
        append(divider)

        val tagStyle = MaterialTheme.typography.overline.toSpanStyle().copy(
            background = MaterialTheme.colors.primary.copy(alpha = 0.1f)
        )

        post.tags.forEachIndexed { index, tag ->
            if (index != 0) {
                append(tagDivider)
            }
            withStyle(tagStyle) {
                append(" ${tag.uppercase(Locale.getDefault())}")
            }
        }
    }
    Text(
        text = text,
        modifier = modifier
    )

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostItem(
    post: Post,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier
            .clickable { }
            .padding(vertical = 8.dp),
        icon = {
            Image(
                painter = painterResource(id = post.imageThumbId),
                contentDescription = null
            )
        },
        text = {
            Text(text = post.title)
        },
        secondaryText = {
            PostMetadata(post = post)
        }

    )

}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
fun HomeAppPreview() {
    JetnewsTheme {
        HomeApp()
    }
}

@Preview
@Composable
fun AppBarPreview() {
    JetnewsTheme {
        AppBar()
    }

}

@Preview
@Composable
fun HeaderPreview() {
    JetnewsTheme {
        Header(text = "Header")
    }

}

@Preview(showBackground = true, name = "Featured Post  Dark")
@Composable
fun FeaturedPostPreview() {
    JetnewsTheme(darkTheme = true) {
        val post = remember {
            PostRepo.getFeaturedPost()
        }

        FeaturedPost(post = post)
    }
}

@Preview
@Composable
fun PostItemPreview() {
    JetnewsTheme() {
        val post = remember {
            PostRepo.getFeaturedPost()
        }
        PostItem(post = post)
    }

}





