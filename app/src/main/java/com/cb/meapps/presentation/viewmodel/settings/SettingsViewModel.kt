package com.cb.meapps.presentation.viewmodel.settings

import androidx.lifecycle.ViewModel
import com.cb.meapps.data.PreferencesDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesDelegate: PreferencesDelegate
) : ViewModel() {

    private val _state = MutableStateFlow(
        SettingsState(
            initialSavings = preferencesDelegate.getInitialSavings(),
            annualInterestRate = preferencesDelegate.getAnnualInterestRate(),
            biweeklyPayment = preferencesDelegate.getBiweeklyPayment()
        )
    )
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    fun dispatch(action: SettingsAction) {
        when (action) {
            is SettingsAction.ChangeInitialSavings -> {
                _state.update { currentState ->
                    currentState.copy(initialSavings = action.newValue)
                }
                preferencesDelegate.saveInitialSavings(action.newValue)
            }
            is SettingsAction.ChangeAnnualInterestRate -> {
                _state.update { currentState ->
                    currentState.copy(annualInterestRate = action.newValue)
                }
                preferencesDelegate.saveAnnualInterestRate(action.newValue)
            }
            is SettingsAction.ChangeBiweeklyPayment -> {
                _state.update { currentState ->
                    currentState.copy(biweeklyPayment = action.newValue)
                }
                preferencesDelegate.saveBiweeklyPayment(action.newValue)
            }
        }
    }
}

data class SettingsState(
    val initialSavings: String = "",
    val annualInterestRate: String = "",
    val biweeklyPayment: String = ""
)

sealed class SettingsAction {
    data class ChangeInitialSavings(val newValue: String) : SettingsAction()
    data class ChangeAnnualInterestRate(val newValue: String) : SettingsAction()
    data class ChangeBiweeklyPayment(val newValue: String) : SettingsAction()
}