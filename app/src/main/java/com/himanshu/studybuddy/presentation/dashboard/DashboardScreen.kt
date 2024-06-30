package com.himanshu.studybuddy.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.himanshu.studybuddy.R
import com.himanshu.studybuddy.domain.model.Session
import com.himanshu.studybuddy.domain.model.Subject
import com.himanshu.studybuddy.domain.model.Task
import com.himanshu.studybuddy.presentation.components.AddSubjectDialog
import com.himanshu.studybuddy.presentation.components.CountCardSection
import com.himanshu.studybuddy.presentation.components.DeleteDialog
import com.himanshu.studybuddy.presentation.components.SubjectCard
import com.himanshu.studybuddy.presentation.components.studySessionsList
import com.himanshu.studybuddy.presentation.components.tasksList

@Composable
fun DashboardScreen() {

    val subjects = listOf(
        Subject("English", 5f, Subject.subjectCardColors[0]),
        Subject("Maths", 6f, Subject.subjectCardColors[1]),
        Subject("Physics", 3f, Subject.subjectCardColors[2]),
        Subject("S.St", 10f, Subject.subjectCardColors[3]),
        Subject("Cs", 4f, Subject.subjectCardColors[4])
    )

    val tasks = listOf(
        Task("Homework", "Complete Homework", 0L, 2, "Maths", false, 11, 100),
        Task("Learn", "Complete Assignment", 0L, 2, "English", true, 11, 100),
        Task("Assignment", "Complete Homework", 0L, 1, "History", false, 11, 100)
    )
    val sessions = listOf(
        Session(1, "Maths", 42, 40, 5),
        Session(1, "Physics", 24, 150, 3),
        Session(1, "Chemistry", 12, 100, 2),
        Session(1, "Maths", 22, 100, 1)
    )
    val selectedColor = remember { mutableStateOf(Subject.subjectCardColors[0]) }
    val selectedSubject = remember { mutableStateOf("") }
    val selectedGoalHours = remember { mutableStateOf("") }
    var isAddSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }

    AddSubjectDialog(
        isDialogBoxOpen = isAddSubjectDialogOpen,
        selectedSubject = selectedSubject.value,
        selectedGoalHours = selectedGoalHours.value,
        onSubjectNameChange = { selectedSubject.value = it },
        onGoalHoursChange = { selectedGoalHours.value = it },
        selectedColor = selectedColor.value,
        onColorChange = { selectedColor.value = it },
        onDismissButton = { isAddSubjectDialogOpen = false },
        onConfirmButton = { isAddSubjectDialogOpen = false }
    )
    DeleteDialog(
        title = "Delete Session ?",
        isDialogBoxOpen = isDeleteSubjectDialogOpen ,
        bodyText = "Are you sure to delete this session ?",
        onDismissButton = { isDeleteSubjectDialogOpen = false },
        onConfirmButton = {isDeleteSubjectDialogOpen = false}
    )


    Scaffold(topBar = { TopBar() }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item {
                CountCardSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    subjectCount = 5,
                    studiedHours = "4",
                    goalHours = "10"
                )
                SubjectCardSection(
                    modifier = Modifier,
                    subjectList = subjects,
                    onAddItemClicked = { isAddSubjectDialogOpen = true })
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp, vertical = 10.dp),
                    onClick = { /*TODO*/ }) {
                    Text(text = "Start Study Session")
                }
            }
            tasksList(
                sectionTitle = "UPCOMING TASKS",
                emptyListText = "You don't have any upcoming tasks.\n " +
                        "Click the + button in subject screen to add new task.",
                tasks = tasks,
                onTaskCardClick = {},
                onCheckBoxClick = {}
            )
            studySessionsList(
                sectionTitle = "RECENT STUDY SESSIONS",
                emptyListText = "You don't have any recent study sessions.\n " +
                        "Start a study session to begin recording your progress.",
                sessions = sessions,
                onDeleteIconClick = { isDeleteSubjectDialogOpen = true }
            )
        }
    }
}

@Composable
private fun SubjectCardSection(
    modifier: Modifier,
    subjectList: List<Subject>,
    emptyListText: String = "You don't have any subject \n Click + button to add new subject",
    onAddItemClicked: () -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Subjects", style = MaterialTheme.typography.titleLarge)
            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .clickable { onAddItemClicked() }, imageVector = Icons.Default.Add,
                contentDescription = "Add subject"
            )
        }

        if (subjectList.isEmpty()) {

            Image(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.img_books),
                contentDescription = emptyListText
            )
            Text(
                text = emptyListText,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp),
            content = {
                items(subjectList) { subject ->
                    SubjectCard(
                        subjectName = subject.name,
                        gradientColors = subject.colors,
                        onClick = {}
                    )
                }
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(title = {
        Text(
            text = "Study Buddy",
            style = MaterialTheme.typography.headlineMedium
        )
    })
}