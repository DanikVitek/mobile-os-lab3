package me.danikvitek.lab3.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import me.danikvitek.lab3.R
import me.danikvitek.lab3.ui.theme.Lab3Theme
import me.danikvitek.lab3.viewmodel.StartScreenViewModel

@Composable
fun StartScreen(
    onNavigateToStudentInfo: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StartScreenViewModel = hiltViewModel(),
) {
    StartScreen(
        callbacks = StartScreenCallbacks(
            onNavigateToStudentInfo = onNavigateToStudentInfo,
            onAddStudent = {
                viewModel.addStudent("Вітковський Данило Олександрович")
            },
            onSwapLastStudent = {
                viewModel.swapLastStudent()
            },
        ),
        modifier = modifier,
    )
}

private data class StartScreenCallbacks(
    val onNavigateToStudentInfo: () -> Unit,
    val onAddStudent: () -> Unit,
    val onSwapLastStudent: () -> Unit,
)

@Composable
private fun StartScreen(
    callbacks: StartScreenCallbacks,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(50.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = { callbacks.onNavigateToStudentInfo() },
        ) {
            Text(
                text = stringResource(R.string.btn_students_info),
                fontSize = 18.sp,
            )
        }
        Button(
            onClick = { callbacks.onAddStudent() },
        ) {
            Text(
                text = stringResource(R.string.btn_add_Student),
                fontSize = 18.sp,
            )
        }
        Button(
            onClick = { callbacks.onSwapLastStudent() },
        ) {
            Text(
                text = stringResource(R.string.btn_swap_student),
                fontSize = 18.sp,
            )
        }
    }
}

@Preview(showBackground = true, locale = "uk")
@Composable
private fun StartScreenPreview() {
    Lab3Theme {
        Scaffold { innerPadding ->
            StartScreen(
                callbacks = StartScreenCallbacks({}, {}, {}),
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}