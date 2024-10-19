package com.cb.meapps.domain.usecase

import com.cb.meapps.domain.model.DateInfo
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class GetCardCalendarUseCase @Inject constructor() {
    operator fun invoke(days: Int): List<DateInfo> {
        return  generateNextDays(days)
    }

    private fun generateNextDays(days: Int): List<DateInfo> {
        val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
        val monthFormat = SimpleDateFormat("MMMM", Locale.getDefault())
        val currentDate = Calendar.getInstance()
        val dates = mutableListOf<DateInfo>()

        for (i in 0 until days) {
            val dateString = dateFormat.format(currentDate.time).uppercase()
            val day = currentDate.get(Calendar.DAY_OF_MONTH)
            val month = currentDate.get(Calendar.MONTH) + 1
            val monthName = monthFormat.format(currentDate.time)
            val year = currentDate.get(Calendar.YEAR)

            dates.add(
                DateInfo(
                    date = dateString,
                    day = day,
                    month = month,
                    monthName = monthName,
                    year = year
                )
            )
            currentDate.add(Calendar.DAY_OF_YEAR, 1)
        }

        return dates
    }
}