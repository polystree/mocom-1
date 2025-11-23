package com.polystree.midtermexam.student

data class Student(
    val id: String,
    val name: String,
    val password: String,
    val phone: String,
    val address: String
)

val initialStudents = listOf(
    Student("001", "John Doe", "password123", "123-456-7890", "123 Main St"),
    Student("002", "Jane Smith", "password123", "123-456-7890", "123 Main St"),
    Student("003", "Bob Johnson", "password123", "123-456-7890", "123 Main St"),
    Student("004", "Alice Williams", "password123", "123-456-7890", "123 Main St"),
    Student("005", "Michael Brown", "password123", "123-456-7890", "123 Main St")
)
