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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.himanshu.studybuddy.presentation.destinations.SessionScreenRouteDestination
import com.himanshu.studybuddy.presentation.destinations.SubjectScreenRouteDestination
import com.himanshu.studybuddy.presentation.destinations.TaskScreenRouteDestination
import com.himanshu.studybuddy.presentation.subject.SubjectScreenNavArgs
import com.himanshu.studybuddy.presentation.task.TaskScreenNavArgs
import com.himanshu.studybuddy.util.SnackbarEvent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@RootNavGraph(start = true)
@Destination
@Composable
fun DashboardScreenRoute(navigator : DestinationsNavigator){

    val viewModel : DashboardViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val tasks by viewModel.tasks.collectAsState()
    val recentSessions by viewModel.recentSessions.collectAsState()

    DashboardScreen(
        state,
        tasks = tasks,
        recentSessions = recentSessions,
        onEvent = { viewModel.onEvent(it) },
        snackbarEvent = viewModel.snackbarEventFlow,
        onSubjectCardClick = { subjectId ->
        subjectId?.let {
            val navArg = SubjectScreenNavArgs(subjectId = subjectId)
            navigator.navigate(SubjectScreenRouteDestination(navArgs = navArg))
        }
    },
        onTaskCardClick = { taskId ->
            val navArg = TaskScreenNavArgs(taskId = taskId, subjectId = null)
            navigator.navigate(TaskScreenRouteDestination(navArgs = navArg))
        },
        onStartSessionButtonClick = {
            navigator.navigate(SessionScreenRouteDestination())
        })
}
@Composable
    private fun DashboardScreen(
    state: DashboardState,
    tasks: List<Task>,
    recentSessions: List<Session>,
    onEvent :(DashboardEvent) -> Unit,
    snackbarEvent : SharedFlow<SnackbarEvent>,
    onSubjectCardClick: (Int?) -> Unit,
    onTaskCardClick: (Int?) -> Unit,
    onStartSessionButtonClick: () -> Unit
    ) {

    var isAddSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        snackbarEvent.collectLatest { event->
            when(event) {
                SnackbarEvent.NavigateUp -> {}
                is SnackbarEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }
            }
        }
    }

    AddSubjectDialog(
        isDialogBoxOpen = isAddSubjectDialogOpen,
        selectedSubject = state.subjectName,
        selectedGoalHours = state.goalStudyHours,
        onSubjectNameChange = { onEvent(DashboardEvent.OnSubjectNameChange(it)) },
        onGoalHoursChange = { onEvent(DashboardEvent.OnGoalStudyHoursChange(it))  },
        selectedColor = state.subjectCardColors,
        onColorChange = { onEvent(DashboardEvent.OnSubjectCardColorChange(it))  },
        onDismissButton = { isAddSubjectDialogOpen = false },
        onConfirmButton = {
            onEvent(DashboardEvent.SaveSubject)
            isAddSubjectDialogOpen = false

        }
    )
    DeleteDialog(
        title = "Delete Session ?",
        isDialogBoxOpen = isDeleteSubjectDialogOpen ,
        bodyText = "Are you sure to delete this session ?",
        onDismissButton = { isDeleteSubjectDialogOpen = false },
        onConfirmButton = {
            onEvent(DashboardEvent.DeleteSession)
            isDeleteSubjectDialogOpen = false
        }
    )


    Scaffold(
        snackbarHost = {SnackbarHost(hostState = snackbarHostState)},
        topBar = { TopBar() }) {
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
                    subjectCount = state.totalSubjectCount,
                    studiedHours = state.totalStudiedHours.toString(),
                    goalHours = state.totalGoalStudyHours.toString()
                )
                SubjectCardSection(
                    modifier = Modifier,
                    subjectList = state.subjects,
                    onAddItemClicked = { isAddSubjectDialogOpen = true },
                    onSubjectCardClick = onSubjectCardClick
                )
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp, vertical = 10.dp),
                    onClick = onStartSessionButtonClick
                ) {
                    Text(text = "Start Study Session")
                }
            }
            tasksList(
                sectionTitle = "UPCOMING TASKS",
                emptyListText = "You don't have any upcoming tasks.\n " +
                        "Click the + button in subject screen to add new task.",
                tasks = tasks,
                onTaskCardClick = onTaskCardClick,
                onCheckBoxClick = {it->onEvent(DashboardEvent.OnTaskIsCompleteChange(it))}
            )
            studySessionsList(
                sectionTitle = "RECENT STUDY SESSIONS",
                emptyListText = "You don't have any recent study sessions.\n " +
                        "Start a study session to begin recording your progress.",
                sessions = recentSessions,
                onDeleteIconClick = {it->
                    onEvent(DashboardEvent.OnDeleteSessionButtonClick(it))
                    isDeleteSubjectDialogOpen = true }
            )
        }
    }
}

@Composable
private fun SubjectCardSection(
    modifier: Modifier,
    subjectList: List<Subject>,
    emptyListText: String = "You don't have any subject \n Click + button to add new subject",
    onAddItemClicked: () -> Unit,
    onSubjectCardClick: (Int?) -> Unit

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
                        gradientColors = subject.colors.map { Color(it) },
                        onClick = {onSubjectCardClick(subject.subjectId)}
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