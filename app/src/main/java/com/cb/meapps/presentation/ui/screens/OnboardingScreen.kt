package com.cb.meapps.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onCompleted: () -> Unit
) {
    val pages = buildPages()

    val pagerState = rememberPagerState {
        pages.size
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            OnboardingPage(pages[page])
        }
        // Navigation controls
        OnboardingNavControl(pages, pagerState, onCompleted)
    }
}

@Composable
private fun OnboardingNavControl(
    pages: List<OnboardingPage>,
    pagerState: PagerState,
    onCompleted: () -> Unit
) {
    val rememberCoroutineScope = rememberCoroutineScope()
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (pagerState.currentPage < pages.lastIndex) {
            Button(
                onClick = {
                    rememberCoroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            ) {
                Text("Next")
            }
        } else {
            Button(
                onClick = onCompleted
            ) {
                Text("Finish")
            }
        }
        OutlinedButton(onClick = onCompleted) {
            Text(text = "Skip")
        }
    }
}

@Composable
private fun OnboardingPage(page: OnboardingPage) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Text(text = page.title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Black)
            Text(text = page.subtitle, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Light)
        }
    }
}

private data class OnboardingPage(
    val title: String,
    val subtitle: String
)

private fun buildPages(): List<OnboardingPage> {
    return listOf(
        OnboardingPage(
            "Documents at Hand",
            "Easily store and access the documents you need for daily tasks, making sure you're never caught unprepared."
        ),
        OnboardingPage(
            "Financial Projections",
            "Gain insights into your financial future with detailed projections, keeping track of your current payments, savings, and expected financial status."
        ),
        OnboardingPage(
            "Task Management",
            "Organize and manage your daily tasks effectively, ensuring everything gets done on time."
        ),
        OnboardingPage(
            "Settings & Customization",
            "Tailor the app to fit your specific needs and personal preferences."
        )
    )
}

@Preview
@Composable
private fun PreviewOnboardingPage() {
    Surface {
        OnboardingPage(buildPages().first())
    }
}

@Preview
@Composable
private fun PreviewOnboardingScreen() {
    Surface {
        OnboardingScreen {

        }
    }
}