package com.cb.meapps.domain.analytics

import javax.inject.Inject

class DayMateAnalytics @Inject constructor() {

    fun track(state: DayMateState) {
        println("Track state = $state")
    }

    fun track(action: DayMateAction) {
        println("Track action = $action")
    }

}

sealed class DayMateState {
    data class Screen(val name: String): DayMateState()
}

sealed class DayMateAction {

}