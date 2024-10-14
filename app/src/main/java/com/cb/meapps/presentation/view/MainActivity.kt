package com.cb.meapps.presentation.view

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import com.cb.meapps.presentation.ui.DayMateContainer
import com.cb.meapps.presentation.ui.theme.MeAppsTheme
import com.cb.meapps.presentation.viewmodel.BureaucraticDocsViewModel
import com.cb.meapps.presentation.viewmodel.settings.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()
    private val bureaucraticDocsViewModel: BureaucraticDocsViewModel by viewModels()

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
                    val context = LocalContext.current

                    DayMateContainer(
                        settingsState,
                        documentsState,
                        onCreditClicked = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/carlos-cervantes-bedoy-34248187/"))
                            context.startActivity(intent)
                        },
                        onSettingsAction = {
                            settingsViewModel.dispatch(it)
                        }
                    )
                }
            }
        }
    }
}
