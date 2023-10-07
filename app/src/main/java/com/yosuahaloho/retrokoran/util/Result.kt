package com.yosuahaloho.retrokoran.util

/**
 * Created by Yosua on 13/05/2023
 */
sealed class Result<out T: Any> {
    data class Success<out T: Any>(val value: T) : Result<T>()
    data class Failure(val message: String) : Result<Nothing>()
}
