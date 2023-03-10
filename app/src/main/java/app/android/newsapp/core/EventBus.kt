package app.android.newsapp.core

import app.android.newsapp.utils.UiText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Event bus to send all the events required for whole application
 */
class EventBus {

    // Variable to hold the toast channel flow
    private val _toastChannel = Channel<UiText?>()
    val toastFlow get() = _toastChannel.receiveAsFlow()

    private val scope = CoroutineScope(Job())

    /**
     * Method to send toast message
     * @param message Ui text for toast
     */
    fun sendToast(message: UiText?) {
        scope.launch {
            _toastChannel.send(message)
        }
    }

}