package me.danikvitek.lab3.screen

import android.util.Log
import androidx.annotation.FloatRange
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.danikvitek.lab3.R
import me.danikvitek.lab3.data.entity.Student
import me.danikvitek.lab3.ui.theme.Lab3Theme
import me.danikvitek.lab3.viewmodel.StudentInfoViewModel
import kotlin.math.ceil
import kotlin.math.log10
import kotlin.math.max
import kotlin.random.Random

@Composable
fun StudentsInfo(
    modifier: Modifier = Modifier,
    viewModel: StudentInfoViewModel = hiltViewModel(),
) {
    val students by viewModel.students.collectAsState()
    StudentsInfo(
        students = students,
        modifier = modifier,
    )
}

@Composable
private fun StudentsInfo(
    students: List<Student>,
    modifier: Modifier = Modifier,
) {
    StudentsTable(
        students = students,
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
private fun StudentsTable(
    students: List<Student>,
    modifier: Modifier = Modifier,
    @FloatRange(
        from = 0.0,
        fromInclusive = false
    ) idWeight: Float = max(1f, 1f + (ceil(log10(students.size.toDouble())) - 1).toFloat() * 0.2f),
    @FloatRange(from = 0.0, fromInclusive = false) fullNameWeight: Float = 8f,
    @FloatRange(from = 0.0, fromInclusive = false) createdAtWeight: Float = 7f,
) {
    val listState = rememberLazyListState()
    SideEffect {
        Log.d(
            "StudentsInfo",
            "listState: (" +
            "firstVisibleItemIndex: ${listState.firstVisibleItemIndex}, " +
            "firstVisibleItemScrollOffset: ${listState.firstVisibleItemScrollOffset}})"
        )
    }
    LazyColumn(
        state = listState,
        modifier = modifier,
    ) {
        item(key = -1L) {
            Header(
                idWeight = idWeight,
                fullNameWeight = fullNameWeight,
                createdAtWeight = createdAtWeight,
            )
        }
        items(items = students, key = { it.id }) { student ->
            EntryRow(
                student = student,
                idWeight = idWeight,
                fullNameWeight = fullNameWeight,
                createdAtWeight = createdAtWeight,
            )
        }
    }
}

@Composable
private fun Header(
    @FloatRange(from = 0.0, fromInclusive = false) idWeight: Float,
    @FloatRange(from = 0.0, fromInclusive = false) fullNameWeight: Float,
    @FloatRange(from = 0.0, fromInclusive = false) createdAtWeight: Float,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .weight(weight = idWeight)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                ),
        ) {
            Text(
                text = stringResource(R.string.student_info_id),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
            )
        }
        Box(
            modifier = Modifier
                .weight(weight = fullNameWeight)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                )
                .padding(horizontal = 4.dp),
        ) {
            Text(
                text = stringResource(R.string.student_info_full_name),
                fontWeight = FontWeight.SemiBold,
            )
        }
        Box(
            modifier = Modifier
                .weight(weight = createdAtWeight)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                )
                .padding(horizontal = 4.dp),
        ) {
            Text(
                text = stringResource(R.string.student_info_created_at),
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
private fun EntryRow(
    student: Student,
    @FloatRange(from = 0.0, fromInclusive = false) idWeight: Float,
    @FloatRange(from = 0.0, fromInclusive = false) fullNameWeight: Float,
    @FloatRange(from = 0.0, fromInclusive = false) createdAtWeight: Float,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxHeight()
            .height(IntrinsicSize.Min),
    ) {
        Box(
            modifier = Modifier
                .weight(weight = idWeight)
                .fillMaxHeight()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                ),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                text = student.id.toString(),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
        Box(
            modifier = Modifier
                .weight(weight = fullNameWeight)
                .fillMaxHeight()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                )
                .padding(horizontal = 4.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                text = student.fullName,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Box(
            modifier = Modifier
                .weight(weight = createdAtWeight)
                .fillMaxHeight()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                )
                .padding(horizontal = 4.dp),
        ) {
            Text(
                text = student.createdAt.toString(),
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
private fun StudentsInfoPreview(
    @PreviewParameter(StudentInfoPreviewParamProvider::class) students: List<Student>,
) {
    Lab3Theme {
        Scaffold { innerPadding ->
            StudentsInfo(
                students = students,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

private class StudentInfoPreviewParamProvider : PreviewParameterProvider<List<Student>> {
    override val values: Sequence<List<Student>> = List(4) {
        List(it * 7) { id ->
            Student(
                id = id.toLong() + 1,
                surname = SURNAMES_POOL[Random.nextInt(0, SURNAMES_POOL.size)],
                name = NAMES_POOL[Random.nextInt(0, NAMES_POOL.size)],
                patronymic = PATRONYMICS_POOL[Random.nextInt(0, PATRONYMICS_POOL.size)],
            )
        }
    }.asSequence()

    companion object {
        private val NAMES_POOL = listOf(
            "Данило",
            "Петро",
            "Іван",
            "Сидір",
            "Микита",
        )

        private val SURNAMES_POOL = listOf(
            "Вітковський",
            "Петренко",
            "Іваненко",
            "Сидоренко",
            "Микитенко",
        )

        private val PATRONYMICS_POOL = listOf(
            "Олександрович",
            "Петрович",
            "Іванович",
            "Сидорович",
            "Микитович",
        )
    }
}