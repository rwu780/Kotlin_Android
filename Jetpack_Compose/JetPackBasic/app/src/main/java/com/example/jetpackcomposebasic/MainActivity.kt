package com.example.jetpackcomposebasic

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposebasic.ui.theme.JetpackComposeBasicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeBasicTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true)}

    if(shouldShowOnboarding){
        OnboardingScreen(onContinuedClicked = { shouldShowOnboarding = false } )
    } else {
        Greetings()
    }

}

@Composable
fun Greetings(names: List<String> = List(1000){ "$it"}) {

    Surface( color = MaterialTheme.colors.background) {
        Column(modifier = Modifier.padding(vertical = 4.dp)) {
            LazyColumn(){
                items(names) {name -> Greeting(name)}
            }
        }
    }
    
}

@Composable
fun Greeting(name: String) {
    var expanded = remember { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        targetValue = if(expanded.value) 48.dp else 0.dp,
        animationSpec = tween(durationMillis = 500)

    )

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello,")
                Text(text = name)
            }
            OutlinedButton(onClick = { expanded.value = !expanded.value }) {
                Text(if (expanded.value) stringResource(R.string.show_less) else stringResource(R.string.show_more))
            }
        }
    }
}


@Composable
fun OnboardingScreen(
    onContinuedClicked :() -> Unit
) {



    Surface {

        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "Welcome to the Basics Codelab!")
            Button(onClick = onContinuedClicked, modifier = Modifier.padding(vertical = 24.dp)) {
                Text(text = "Continue")
            }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, widthDp = 320, heightDp = 320)
@Preview(widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    JetpackComposeBasicTheme {
        OnboardingScreen(onContinuedClicked = {})
    }
    
}

@Preview(showBackground = true)
@Preview(
showBackground = true,
widthDp = 320,
uiMode = UI_MODE_NIGHT_YES,
name = "DefaultPreviewDark")
@Composable
fun DefaultPreview() {
    JetpackComposeBasicTheme {
        MyApp()
    }
}