package andrzej.calendar.utils

sealed class DataState<out R> {

    data class Success<out T>(val data: T): DataState<T>()
    data class FirstUse(val exception: Exception): DataState<Nothing>()
    object Loading: DataState<Nothing>()

}