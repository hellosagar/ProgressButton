package dev.sagar.progress_button_compose

import androidx.compose.ui.graphics.Color

/**
 * @param backgroundColor BackgroundColor of button
 * @param disabledColor button color when button is disabled
 * @param finishedColor button color when button is in finished state
 * @param contentColor color of the content inside button

 */
data class ProgressButtonColors(
    val backgroundColor: Color = DEFAULT_COLOR,
    val disabledColor: Color = DISABLED_COLOR,
    val finishedColor: Color = FINISHED_COLOR,
    val contentColor: Color = CONTENT_COLOR
)
