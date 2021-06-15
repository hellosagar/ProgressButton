package dev.sagar.progress_button

import android.content.Context
import androidx.core.content.ContextCompat

fun Context.getColor(color: Int) = ContextCompat.getColor(
    this,
    color
)