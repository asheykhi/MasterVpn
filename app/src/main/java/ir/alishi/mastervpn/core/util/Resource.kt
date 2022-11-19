package ir.alishi.mastervpn.core.util

sealed class ResourceFlow<T>(val message: String? = null) {
    class Loading<T>() :ResourceFlow<T>()
    class Error<T>(message: String) : ResourceFlow<T>(message)
}