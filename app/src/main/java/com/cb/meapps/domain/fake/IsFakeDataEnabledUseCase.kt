package com.cb.meapps.domain.fake

import javax.inject.Inject

class IsFakeDataEnabledUseCase @Inject constructor() {
    operator fun invoke() = true
}