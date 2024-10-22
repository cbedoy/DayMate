package com.cb.meapps.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.cb.meapps.data.model.AdditionalPayment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

    fun saveProjectionDays(value: String) {
        getPreferences().edit().putString(PROJECTION_DAYS_COUNT_KEY, value).apply()
    }

    fun getProjectionDays(): String {
        return getPreferences().getString(PROJECTION_DAYS_COUNT_KEY, "") ?: ""
    }

    fun saveSkipOnboarding() {
        getPreferences().edit().putBoolean(SKIP_ONBOARDING, true).apply()
    }

    fun isSkipOnboarding(): Boolean {
        return getPreferences().getBoolean(SKIP_ONBOARDING, false)
    }

    fun getAdditionalPayments(): List<AdditionalPayment> {
        val gson = Gson()
        val json = getPreferences().getString(ADDITIONAL_PAYMENTS_KEY, null) ?: return emptyList()
        return try {
            val type = object : TypeToken<List<AdditionalPayment>>() {}.type
            gson.fromJson(json, type)
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun saveAdditionalPayments(additionalPayments: List<AdditionalPayment>) {
        val editor = getPreferences().edit()
        val gson = Gson()
        val json = gson.toJson(additionalPayments)

        editor.putString(ADDITIONAL_PAYMENTS_KEY, json)
        editor.apply()
    }

    companion object {
        private const val PREFERENCES_FILE_KEY = "com.cb.daymate"
        private const val INITIAL_SAVINGS_KEY = "initial_savings"
        private const val ANNUAL_INTEREST_RATE_KEY = "annual_interest_rate"
        private const val PROJECTION_DAYS_COUNT_KEY = "projection_days_count"
        private const val BIWEEKLY_PAYMENT_KEY = "biweekly_payment"
        private const val SKIP_ONBOARDING = "skip_onboarding"
        private const val ADDITIONAL_PAYMENTS_KEY = "additional_payments"

    }
}