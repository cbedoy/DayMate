package com.cb.meapps.presentation.viewmodel.analytics

import androidx.lifecycle.ViewModel
import com.cb.meapps.domain.analytics.DayMateAction
import com.cb.meapps.domain.analytics.DayMateAnalytics
import com.cb.meapps.domain.analytics.DayMateState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val dayMateAnalytics: DayMateAnalytics
) : ViewModel() {
    fun dispatch(action: AnalyticsAction) {
        when (action) {
            is AnalyticsAction.TrackAction -> dayMateAnalytics.track(action.state)
            is AnalyticsAction.TrackState -> dayMateAnalytics.track(action.state)
        }
    }
}

sealed class AnalyticsAction {
    data class TrackState(val state: DayMateState) : AnalyticsAction()
    data class TrackAction(val state: DayMateAction) : AnalyticsAction()
}
