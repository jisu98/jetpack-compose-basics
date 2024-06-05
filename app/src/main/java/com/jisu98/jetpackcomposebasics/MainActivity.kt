package com.jisu98.jetpackcomposebasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jisu98.jetpackcomposebasics.ui.theme.JetpackComposeBasicsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeBasicsTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier = modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Greetings()
        }
    }
}

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" },
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
    ) {
        LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
            items(names) { name ->
                Greeting(name)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        targetValue = if (expanded) 24.dp else 0.dp,
        label = "extraPaddingDpAnimation",
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow,
        ),
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        color = MaterialTheme.colorScheme.primary,
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp)),
            ) {
                Text(text = "Hello")
                Text(text = "$name!")
            }
            ElevatedButton(
                onClick = { expanded = !expanded },
            ) {
                Text(
                    text = if (expanded) {
                        "Show less"
                    } else {
                        "Show more"
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview() {
    JetpackComposeBasicsTheme {
        MyApp()
    }
}

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onContinueClicked: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked,
        ) {
            Text("Continue")
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    JetpackComposeBasicsTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}
