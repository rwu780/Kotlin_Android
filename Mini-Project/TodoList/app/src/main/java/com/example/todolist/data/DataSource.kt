package com.example.todolist.data

import androidx.lifecycle.LiveData
import com.example.todolist.data.model.Task
import com.example.todolist.util.ResultState

interface DataSource {

    fun observeTasks(): LiveData<ResultState<List<Task>>>

    suspend fun getTasks(): ResultState<List<Task>>

    suspend fun refreshTasks()

    fun observeTask(taskId: String): LiveData<ResultState<Task>>

    suspend fun getTask(taskId: String): ResultState<Task>

    suspend fun refreshTask(taskId: String)

    suspend fun saveTask(task: Task)

    suspend fun completeTask(task: Task)

    suspend fun completeTask(taskId: String)

    suspend fun activateTask(task: Task)

    suspend fun activateTask(taskId: String)

    suspend fun clearCompletedTasks()

    suspend fun deleteAllTasks()

    suspend fun deleteTask(taskId: String)


}