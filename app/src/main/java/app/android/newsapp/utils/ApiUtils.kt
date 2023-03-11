package app.android.newsapp.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

/**
 * Method to execute the Network call.
 * @param block lambda defined for the user api call block
 * @return ApiResult<Type> The response body / exception
 */
suspend fun <M> initApiCall(block: suspend () -> ApiResult<M>): ApiResult<M> =
    withContext(Dispatchers.IO) {
        try {
            block()
        } catch (ex: Exception) {
            var errorBody: ErrorBody? = null
            if (ex is HttpException) {
                errorBody = ex.response().asErrorBody()
            }
            ApiResult.Failed(UiText.DynamicString(ex.message), errorBody)
        }
    }

sealed class ApiResult<out Model> {
    object None : ApiResult<Nothing>()
    data class Success<out Model>(val data: Model) : ApiResult<Model>()
    data class Failed(val uiText: UiText?, val errorBody: ErrorBody?) : ApiResult<Nothing>()
}

inline fun <reified Model> ApiResult<Model>.ifSuccess(block: (data: Model) -> Unit) {
    if (this is ApiResult.Success) {
        block(data)
    }
}

inline fun <reified Model> ApiResult<Model>.ifFailed(cause: (message: UiText?, errorBody: ErrorBody?) -> Unit) {
    if (this is ApiResult.Failed) {
        cause(uiText, errorBody)
    }
}


fun Response<*>?.asErrorBody(): ErrorBody? {
    return if (this == null) {
        null
    } else {
        errorBody()?.string()?.let {
            it.fromJson(ErrorBody::class.java)
        }
    }
}

data class ErrorBody(
    val status: String = "Status not available",
    val code: String = "Code not available",
    val message: String = "Message not available"
)
