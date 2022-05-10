package kz.kuanysh.newsapiapp2.extension

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Failure(val exception: Exception) : Result<Nothing>()
}

inline fun <T : Any> apiCall(call: () -> T) =
    try {
        Result.Success(call())
    } catch (e: Exception) {
        Result.Failure(e)
    }