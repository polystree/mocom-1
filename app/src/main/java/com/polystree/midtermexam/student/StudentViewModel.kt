package com.polystree.midtermexam.student

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class RegisterUiState(
    val name: String = "",
    val password: String = "",
    val phone: String = "",
    val address: String = "",
    val errorMessage: String? = null
)

class StudentViewModel : ViewModel() {
    private val _students = MutableStateFlow(initialStudents)
    val students: StateFlow<List<Student>> = _students.asStateFlow()

    private val _registerUiState = MutableStateFlow(RegisterUiState())
    val registerUiState: StateFlow<RegisterUiState> = _registerUiState.asStateFlow()

    fun onRegisterNameChanged(value: String) {
        _registerUiState.update { it.copy(name = value, errorMessage = null) }
    }

    fun onRegisterPasswordChanged(value: String) {
        _registerUiState.update { it.copy(password = value, errorMessage = null) }
    }

    fun onRegisterPhoneChanged(value: String) {
        _registerUiState.update { it.copy(phone = value, errorMessage = null) }
    }

    fun onRegisterAddressChanged(value: String) {
        _registerUiState.update { it.copy(address = value, errorMessage = null) }
    }

    fun submitRegistration(): Boolean {
        val current = _registerUiState.value
        if (current.name.isBlank() || current.password.isBlank()) {
            _registerUiState.update { it.copy(errorMessage = "Name and password must not be empty") }
            return false
        }

        val newStudent = Student(
            id = (_students.value.size + 1).toString().padStart(3, '0'),
            name = current.name,
            password = current.password,
            phone = current.phone,
            address = current.address
        )

        _students.update { existing -> existing + newStudent }
        resetRegistrationForm()
        return true
    }

    fun resetRegistrationForm() {
        _registerUiState.value = RegisterUiState()
    }
}
