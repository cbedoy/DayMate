package com.cb.meapps.presentation.ui.common.resiliency

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

enum class ErrorRepresentation {
    GeneralError
}

@Composable
fun HandleResiliency(
    snackbarHostState: SnackbarHostState,
    errorRepresentation: ErrorRepresentation
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(errorRepresentation) {
        scope.launch {
            snackbarHostState.showSnackbar(errorRepresentation.getMessage())
        }
    }
}

fun ErrorRepresentation.getMessage() : String {
    return when(this) {
        ErrorRepresentation.GeneralError -> {
            "Oopsie! That didn’t go as planned. How about a quick retry?"
        }
    }
}