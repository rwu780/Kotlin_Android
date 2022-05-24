package com.example.todolist.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.todolist.data.DataSource
import com.example.todolist.data.model.Task
import com.example.todolist.util.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LocalDataSource(
    private val taskDao: TaskDao,
    private val ioDispatcher: CoroutineDispatcher
) : DataSource  {
    override fun observeTasks(): LiveData<ResultState<List<Task>>> {
        return taskDao.observeTasks().map {
            ResultState.SUCCESS(it)
        }
    }

    override suspend fun getTasks(): ResultState<List<Task>> = withContext(ioDispatcher) {
        return@withContext try{
            ResultState.SUCCESS(taskDao.getTasks())
        } catch (e: Exception) {
            ResultState.ERROR(msg = e.message ?: "Unknown Error")
        }
    }

    override suspend fun refreshTasks() {
        // NO-OP
    }

    override fun observeTask(taskId: String): LiveData<ResultState<Task>> {
        return taskDao.observeTaskById(taskId = taskId).map {
            ResultState.SUCCESS(it)
        }
    }

    override suspend fun getTask(taskId: String): ResultState<Task> = withContext(ioDispatcher){
        try{
            val task = taskDao.getTasksById(taskId = taskId)
            task?.let {
                ResultState.SUCCESS(it)
            } ?: run {
                ResultState.ERROR(msg = "Task not found")
            }
        } catch (e: Exception){
            ResultState.ERROR(msg = e.message ?: "Unknown Error")
        }
    }

    override suspend fun refreshTask(taskId: String) {
        // No-OP
    }

    override suspend fun saveTask(task: Task) = withContext(ioDispatcher) {
        taskDao.insertTask(task)
    }

    override suspend fun completeTask(task: Task) = withContext(ioDispatcher) {
        taskDao.updateCompleted(taskId = task.id, true)
    }

    override suspend fun completeTask(taskId: String) = withContext(ioDispatcher) {
        taskDao.updateCompleted(taskId = taskId, true)
    }

    override suspend fun activateTask(task: Task) = withContext(ioDispatcher) {
        taskDao.updateCompleted(taskId = task.id, false)

    }

    override suspend fun activateTask(taskId: String) = withContext(ioDispatcher) {
        taskDao.updateCompleted(taskId = taskId, false)
    }

    override suspend fun clearCompletedTasks() = withContext<Unit>(ioDispatcher) {
        taskDao.deleteCompletedTasks()

    }

    override suspend fun deleteAllTasks() = withContext(ioDispatcher) {
        taskDao.deleteTasks()

    }

    override suspend fun deleteTask(taskId: String) = withContext<Unit>(ioDispatcher) {
        taskDao.deleteTaskById(taskId)
    }
}