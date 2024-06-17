package com.himanshu.studybuddy.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountCard(modifier: Modifier = Modifier, headingText: String, count: String) {
    ElevatedCard(modifier = modifier.padding(horizontal = 6.dp) , onClick = { /*TODO*/ }) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp, horizontal = 4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = headingText,
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = count,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 30.sp)
            )
        }
    }
}

@Composable
fun CountCardSection(
    modifier: Modifier,
    subjectCount :Int,
    studiedHours :String,
    goalHours:String
){

    Row( modifier = modifier) {
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Subject Count" ,
            count = "$subjectCount"
        )
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Study Hours" ,
            count = studiedHours
        )
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Goal Hours" ,
            count = goalHours
        )

    }
}