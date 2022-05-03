package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Test

class StatisticsUtilsTest {

    @Test
    fun getActiveAndCompletedStats_empty_returnsZero() {
        // Create an active task
        val tasks = emptyList<Task>()

        // Call your function
        val result = getActiveAndCompletedStats(tasks)

        // Check the result
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(0f))

    }

    @Test
    fun getActiveAndCompletedStats_noActive_returnsZeroHundred() {
        // Create an active task
        val tasks = listOf<Task>(
            Task("title", "desc", isCompleted = true)
        )

        // Call your function
        val result = getActiveAndCompletedStats(tasks)

        // Check the result
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(100f))

    }

    @Test
    fun getActiveAndCompletedStats_error_returnsZeros() {
        // Create an active task
        val tasks = null

        // Call your function
        val result = getActiveAndCompletedStats(tasks)

        // Check the result
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(0f))

    }

    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsHundredZero() {
        // Create an active task
        val tasks = listOf<Task>(
            Task("title", "desc", isCompleted = false)
        )

        // Call your function
        val result = getActiveAndCompletedStats(tasks)

        // Check the result
        assertThat(result.activeTasksPercent, `is`(100f))
        assertThat(result.completedTasksPercent, `is`(0f))

    }

    @Test
    fun getActiveAndCompletedStats_both_returnsFortySixty() {
        // Create an active task
        val tasks = listOf<Task>(
            Task("title1", "desc1", isCompleted = true),
            Task("title2", "desc2", isCompleted = true),
            Task("title3", "desc3", isCompleted = true),
            Task("title4", "desc4", isCompleted = true),
            Task("title5", "desc5", isCompleted = true),
            Task("title6", "desc6", isCompleted = true),
            Task("title7", "desc7", isCompleted = false),
            Task("title8", "desc8", isCompleted = false),
            Task("title9", "desc9", isCompleted = false),
            Task("title10", "desc10", isCompleted = false)

        )

        // Call your function
        val result = getActiveAndCompletedStats(tasks)

        // Check the result
        assertThat(result.activeTasksPercent, `is`(40f))
        assertThat(result.completedTasksPercent, `is`(60f))

    }

}