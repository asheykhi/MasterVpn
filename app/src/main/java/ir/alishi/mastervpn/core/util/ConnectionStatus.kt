package ir.alishi.mastervpn.core.util

sealed class ConnectionStatus<T>(val exception:Exception? = null) {
     class OK<T> : ConnectionStatus<T>()
     class Error<T>(exception: Exception) : ConnectionStatus<T>(exception)
}