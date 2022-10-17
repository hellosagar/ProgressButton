package dev.sagar.progressbutton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.* // ktlint-disable no-wildcard-imports
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.sagar.progress_button_compose.ButtonState
import dev.sagar.progress_button_compose.ProgressButton
import dev.sagar.progress_button_compose.ProgressButtonColors

class ComposeActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = Color.White) {
                MainScreenContent()
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
@Preview
fun MainScreenContent() {

    var buttonState by remember {
        mutableStateOf(ButtonState.DISABLED)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProgressButton(
            buttonState = buttonState,
            text = stringResource(R.string.button),
            completedText = stringResource(R.string.finished),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth(),
            progressButtonColors = ProgressButtonColors()
        ) { }

        Spacer(modifier = Modifier.height(100.dp))

        ControlButton(text = stringResource(R.string.enable)) {
            buttonState = ButtonState.ENABLED
        }
        Spacer(modifier = Modifier.height(16.dp))
        ControlButton(text = stringResource(R.string.disable)) {
            buttonState = ButtonState.DISABLED
        }
        Spacer(modifier = Modifier.height(16.dp))
        ControlButton(text = stringResource(R.string.loading)) {
            buttonState = ButtonState.LOADING
        }
        Spacer(modifier = Modifier.height(16.dp))
        ControlButton(text = stringResource(R.string.finished)) {
            buttonState = ButtonState.FINISHED
        }
    }
}

@Composable
fun ControlButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick, contentPadding = PaddingValues(horizontal = 16.dp)) {
        Text(text = text, color = Color.White)
    }
}
