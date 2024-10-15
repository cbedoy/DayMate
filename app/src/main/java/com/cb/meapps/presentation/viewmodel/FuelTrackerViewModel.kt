package com.cb.meapps.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.cb.meapps.domain.fake.getFakeTracks
import com.cb.meapps.domain.model.FuelTracker
import com.cb.meapps.domain.model.SummaryFullTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FuelTrackerViewModel @Inject constructor() : ViewModel() {
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
}

data class FuelTrackerState(
    val tracks: List<FuelTracker> = emptyList(),
    val summaryFullTracker: SummaryFullTracker ?= null
)