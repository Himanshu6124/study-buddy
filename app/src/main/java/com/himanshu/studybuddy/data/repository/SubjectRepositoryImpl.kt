package com.himanshu.studybuddy.data.repository

import com.himanshu.studybuddy.data.local.SessionDao
import com.himanshu.studybuddy.data.local.SubjectDao
import com.himanshu.studybuddy.data.local.TaskDao
import com.himanshu.studybuddy.domain.model.Subject
import com.himanshu.studybuddy.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubjectRepositoryImpl @Inject constructor(
    private val subjectDao: SubjectDao,
    private val sessionDao: SessionDao,
    private val taskDao: TaskDao,
) : SubjectRepository {
    override suspend fun upsertSubject(subject: Subject) {
        subjectDao.upsertSubject(subject)
    }

    override fun getTotalSubjectCount(): Flow<Int> {
        return subjectDao.getTotalSubjectCount()
    }

    override fun getTotalGoalHours(): Flow<Float> {
        return subjectDao.getTotalGoalHours()
    }

    override suspend fun deleteSubject(subjectId: Int) {
        taskDao.deleteTasksBySubjectId(subjectId)
        subjectDao.deleteSubject(subjectId)
        sessionDao.deleteSessionsBySubjectId(subjectId)
    }

    override suspend fun getSubjectById(subjectId: Int): Subject? {
        return subjectDao.getSubjectById(subjectId)
    }

    override fun getAllSubjects(): Flow<List<Subject>> {
        return subjectDao.getAllSubjects()
    }
}