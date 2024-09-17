package me.danikvitek.lab3.screen

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        modifier = modifier.fillMaxWidth(),
    ) {
        item { Header() }
        items(items = students, key = { it.id }) { student ->
            EntryRow(student = student)
        }
    }
}

@Composable
private fun Header(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.black)
                )
                .padding(horizontal = 4.dp),
        ) {
            Text(
                text = "ID",
                fontWeight = FontWeight.SemiBold,
            )
        }
        Box(
            modifier = Modifier
                .weight(8f)
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.black)
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
                .weight(7f)
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.black)
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
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxHeight()
            .height(IntrinsicSize.Min),
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.black)
                )
                .padding(horizontal = 4.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                text = student.id.toString(),
                overflow = TextOverflow.Visible,
            )
        }
        Box(
            modifier = Modifier
                .weight(8f)
                .fillMaxHeight()
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.black)
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
                .weight(7f)
                .fillMaxHeight()
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.black)
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
    override val values: Sequence<List<Student>> = sequenceOf(
        emptyList(),
        listOf(
            Student(1, "Вітковський Данило Олександрович"),
            Student(2, "Петренко Петро Петрович"),
            Student(3, "Іваненко Іван Іванович"),
            Student(4, "Сидоренко Сидір Сидорович"),
            Student(5, "Микитенко Микита Микитович"),
        )
    )
}