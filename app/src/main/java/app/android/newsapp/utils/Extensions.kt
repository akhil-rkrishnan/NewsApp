package app.android.newsapp.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.input.TextFieldValue
import app.android.newsapp.R

private val osProperty: String = System.getProperty("os.name", "unknown") as String
private val isMac = osProperty.startsWith("mac", ignoreCase = true)

/**
 * Method to persist the shortkey input
 * @param input [TextFieldValue] text value of the text filed
 * @return modifier [Modifier]
 **/
@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.persistShortKeyForInput(input: TextFieldValue): Modifier {
    return onPreviewKeyEvent { event ->
        // Returns true to mark the event as handled and stop its propagation.
        when {
            // Temporary fix for https://github.com/JetBrains/compose-jb/issues/565.
            // To repro, press CTRL/Option+backspace on an empty TextField.
            event.isCtrlBackspace() && input.text.isEmpty() -> true
            // Temporary fix for https://github.com/JetBrains/compose-jb/issues/2023.
            // To repro, simply press the DEL key at the end of the text.
            event.key == Key.Delete && input.isCursorAtTheEnd() -> true
            else -> false
        }
    }
}

/**
 * Method to check whether control backspace is clicked
 * @return boolean
 **/
@OptIn(ExperimentalComposeUiApi::class)
private fun KeyEvent.isCtrlBackspace() =
    (key == Key.Backspace || key == Key.Enter || key == Key.Delete
            || key == Key.DirectionUp || key == Key.DirectionDown
            || key == Key.DirectionLeft || key == Key.DirectionRight) && ((isCtrlPressed || isAltPressed) || (isMac && isAltPressed))


/**
 * Method to check whether the cursor is at the end
 * @return key [KeyEvent]
 **/
private fun TextFieldValue.isCursorAtTheEnd(): Boolean {
    val hasNoSelection = selection.collapsed
    val isCursorAtTheEnd = text.length == selection.end
    return hasNoSelection && isCursorAtTheEnd
}

/**
 * Method to show toast for UiText
 * @param message message to show
 * @param longToast to specify the duration of toast
 **/
fun Context.showToast(message: String, longToast: Boolean = false) {
    Toast.makeText(this, message, if (longToast) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}

/**
 * Method to show toast for UiText
 * @param message [UiText] UiText object for the message
 * @param longToast to specify the duration of toast
 **/
fun Context.showToast(message: UiText?, longToast: Boolean = false) {
    val toastDuration = if (longToast) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    when (message) {
        is UiText.DynamicString -> {
            Toast.makeText(this, message.message, toastDuration).show()
        }
        is UiText.StringResId -> {
            if (message.resID != null) {
                Toast.makeText(this, getString(message.resID), toastDuration).show()
            } else {
                Toast.makeText(this, getString(R.string.invalidMessage), toastDuration).show()
            }
        }
        else -> {
            Toast.makeText(this, getString(R.string.invalidMessage), toastDuration).show()
        }
    }
}