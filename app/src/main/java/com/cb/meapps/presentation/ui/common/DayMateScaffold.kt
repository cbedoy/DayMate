package com.cb.meapps.presentation.ui.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun DayMateScaffold(
    title: String,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            CommonTopAppBar(title = title)
        },
        bottomBar = {
            CreditView()
        },
        floatingActionButton = floatingActionButton,
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        content = content
    )
}