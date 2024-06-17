package com.himanshu.studybuddy.presentation.dashboard

import android.graphics.drawable.Icon
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.himanshu.studybuddy.R
import com.himanshu.studybuddy.domain.model.Subject
import com.himanshu.studybuddy.presentation.components.CountCardSection
import com.himanshu.studybuddy.presentation.components.SubjectCard

@Composable
fun DashboardScreen() {

    val subjects = listOf(
        Subject("English", 5f, Subject.subjectCardColors[0]),
        Subject("Maths", 6f, Subject.subjectCardColors[1]),
        Subject("Physics", 3f, Subject.subjectCardColors[2]),
        Subject("S.St", 10f, Subject.subjectCardColors[3]),
        Subject("Cs", 4f, Subject.subjectCardColors[4])
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
                SubjectCardSection(modifier = Modifier, subjectList = subjects)
            }
        }
    }
}

@Composable
private fun SubjectCardSection(
    modifier: Modifier,
    subjectList: List<Subject>,
    emptyListText: String = "You don't have any subject \n Click + button to add new subject"
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
                    .clickable { /* TODO */ }, imageVector = Icons.Default.Add,
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
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
        ) {
            items(subjectList) { subject ->
                SubjectCard(
                    subjectName = subject.name,
                    gradientColors = subject.colors,
                    onClick = {}
                )
            }
        }
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