@file:OptIn(ExperimentalMaterial3Api::class)

package com.cb.meapps.presentation.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.cb.meapps.presentation.ui.common.preview.SupportedDevicesPreview

@Composable
fun CommonTopAppBar(title: String) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            BodyMedium(title)
        }
    )
}

@SupportedDevicesPreview
@Composable
private fun PreviewCommonTopAppBar() {
    Surface {
        Column {
            CommonTopAppBar("DayMate")
        }
    }
}