package com.example.todolist.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.todolist.data.DataSource
import com.example.todolist.data.model.Task
import com.example.todolist.util.ResultState
import kotlinx.coroutines.delay

class RemoteDataSource : DataSource {

    private val SERVICE_LATENCY_IN_MILLIS = 2000L

    private var TASKS_SERVICE_DATA = LinkedHashMap<String, Task>(2)
    private val observableTasks = MutableLiveData<ResultState<List<Task>>>()

    init {

        addTask("Build tower in Pisa", "Ground looks good, no foundation work required.")
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!")

    }

    override fun observeTasks(): LiveData<ResultState<List<Task>>> {
        return observeTasks()
    }

    override suspend fun getTasks(): ResultState<List<Task>> {
        val tasks = TASKS_SERVICE_DATA.values.toList()
        delay(SERVICE_LATENCY_IN_MILLIS)
        return ResultState.SUCCESS(tasks)
    }

    override suspend fun refreshTasks() {
        observableTasks.value = getTasks()!!
    }

    override fun observeTask(taskId: String): LiveData<ResultState<Task>> {
        return observableTasks.map { tasks ->
            when(tasks) {
                is ResultState.ERROR -> ResultState.ERROR(msg = tasks.msg)
                is ResultState.SUCCESS -> {
                    val task = tasks.data.firstOrNull() {
                        it.id == taskId
                    } ?: return@map ResultState.ERROR(msg = "Task Not found")

                    ResultState.SUCCESS(task)
                }
                else -> ResultState.LOADING()
            }
        }
    }

    override suspend fun getTask(taskId: String): ResultState<Task> {
        delay(SERVICE_LATENCY_IN_MILLIS)

        return TASKS_SERVICE_DATA[taskId]?.let {
            ResultState.SUCCESS(it)
        } ?: run {ResultState.ERROR(msg="No Task Found")}
    }

    override suspend fun refreshTask(taskId: String) {
        refreshTasks()
    }

    private fun addTask(title: String, description: String) {
        val newTask = Task(title, description)
        TASKS_SERVICE_DATA[newTask.id] = newTask
    }

    override suspend fun saveTask(task: Task) {
        TASKS_SERVICE_DATA[task.id] = task
    }

    override suspend fun completeTask(task: Task) {
        val completedTask = task.copy(isCompleted = true)
        TASKS_SERVICE_DATA[completedTask.id] = completedTask
    }

    override suspend fun completeTask(taskId: String) {
        // No - OP
    }

    override suspend fun activateTask(task: Task) {
        val activeTask = task.copy(isCompleted = false)
        TASKS_SERVICE_DATA[task.id] = activeTask
    }

    override suspend fun activateTask(taskId: String) {
        // No - OP
    }

    override suspend fun clearCompletedTasks() {
        TASKS_SERVICE_DATA = TASKS_SERVICE_DATA.filter { (id, task) ->
            !task.isCompleted
        }  as LinkedHashMap<String, Task>
    }

    override suspend fun deleteAllTasks() {
        TASKS_SERVICE_DATA.clear()
    }

    override suspend fun deleteTask(taskId: String) {
        TASKS_SERVICE_DATA.remove(taskId)
    }
}