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
import com.cb.meapps.presentation.ui.theme.MeAppsTheme
import com.cb.meapps.presentation.viewmodel.BureaucraticDocsViewModel
import com.cb.meapps.presentation.viewmodel.FuelTrackerViewModel
import com.cb.meapps.presentation.viewmodel.financial.ProjectionsViewModel
import com.cb.meapps.presentation.viewmodel.settings.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()
    private val bureaucraticDocsViewModel: BureaucraticDocsViewModel by viewModels()
    private val projectionsViewModel: ProjectionsViewModel by viewModels()
    private val fuelTrackerViewModel: FuelTrackerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeAppsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val settingsState by settingsViewModel.state.collectAsState()
                    val documentsState by bureaucraticDocsViewModel.state.collectAsState()
                    val financialProjectionState by projectionsViewModel.state.collectAsState()
                    val fuelTrackerState by fuelTrackerViewModel.state.collectAsState()

                    DayMateContainer(
                        settingsState,
                        documentsState,
                        financialProjectionState,
                        fuelTrackerState,
                        onProjectionsAction = projectionsViewModel::dispatch,
                        onFuelTrackerAction = fuelTrackerViewModel::dispatch,
                        onSettingsAction = settingsViewModel::dispatch
                    )
                }
            }
        }
    }
}
