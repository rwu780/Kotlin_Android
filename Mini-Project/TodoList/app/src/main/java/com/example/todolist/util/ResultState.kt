package com.example.todolist.util

sealed class ResultState<T> {

    data class SUCCESS<T>(val data: T): ResultState<T>()
    data class ERROR<T>(val data: T? = null, val msg: String): ResultState<T>()
    data class LOADING<T>(val data: T? = null, val msg: String? = null): ResultState<T>()

}