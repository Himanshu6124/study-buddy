package com.himanshu.studybuddy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.himanshu.studybuddy.domain.model.Session
import com.himanshu.studybuddy.domain.model.Subject
import com.himanshu.studybuddy.domain.model.Task

@TypeConverters(ColorListConverter::class)
@Database(version = 1, entities = [Subject::class,Task::class,Session::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun subjectDao(): SubjectDao
    abstract fun taskDao(): TaskDao
    abstract fun sessionDao(): SessionDao
}