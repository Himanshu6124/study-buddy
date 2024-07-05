package com.himanshu.studybuddy.domain.repository

import com.himanshu.studybuddy.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun upsertTask(task: Task)

    suspend fun deleteTask(taskId: Int)

    suspend fun getTaskById(taskId: Int): Task?

    fun getUpcomingTasksForSubject(subjectInt: Int): Flow<List<Task>>

    fun getCompletedTasksForSubject(subjectInt: Int): Flow<List<Task>>

    fun getAllUpcomingTasks(): Flow<List<Task>>
}