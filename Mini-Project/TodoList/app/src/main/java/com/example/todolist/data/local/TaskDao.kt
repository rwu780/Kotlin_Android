package com.example.todolist.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todolist.data.model.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM Tasks")
    fun observeTasks() : LiveData<List<Task>>

    @Query("SELECT * FROM Tasks where entryid = :taskId")
    fun observeTaskById(taskId: String) : LiveData<Task>

    @Query("SELECT * FROM Tasks")
    suspend fun getTasks() : List<Task>

    @Query("SELECT * FROM tasks where entryid = :taskId")
    suspend fun getTasksById(taskId: String) : Task?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task): Int

    @Query("UPDATE tasks SET completed = :completed where entryid = :taskId")
    suspend fun updateCompleted(taskId: String, completed : Boolean)

    @Query("DELETE FROM Tasks where entryid = :taskId")
    suspend fun deleteTaskById(taskId: String): Int

    @Query("DELETE FROM Tasks")
    suspend fun deleteTasks()

    @Query("DELETE FROM tasks where completed = 1")
    suspend fun deleteCompletedTasks(): Int

}