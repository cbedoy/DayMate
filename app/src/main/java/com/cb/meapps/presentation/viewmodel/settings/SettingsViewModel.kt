package com.cb.meapps.presentation.viewmodel.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cb.meapps.data.PreferencesDelegate
import com.cb.meapps.domain.fake.getFakeCards
import com.cb.meapps.domain.model.Card
import com.cb.meapps.domain.usecase.AddNewCardUseCase
import com.cb.meapps.domain.usecase.GetCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesDelegate: PreferencesDelegate,
    private val addNewCardUseCase: AddNewCardUseCase,
    private val getCardsUseCase: GetCardsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        SettingsState(
            initialSavings = preferencesDelegate.getInitialSavings(),
            annualInterestRate = preferencesDelegate.getAnnualInterestRate(),
            biweeklyPayment = preferencesDelegate.getBiweeklyPayment(),
            skipOnboarding = preferencesDelegate.isSkipOnboarding(),
            projectionDays = preferencesDelegate.getProjectionDays()
        )
    )
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getCardsUseCase().collect { cards ->
                _state.update { currentState ->
                    currentState.copy(cards = cards)
                }
            }
        }
    }

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
            is SettingsAction.ChangeProjectionDays -> {
                _state.update { currentState ->
                    currentState.copy(projectionDays = action.newValue)
                }
                preferencesDelegate.saveProjectionDays(action.newValue)
            }
            SettingsAction.SkipOnboarding -> {
                _state.update { currentState ->
                    currentState.copy(skipOnboarding = true)
                }
                preferencesDelegate.saveSkipOnboarding()
            }

            is SettingsAction.AddNewCard -> {
                viewModelScope.launch {
                    _state.update { currentState ->
                        currentState.copy(loading = true)
                    }
                    addNewCardUseCase(action.name, action.cutOffDate, action.dueDate, action.debt)
                }
            }
        }
    }
}

data class SettingsState(
    val initialSavings: String = "",
    val annualInterestRate: String = "",
    val biweeklyPayment: String = "",
    val projectionDays: String = "",
    val skipOnboarding: Boolean = false,
    val cards: List<Card> = getFakeCards(),
    val loading: Boolean = false
)

sealed class SettingsAction {
    data class ChangeInitialSavings(val newValue: String) : SettingsAction()
    data class ChangeAnnualInterestRate(val newValue: String) : SettingsAction()
    data class ChangeBiweeklyPayment(val newValue: String) : SettingsAction()
    data class ChangeProjectionDays(val newValue: String) : SettingsAction()
    data class AddNewCard(
        val name: String,
        val cutOffDate: Int,
        val dueDate: Int,
        val debt: Float
    ) : SettingsAction()
    data object SkipOnboarding : SettingsAction()
}