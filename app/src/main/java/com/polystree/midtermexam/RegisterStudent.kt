package com.polystree.midtermexam

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polystree.midtermexam.student.RegisterUiState
import com.polystree.midtermexam.student.StudentViewModel

@Composable
fun RegisterRoute(
    viewModel: StudentViewModel,
    onRegistered: () -> Unit = { }
) {
    val state by viewModel.registerUiState.collectAsState()

    RegisterForm(
        state = state,
        onNameChanged = viewModel::onRegisterNameChanged,
        onPasswordChanged = viewModel::onRegisterPasswordChanged,
        onPhoneChanged = viewModel::onRegisterPhoneChanged,
        onAddressChanged = viewModel::onRegisterAddressChanged,
        onSubmit = viewModel::submitRegistration,
        onRegistered = {
            onRegistered()
            viewModel.resetRegistrationForm()
        }
    )
}

@Composable
fun RegisterForm(
    state: RegisterUiState,
    onNameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit,
    onAddressChanged: (String) -> Unit,
    onSubmit: () -> Boolean,
    onRegistered: () -> Unit = { }
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = state.name,
            onValueChange = onNameChanged,
            label = { Text("Masukkan username") },
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = state.password,
            onValueChange = onPasswordChanged,
            label = { Text("Masukkan password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = state.phone,
            onValueChange = onPhoneChanged,
            label = { Text("Masukkan nomor telepon") },
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = state.address,
            onValueChange = onAddressChanged,
            label = { Text("Masukkan alamat") },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        state.errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = {
                if (onSubmit()) {
                    onRegistered()
                }
            }
        ) {
            Text("Register")
        }
    }
}

@Preview
@Composable
private fun RegisterFormPreview() {
    RegisterForm(
        state = RegisterUiState(name = "Jane Doe"),
        onNameChanged = {},
        onPasswordChanged = {},
        onPhoneChanged = {},
        onAddressChanged = {},
        onSubmit = { true }
    )
}