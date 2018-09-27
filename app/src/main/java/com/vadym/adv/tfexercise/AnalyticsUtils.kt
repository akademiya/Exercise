package com.vadym.adv.tfexercise

import android.content.Context
import com.google.android.gms.analytics.Tracker

/**
 * Gets the default [Tracker] for this [Application].
 */
@Synchronized
fun Context.tracker(): Tracker = (this.applicationContext as AndroidApplication).getDefaultTracker()