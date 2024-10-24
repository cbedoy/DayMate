package com.cb.meapps.presentation.viewmodel.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cb.meapps.domain.model.AdditionalPayment
import com.cb.meapps.data.repository.PreferencesDelegate
import com.cb.meapps.domain.fake.getFakeCards
import com.cb.meapps.domain.model.Card
import com.cb.meapps.domain.usecase.AddNewCardUseCase
import com.cb.meapps.domain.usecase.DeleteAdditionalPaymentUseCase
import com.cb.meapps.domain.usecase.GenerateDaysUseCase
import com.cb.meapps.domain.usecase.GetAdditionalPaymentsUseCase
import com.cb.meapps.domain.usecase.GetCardsUseCase
import com.cb.meapps.domain.usecase.SaveAdditionalPaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesDelegate: PreferencesDelegate,
    private val addNewCardUseCase: AddNewCardUseCase,
    private val getCardsUseCase: GetCardsUseCase,
    private val getAdditionalPaymentsUseCase: GetAdditionalPaymentsUseCase,
    private val generateDaysUseCase: GenerateDaysUseCase,
    private val saveAdditionalPaymentUseCase: SaveAdditionalPaymentUseCase,
    private val deleteAdditionalPaymentUseCase: DeleteAdditionalPaymentUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        SettingsState(
            initialSavings = preferencesDelegate.getInitialSavings(),
            annualInterestRate = preferencesDelegate.getAnnualInterestRate(),
            biweeklyPayment = preferencesDelegate.getBiweeklyPayment(),
            skipOnboarding = preferencesDelegate.isSkipOnboarding(),
            projectionDays = preferencesDelegate.getProjectionDays(),
            days = generateDaysUseCase.invoke()
        )
    )
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val getCardsFlow = getCardsUseCase()
            val getAdditionalPaymentFlow = getAdditionalPaymentsUseCase()

            getCardsFlow.combine(getAdditionalPaymentFlow) { cards, additionalPayments ->
                _state.update { currentState ->
                    currentState.copy(
                        cards = cards,
                        additionalPayments = additionalPayments
                    )
                }
            }.launchIn(viewModelScope)
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
            is SettingsAction.SaveAdditionalPayment -> {
                viewModelScope.launch(Dispatchers.IO) {
                    saveAdditionalPaymentUseCase(action.name, action.day, action.value)
                }
            }
            is SettingsAction.DeleteAdditionalPayment -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteAdditionalPaymentUseCase(action.name)
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
    val loading: Boolean = false,
    val days: List<String> = emptyList(),
    val additionalPayments: List<AdditionalPayment> = emptyList()
)

sealed class SettingsAction {
    data class ChangeInitialSavings(val newValue: String) : SettingsAction()
    data class ChangeAnnualInterestRate(val newValue: String) : SettingsAction()
    data class ChangeBiweeklyPayment(val newValue: String) : SettingsAction()
    data class ChangeProjectionDays(val newValue: String) : SettingsAction()

    data class SaveAdditionalPayment(val name: String, val day: Int, val value: Float) : SettingsAction()
    data class DeleteAdditionalPayment(val name: String): SettingsAction()
    data class AddNewCard(
        val name: String,
        val cutOffDate: Int,
        val dueDate: Int,
        val debt: Float
    ) : SettingsAction()
    data object SkipOnboarding : SettingsAction()
}