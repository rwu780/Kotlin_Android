package com.example.todolist.domain

import androidx.lifecycle.LiveData
import com.example.todolist.data.model.Task
import com.example.todolist.util.ResultState

interface TasksRepository {

    suspend fun getTasks(forceUpdate: Boolean = false): ResultState<List<Task>>
    suspend fun refreshTasks()
    fun observeTasks(): LiveData<ResultState<List<Task>>>

    suspend fun refreshTask(taskId: String)

    fun observeTask(taskId: String): LiveData<ResultState<Task>>

    /**
     * Relies on [getTasks] to fetch data and picks the task with the same ID.
     */
    suspend fun getTask(taskId: String,  forceUpdate: Boolean = false): ResultState<Task>

    suspend fun saveTask(task: Task)

    suspend fun completeTask(task: Task)

    suspend fun completeTask(taskId: String)

    suspend fun activateTask(task: Task)

    suspend fun activateTask(taskId: String)

    suspend fun clearCompletedTasks()


    suspend fun deleteAllTasks()
    suspend fun deleteTask(taskId: String)

}