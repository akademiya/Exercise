package com.vadym.adv.tfexercise

import android.view.View

fun Boolean.toAndroidVisibility() = if (this) View.VISIBLE else View.GONE