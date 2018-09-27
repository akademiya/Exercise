package com.vadym.adv.tfexercise

import android.app.Application
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.Tracker

class AndroidApplication : Application() {

    private lateinit var analytics: GoogleAnalytics
    private lateinit var tracker: Tracker

    override fun onCreate() {
        super.onCreate()
        analytics = GoogleAnalytics.getInstance(this)
        tracker = analytics.newTracker(R.xml.global_tracker)
    }

    @Synchronized
    fun getDefaultTracker(): Tracker {
        return tracker
    }

}