package app.android.newsapp.utils

/**
 * Sealed class for UiText
 */
sealed class UiText {
    data class DynamicString(val message: String?) : UiText()
    data class StringResId(val resID: Int?) : UiText()
}