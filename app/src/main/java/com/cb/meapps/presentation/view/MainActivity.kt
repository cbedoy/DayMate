package com.cb.meapps.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.cb.meapps.presentation.ui.DayMateContainer
import com.cb.meapps.presentation.viewmodel.settings.SettingsViewModel
import com.cb.meapps.presentation.ui.theme.MeAppsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeAppsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val settingsState by settingsViewModel.state.collectAsState()


                    DayMateContainer(
                        settingsState,
                        onSettingsAction = { settingsViewModel::dispatch }
                    )
                }
            }
        }
    }
}
