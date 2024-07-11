package com.himanshu.studybuddy.util

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.Color
import com.himanshu.studybuddy.presentation.theme.Green
import com.himanshu.studybuddy.presentation.theme.Orange
import com.himanshu.studybuddy.presentation.theme.Red
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

enum class Priority(val title :String, val color : Color, val value:Int){
    LOW("Low", Green,0),
    MEDIUM("Medium", Orange,1),
    HIGH("High", Red,2);

    companion object {
        fun fromInt(value: Int) = entries.firstOrNull{it.value == value} ?: MEDIUM
    }
}
fun Long?.changeMillisToDateString(): String {
    val date: LocalDate = this?.let {
        Instant
            .ofEpochMilli(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    } ?: LocalDate.now()
    return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
}

fun Long.toHours(): Float {
    val hours = this.toFloat() / 3600f
    return "%.2f".format(hours).toFloat()
}

sealed class SnackbarEvent {
    data class ShowSnackbar(
        val message: String,
        val duration: SnackbarDuration = SnackbarDuration.Short
    ) : SnackbarEvent()

    data object NavigateUp: SnackbarEvent()
}