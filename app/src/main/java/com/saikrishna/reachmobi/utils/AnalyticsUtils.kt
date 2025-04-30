package com.saikrishna.reachmobi.utils

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AnalyticsUtils @Inject constructor(private val firebaseAnalytics: FirebaseAnalytics) {
    fun logEvent(eventName: String, bundle: Bundle? = null) {
        firebaseAnalytics.logEvent(eventName, bundle)
    }
}