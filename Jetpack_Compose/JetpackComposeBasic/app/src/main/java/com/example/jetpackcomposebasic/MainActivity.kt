package com.example.jetpackcomposebasic

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposebasic.ui.theme.JetpackComposeBasicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeBasicTheme() {
                MyApp()
            }



        }
    }
}

@Composable
fun MyApp() {
    var shouldShowOnboarding by rememberSaveable {
        mutableStateOf(true)
    }

    if (shouldShowOnboarding) {
        OnboardingScreen() {
            shouldShowOnboarding = false
        }
    } else {
        Greetings()
    }

}

@Composable
private fun Greetings(names: List<String> = List(1000) { "$it" }) {
    Surface(
        color = MaterialTheme.colors.background
    ) {
        LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
            items(items = names){ name ->
                Greeting(name= name)
            }
        }
    }

}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {

    Surface() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(text = "Welcome to the Basics Codelab")
            
            Button(
                onClick = { onContinueClicked() },
                modifier = Modifier.padding(vertical = 24.dp)
                ) {
                Text(text = "Continue")
            }
        }

    }

}





@Composable
fun Greeting(name: String) {

    var expand = remember{mutableStateOf(false)}

    //var extraPadding =if (expand.value) 48.dp else 0.dp
    val extraPadding by animateDpAsState(
        if (expand.value) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio= Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello,")
                Text(text = name, style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                ))

            }
            IconButton(onClick = { expand.value = !expand.value }) {
                Icon(
                    imageVector = if (expand.value) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expand.value) stringResource(R.string.show_less) else stringResource(
                                            R.string.show_more)
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    JetpackComposeBasicTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    widthDp = 320,
    name = "DefaultPreviewDark"
)
@Composable
fun DefaultPreview() {
    JetpackComposeBasicTheme {
        MyApp()
    }
}