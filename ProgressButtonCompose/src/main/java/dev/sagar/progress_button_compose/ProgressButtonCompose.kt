package dev.sagar.progress_button_compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * @param buttonState Current State of Button. eg.LOADING, FINISHED
 * @param text Text shown on Button
 * @param completedText Text shown when button state is FINISHED
 * @param modifier Modifier to modify the button
 * @param shape Button Shape
 * @param progressBarStrokeWidth Stroke Width of the progress bar shown in the button
 * @param progressButtonColors A data class which contains the colors of the button
 * @param progressButtonElevation A data class which contains the elevation values of button
 * @param textStyle TextStyle to style the text shown in button
 * @param onClick lambda functions which defines the action to be done on button press
 */
@Composable
fun ProgressButton(
    buttonState: ButtonState,
    text: String,
    completedText: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    progressBarStrokeWidth: Dp = 3.dp,
    progressButtonColors: ProgressButtonColors,
    progressButtonElevation: ProgressButtonElevation = ProgressButtonElevation(),
    textStyle: TextStyle = defaultTextStyle,
    onClick: () -> Unit
) {

    val btnBackgroundColor = when (buttonState) {
        ButtonState.LOADING -> progressButtonColors.disabledColor
        ButtonState.ENABLED -> progressButtonColors.backgroundColor
        ButtonState.DISABLED -> progressButtonColors.disabledColor
        ButtonState.FINISHED -> progressButtonColors.finishedColor
    }
    val isEnabled = buttonState == ButtonState.ENABLED
    val btnColorDisabled =
        if (buttonState == ButtonState.FINISHED) progressButtonColors.finishedColor
        else progressButtonColors.disabledColor
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = isEnabled,
        shape = shape,
        elevation = ButtonDefaults.elevation(
            defaultElevation = progressButtonElevation.defaultElevation,
            pressedElevation = progressButtonElevation.elevationOnPress
        ),
        contentPadding = PaddingValues(16.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = btnBackgroundColor,
            contentColor = progressButtonColors.contentColor,
            disabledBackgroundColor = btnColorDisabled
        )
    ) {
        when (buttonState) {
            ButtonState.LOADING -> CircularProgressIndicator(
                color = progressButtonColors.contentColor,
                modifier = Modifier.size(24.dp),
                strokeWidth = progressBarStrokeWidth
            )
            ButtonState.FINISHED -> Text(text = completedText, style = textStyle)
            else -> Text(text = text, style = textStyle)
        }
    }
}


//@Preview
//@Composable
//fun ButtonLoadingPreview() {
//    ProgressButton(
//        buttonState = ButtonState.LOADING,
//        text = "Login",
//        completedText = "Success",
//        modifier = Modifier.fillMaxWidth()
//    ) {
//
//    }
//}
//
//@Preview
//@Composable
//fun ButtonEnabledPreview() {
//    ProgressButton(
//        buttonState = ButtonState.ENABLED,
//        text = "Login",
//        completedText = "Success",
//        modifier = Modifier.fillMaxWidth()
//    ) {
//
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ButtonDisabledPreview() {
//    ProgressButton(
//        buttonState = ButtonState.DISABLED,
//        text = "Login",
//        completedText = "Success",
//        modifier = Modifier.fillMaxWidth()
//    ) {
//
//    }
//}
//
//@Preview
//@Composable
//fun ButtonFinishedPreview() {
//    ProgressButton(
//        buttonState = ButtonState.FINISHED,
//        text = "Login",
//        completedText = "Success",
//        modifier = Modifier.fillMaxWidth()
//    ) {
//
//    }
//}


