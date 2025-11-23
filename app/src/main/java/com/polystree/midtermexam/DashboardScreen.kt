package com.polystree.midtermexam

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.polystree.midtermexam.student.RegisterUiState
import com.polystree.midtermexam.student.Student
import com.polystree.midtermexam.student.StudentViewModel
import com.polystree.midtermexam.student.initialStudents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardRoute(
    viewModel: StudentViewModel,
    modifier: Modifier = Modifier
) {
    val students by viewModel.students.collectAsState()
    val registerState by viewModel.registerUiState.collectAsState()

    DashboardScreen(
        students = students,
        registerState = registerState,
        modifier = modifier,
        onRegisterNameChanged = viewModel::onRegisterNameChanged,
        onRegisterPasswordChanged = viewModel::onRegisterPasswordChanged,
        onRegisterPhoneChanged = viewModel::onRegisterPhoneChanged,
        onRegisterAddressChanged = viewModel::onRegisterAddressChanged,
        onSubmitRegister = viewModel::submitRegistration,
        onResetRegister = viewModel::resetRegistrationForm
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    students: List<Student>,
    registerState: RegisterUiState,
    modifier: Modifier = Modifier,
    onRegisterNameChanged: (String) -> Unit,
    onRegisterPasswordChanged: (String) -> Unit,
    onRegisterPhoneChanged: (String) -> Unit,
    onRegisterAddressChanged: (String) -> Unit,
    onSubmitRegister: () -> Boolean,
    onResetRegister: () -> Unit
) {
    var showRegisterDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Student Roster") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showRegisterDialog = true
                }
            ) {
                Icon(Icons.Filled.Add, "Add new student")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = modifier
    ) { paddingValues ->
        StudentList(
            students = students,
            modifier = Modifier.padding(paddingValues)
        )

        if (showRegisterDialog) {
            Dialog(
                onDismissRequest = {
                    showRegisterDialog = false
                    onResetRegister()
                }
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    RegisterForm(
                        state = registerState,
                        onNameChanged = onRegisterNameChanged,
                        onPasswordChanged = onRegisterPasswordChanged,
                        onPhoneChanged = onRegisterPhoneChanged,
                        onAddressChanged = onRegisterAddressChanged,
                        onSubmit = onSubmitRegister,
                        onRegistered = {
                            showRegisterDialog = false
                            onResetRegister()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun StudentList(
    students: List<Student>,
    modifier: Modifier = Modifier
) {
    Row {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                items(items = students) { student ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        Row {
                            Text(text = "Hello ${student.name} with ID ${student.id}")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DashboardScreenPreview() {
    DashboardScreen(
        students = initialStudents,
        registerState = RegisterUiState(),
        onRegisterNameChanged = {},
        onRegisterPasswordChanged = {},
        onRegisterPhoneChanged = {},
        onRegisterAddressChanged = {},
        onSubmitRegister = { true },
        onResetRegister = {}
    )
}
