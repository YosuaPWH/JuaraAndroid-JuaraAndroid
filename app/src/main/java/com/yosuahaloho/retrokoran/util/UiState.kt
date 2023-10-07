package com.yosuahaloho.retrokoran.util

/**
 * Created by Yosua on 13/05/2023
 */
sealed class UiState<out T: Any> {
    data class Success<out T: Any>(val data: T) : UiState<T>()
    data class Error(val errorMessage: String) : UiState<Nothing>()
    object Loading : UiState<Nothing>()
}
