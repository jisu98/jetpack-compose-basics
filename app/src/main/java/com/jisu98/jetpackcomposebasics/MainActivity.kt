package com.jisu98.jetpackcomposebasics

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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

    Surface(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            GreetingCards()
        }
    }
}

@Composable
fun GreetingCards(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" },
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // top item decoration
        item { Spacer(modifier = Modifier) }

        items(names) { name ->
            GreetingCard(name = name)
        }

        // bottom item decoration
        item { Spacer(modifier = Modifier) }
    }
}

@Composable
fun GreetingCard(modifier: Modifier = Modifier, name: String) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary,
        ),
    ) {
        GreetingContents(name = name)
    }
}

@Composable
fun GreetingContents(
    modifier: Modifier = Modifier,
    name: String,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Row(modifier = modifier.padding(24.dp)) {
        GreetingText(
            modifier = Modifier.weight(1f),
            name = name,
            expanded = expanded,
        )
        ShowMoreIconButton(
            expanded = expanded,
            onClick = { expanded = !expanded },
        )
    }
}

@Composable
fun GreetingText(
    modifier: Modifier = Modifier,
    name: String,
    expanded: Boolean,
) {
    val extraPadding by animateDpAsState(
        targetValue = if (expanded) 24.dp else 0.dp,
        label = "extraPaddingDpAnimation",
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow,
        ),
    )

    Column(
        modifier = modifier.padding(
            bottom = extraPadding.coerceAtLeast(0.dp),
        ),
    ) {
        Text(text = stringResource(R.string.hello))
        Text(
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.SemiBold,
            ),
            text = name,
        )
    }
}

@Composable
fun ShowMoreIconButton(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            imageVector = if (expanded) {
                Icons.Filled.ArrowDropUp
            } else {
                Icons.Filled.ArrowDropDown
            },
            contentDescription = if (expanded) {
                stringResource(R.string.show_more)
            } else {
                stringResource(R.string.show_less)
            },
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "GreetingCardsPreviewDark",
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingCardsPreview() {
    JetpackComposeBasicsTheme {
        GreetingCards()
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

@Preview(
    showBackground = true,
    widthDp = 320,
    heightDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "OnboardingPreviewDark",
)
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    JetpackComposeBasicsTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}
