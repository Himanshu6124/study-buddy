package com.himanshu.studybuddy.data.repository

import com.himanshu.studybuddy.data.local.TaskDao
import com.himanshu.studybuddy.domain.model.Task
import com.himanshu.studybuddy.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(val taskDao: TaskDao): TaskRepository {
    override suspend fun upsertTask(task: Task) {
        taskDao.upsertTask(task)
    }

    override suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTask(taskId)
    }

    override suspend fun getTaskById(taskId: Int): Task? {
        return taskDao.getTaskById(taskId)
    }

    override fun getUpcomingTasksForSubject(subjectInt: Int): Flow<List<Task>> {
        return taskDao.getTasksForSubject(subjectInt)
            .map { tasks-> tasks.filter { !it.isComplete } }
            .map { tasks-> sortedTasks(tasks)  }

    }

    override fun getCompletedTasksForSubject(subjectInt: Int): Flow<List<Task>> {
        return taskDao.getTasksForSubject(subjectInt)
            .map { tasks-> tasks.filter { it.isComplete } }
            .map { tasks-> sortedTasks(tasks)  }
    }

    override fun getAllUpcomingTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
                .map { tasks-> tasks.filter { !it.isComplete } }
                .map { tasks-> sortedTasks(tasks)  }
    }

    private fun sortedTasks(tasks : List<Task>) : List<Task>{
        return tasks.sortedWith(compareBy<Task> {it.dueDate}.thenByDescending{it.priority})

    }
}