package com.example.todolist.data

import androidx.lifecycle.LiveData
import com.example.todolist.data.local.LocalDataSource
import com.example.todolist.data.model.Task
import com.example.todolist.data.remote.RemoteDataSource
import com.example.todolist.domain.TasksRepository
import com.example.todolist.util.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.withContext
import java.lang.Exception

class TasksRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TasksRepository {

    private suspend fun updateTasksFromRemoteDataSource() {
        val remoteTasks = remoteDataSource.getTasks()

        if (remoteTasks is ResultState.SUCCESS) {
            // Real apps might want to do a proper sync.
            localDataSource.deleteAllTasks()
            remoteTasks.data.forEach { task ->
                localDataSource.saveTask(task)
            }
        } else if (remoteTasks is ResultState.ERROR) {
            throw Exception(remoteTasks.msg)
        }
    }

    private suspend fun updateTaskFromRemoteDataSource(taskId: String) {
        val remoteTask = remoteDataSource.getTask(taskId)

        if (remoteTask is ResultState.SUCCESS) {
            localDataSource.saveTask(remoteTask.data)
        }
    }

    private suspend fun getTaskWithId(id: String): ResultState<Task> {
        return localDataSource.getTask(id)
    }

    override suspend fun getTasks(forceUpdate: Boolean): ResultState<List<Task>> {
        if (forceUpdate) {
            try {
                updateTasksFromRemoteDataSource()
            } catch (e: Exception){
                return ResultState.ERROR(msg=e.message ?: "Unknown Error")
            }
        }

        return localDataSource.getTasks()
    }

    override suspend fun refreshTasks() {
        updateTasksFromRemoteDataSource()
    }

    override fun observeTasks(): LiveData<ResultState<List<Task>>> {
        return localDataSource.observeTasks()
    }

    override suspend fun refreshTask(taskId: String) {
        val remoteTask = remoteDataSource.getTask(taskId)
        if(remoteTask is ResultState.SUCCESS){
            localDataSource.saveTask(remoteTask.data)
        }

    }

    override fun observeTask(taskId: String): LiveData<ResultState<Task>> {
        return localDataSource.observeTask(taskId)
    }

    override suspend fun getTask(taskId: String, forceUpdate: Boolean): ResultState<Task> {
        if(forceUpdate){
            updateTaskFromRemoteDataSource(taskId)
        }
        return localDataSource.getTask(taskId)
    }

    override suspend fun saveTask(task: Task) {
        withContext(ioDispatcher) {
            localDataSource.saveTask(task)
            remoteDataSource.saveTask(task)
        }
    }

    override suspend fun completeTask(task: Task) {
        withContext(ioDispatcher) {
            localDataSource.completeTask(task)
            remoteDataSource.completeTask(task)
        }
    }

    override suspend fun completeTask(taskId: String) {
        withContext(ioDispatcher) {
            localDataSource.completeTask(taskId)
            remoteDataSource.completeTask(taskId)
        }
    }

    override suspend fun activateTask(task: Task) {
        withContext(ioDispatcher){
            localDataSource.activateTask(task)
            remoteDataSource.activateTask(task)
        }
    }

    override suspend fun activateTask(taskId: String) {
        withContext(ioDispatcher){
            localDataSource.activateTask(taskId)
            remoteDataSource.activateTask(taskId)
        }
    }

    override suspend fun clearCompletedTasks() {
        withContext(ioDispatcher) {
            localDataSource.clearCompletedTasks()
            remoteDataSource.clearCompletedTasks()
        }
    }

    override suspend fun deleteAllTasks() {
        withContext(ioDispatcher){
            localDataSource.deleteAllTasks()
            remoteDataSource.deleteAllTasks()
        }
    }

    override suspend fun deleteTask(taskId: String) {
        withContext(ioDispatcher) {
            localDataSource.deleteTask(taskId)
            remoteDataSource.deleteTask(taskId)
        }
    }


}