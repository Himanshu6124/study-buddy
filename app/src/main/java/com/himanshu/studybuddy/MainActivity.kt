package com.himanshu.studybuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import com.himanshu.studybuddy.domain.model.Session
import com.himanshu.studybuddy.domain.model.Subject
import com.himanshu.studybuddy.domain.model.Task
import com.himanshu.studybuddy.presentation.dashboard.DashboardScreen
import com.himanshu.studybuddy.presentation.session.SessionScreen
import com.himanshu.studybuddy.presentation.subject.SubjectScreen
import com.himanshu.studybuddy.presentation.task.TaskScreen
import com.himanshu.studybuddy.presentation.theme.StudyBuddyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyBuddyTheme( content = { SessionScreen() } )
        }
    }
}
val tasks = listOf(
    Task("Homework", "Complete Homework", 0L, 2, "Maths", false, 11, 100),
    Task("Learn", "Complete Assignment", 0L, 2, "English", true, 11, 100),
    Task("Assignment", "Complete Homework", 0L, 1, "History", false, 11, 100)
)
val subjects = listOf(
    Subject("English", 5f, Subject.subjectCardColors[0]),
    Subject("Maths", 6f, Subject.subjectCardColors[1]),
    Subject("Physics", 3f, Subject.subjectCardColors[2]),
    Subject("S.St", 10f, Subject.subjectCardColors[3]),
    Subject("Cs", 4f, Subject.subjectCardColors[4])
)
val sessions = listOf(
    Session(1, "Maths", 42, 40, 5),
    Session(1, "Physics", 24, 150, 3),
    Session(1, "Chemistry", 12, 100, 2),
    Session(1, "Maths", 22, 100, 1)
)