package dev.sagar.progress_button_compose

import androidx.compose.ui.unit.Dp

/**
 * @param defaultElevation Default elevation of button
 * @param elevationOnPress elevation of button when it is being pressed
 */
data class ProgressButtonElevation(
    val defaultElevation: Dp = DEFAULT_ELEVATION,
    val elevationOnPress: Dp = DEFAULT_ELEVATION_ON_PRESS
)
