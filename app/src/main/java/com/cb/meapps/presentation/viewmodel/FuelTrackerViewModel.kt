package com.cb.meapps.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cb.meapps.data.model.FuelTrackerEntity
import com.cb.meapps.data.repository.FuelTrackerRepository
import com.cb.meapps.domain.fake.getFakeTracks
import com.cb.meapps.domain.model.FuelTracker
import com.cb.meapps.domain.model.SummaryFullTracker
import com.cb.meapps.presentation.ui.common.resiliency.ErrorRepresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FuelTrackerViewModel @Inject constructor(
    private val fuelTrackerRepository: FuelTrackerRepository
) : ViewModel() {
    private val _state = MutableStateFlow(FuelTrackerState())
    val state: StateFlow<FuelTrackerState> = _state.asStateFlow()

    init {

        val fakeTracks = getFakeTracks()
        val summaryFullTracker = SummaryFullTracker(
            totalKM = fakeTracks.map { it.totalKM }.sum(),
            totalLiters = fakeTracks.map { it.liters }.sum(),
            totalPrice = fakeTracks.map { it.totalPrice }.sum()
        )

        _state.update {
            it.copy(
                tracks = fakeTracks,
                summaryFullTracker = summaryFullTracker
            )
        }
    }

    fun dispatch(
        action: FuelTrackerAction
    ) = viewModelScope.launch(Dispatchers.IO) {
        when(action) {
            is FuelTrackerAction.LoadFuelTracker -> {
                val fuelTrackerEntity = fuelTrackerRepository.get(action.trackerId)

                if (fuelTrackerEntity == null) {
                    _state.update { currentState ->
                        currentState.copy(
                            error = ErrorRepresentation.GeneralError,
                            currentFuelTracker = null
                        )
                    }
                    return@launch
                }

                _state.update { currentState ->
                    currentState.copy(
                        currentFuelTracker = FuelTracker(
                            id = fuelTrackerEntity.id,
                            totalKM = fuelTrackerEntity.totalKM,
                            totalPrice = fuelTrackerEntity.totalPrice,
                            liters = fuelTrackerEntity.liters
                        )
                    )
                }
            }
            is FuelTrackerAction.OnCommitChanges -> {
                val currentFuelTracker = _state.value.currentFuelTracker ?: return@launch

                fuelTrackerRepository.save(
                    FuelTrackerEntity(
                        id = currentFuelTracker.id,
                        totalKM = action.kilometers,
                        totalPrice = currentFuelTracker.totalPrice,
                        liters = currentFuelTracker.liters
                    )
                )
            }
        }
    }
}

data class FuelTrackerState(
    val tracks: List<FuelTracker> = emptyList(),
    val summaryFullTracker: SummaryFullTracker ?= null,
    val currentFuelTracker: FuelTracker? = null,
    val error: ErrorRepresentation? = null
)

sealed class FuelTrackerAction {
    data class OnCommitChanges(
        val kilometers: Float,
        val totalPrice: Float,
        val liters: Float,
    ) : FuelTrackerAction()

    data class LoadFuelTracker(val trackerId: Int): FuelTrackerAction()
}