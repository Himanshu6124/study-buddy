
package com.himanshu.studybuddy
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import com.himanshu.studybuddy.presentation.NavGraphs
import com.himanshu.studybuddy.presentation.destinations.SessionScreenRouteDestination
import com.himanshu.studybuddy.presentation.session.StudySessionTimerService
import com.himanshu.studybuddy.presentation.theme.StudyBuddyTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var isBound by mutableStateOf(false)
    private lateinit var timerService: StudySessionTimerService
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            val binder = service as StudySessionTimerService.StudySessionTimerBinder
            timerService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, StudySessionTimerService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (isBound) {
                StudyBuddyTheme {
                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        dependenciesContainerBuilder = {
                            dependency(SessionScreenRouteDestination) { timerService }
                        }
                    )
                }
            }
        }
        requestPermission()
    }
    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        isBound = false
    }
}


//val tasks = listOf(
//    Task("Homework", "Complete Homework", 0L, 2, "Maths", false, 11, 100),
//    Task("Learn", "Complete Assignment", 0L, 2, "English", true, 11, 100),
//    Task("Assignment", "Complete Homework", 0L, 1, "History", false, 11, 100)
//)
//val subjects = listOf(
//    Subject("English", 5f, Subject.subjectCardColors[0].map { it.toArgb() },1),
//    Subject("Maths", 6f, Subject.subjectCardColors[1].map { it.toArgb() },2),
//    Subject("Physics", 3f, Subject.subjectCardColors[2].map { it.toArgb() },3),
//    Subject("S.St", 10f, Subject.subjectCardColors[3].map { it.toArgb() },4),
//    Subject("Cs", 4f, Subject.subjectCardColors[4].map { it.toArgb() },5)
//)
//val sessions = listOf(
//    Session(1, "Maths", 42, 40, 5),
//    Session(1, "Physics", 24, 150, 3),
//    Session(1, "Chemistry", 12, 100, 2),
//    Session(1, "Maths", 22, 100, 1)
//)