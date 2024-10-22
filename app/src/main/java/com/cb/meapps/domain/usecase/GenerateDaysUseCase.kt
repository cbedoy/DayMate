package com.cb.meapps.domain.usecase

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class GenerateDaysUseCase @Inject constructor() {
    operator fun invoke(): List<String> {
        val formatter = SimpleDateFormat("d MMMM", Locale.ENGLISH)
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, 2024)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DAY_OF_MONTH, 1)
        }

        val daysList = mutableListOf<String>()

        for (i in 0 until 365) {
            daysList.add(formatter.format(calendar.time))
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        return daysList
    }
}