package com.himanshu.studybuddy.presentation.session

import com.himanshu.studybuddy.domain.model.Session
import com.himanshu.studybuddy.domain.model.Subject

data class SessionState(
    val subjects: List<Subject> = emptyList(),
    val sessions: List<Session> = emptyList(),
    val relatedToSubject: String? = null,
    val subjectId: Int? = null,
    val session: Session? = null
)