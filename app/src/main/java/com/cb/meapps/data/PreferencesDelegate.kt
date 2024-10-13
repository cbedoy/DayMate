package com.cb.meapps.data

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesDelegate @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private fun getPreferences(): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
    }

    fun getInitialSavings(): String {
        return getPreferences().getString(INITIAL_SAVINGS_KEY, "") ?: ""
    }

    fun saveInitialSavings(value: String) {
        getPreferences().edit().putString(INITIAL_SAVINGS_KEY, value).apply()
    }

    fun getAnnualInterestRate(): String {
        return getPreferences().getString(ANNUAL_INTEREST_RATE_KEY, "") ?: ""
    }

    fun saveAnnualInterestRate(value: String) {
        getPreferences().edit().putString(ANNUAL_INTEREST_RATE_KEY, value).apply()
    }

    fun getBiweeklyPayment(): String {
        return getPreferences().getString(BIWEEKLY_PAYMENT_KEY, "") ?: ""
    }

    fun saveBiweeklyPayment(value: String) {
        getPreferences().edit().putString(BIWEEKLY_PAYMENT_KEY, value).apply()
    }

    companion object {
        private const val PREFERENCES_FILE_KEY = "com.cb.daymate"
        private const val INITIAL_SAVINGS_KEY = "initial_savings"
        private const val ANNUAL_INTEREST_RATE_KEY = "annual_interest_rate"
        private const val BIWEEKLY_PAYMENT_KEY = "biweekly_payment"

    }
}