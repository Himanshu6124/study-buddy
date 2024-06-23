package com.himanshu.studybuddy.util

import androidx.compose.ui.graphics.Color
import com.himanshu.studybuddy.presentation.theme.Green
import com.himanshu.studybuddy.presentation.theme.Orange
import com.himanshu.studybuddy.presentation.theme.Red

enum class Priority(val title :String, val color : Color, val value:Int){
    LOW("Low", Green,0),
    MEDIUM("Low", Orange,1),
    HIGH("Low", Red,2);

    companion object {
        fun fromInt(value: Int) = entries.firstOrNull{it.value == value} ?: MEDIUM
    }
}