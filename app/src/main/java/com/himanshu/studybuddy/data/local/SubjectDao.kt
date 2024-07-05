package com.himanshu.studybuddy.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import javax.security.auth.Subject

@Dao
interface SubjectDao {

    @Upsert
    suspend fun upsertSubject(subject: com.himanshu.studybuddy.domain.model.Subject)

    @Query("SELECT COUNT(*) FROM SUBJECT")
    fun getTotalSubjectCount() :Flow<Int>

    @Query("SELECT SUM(goalHours) FROM SUBJECT")
    fun getTotalGoalHours() :Flow<Float>

    @Query("SELECT * FROM SUBJECT WHERE subjectId = :subjectId")
    suspend fun getSubjectById(subjectId: Int) : Subject?


    @Query("DELETE FROM SUBJECT WHERE subjectId = :subjectId")
    suspend fun deleteSubject(subjectId: Int)

    @Query("SELECT * FROM SUBJECT")
    fun getAllSubjects():Flow<List<Subject>>


}
