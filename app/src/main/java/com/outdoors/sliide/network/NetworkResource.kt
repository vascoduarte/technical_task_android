package com.outdoors.sliide.network

data class NetworkResource<out T>(val status: NetworkStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): NetworkResource<T> = NetworkResource(status = NetworkStatus.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): NetworkResource<T> =
            NetworkResource(status = NetworkStatus.ERROR, data = data, message = message)

        fun <T> loading(data: T?): NetworkResource<T> = NetworkResource(status = NetworkStatus.LOADING, data = data, message = null)
    }
}