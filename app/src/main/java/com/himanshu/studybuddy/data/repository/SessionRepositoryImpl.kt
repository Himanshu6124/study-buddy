package com.himanshu.studybuddy.data.repository

import com.himanshu.studybuddy.data.local.SessionDao
import com.himanshu.studybuddy.domain.model.Session
import com.himanshu.studybuddy.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(sessionDao: SessionDao) : SessionRepository {
    override suspend fun insertSession(session: Session) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSession(session: Session) {
        TODO("Not yet implemented")
    }

    override fun getAllSessions(): Flow<List<Session>> {
        TODO("Not yet implemented")
    }

    override fun getRecentFiveSessions(): Flow<List<Session>> {
        TODO("Not yet implemented")
    }

    override fun getRecentTenSessionsForSubject(subjectId: Int): Flow<List<Session>> {
        TODO("Not yet implemented")
    }

    override fun getTotalSessionsDuration(): Flow<Long> {
        TODO("Not yet implemented")
    }

    override fun getTotalSessionsDurationBySubjectId(subjectId: Int): Flow<Long> {
        TODO("Not yet implemented")
    }
}