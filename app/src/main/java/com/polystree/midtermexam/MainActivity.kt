package com.polystree.midtermexam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.polystree.midtermexam.ui.theme.MidtermExamTheme
import com.polystree.midtermexam.login.LoginRoute
import com.polystree.midtermexam.login.LoginViewModel
import com.polystree.midtermexam.student.StudentViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MidtermExamTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNav(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController
                    )
                }
            }
        }
    }
}

private sealed class Destination(val route: String) {
    data object Login : Destination("login")
    data object Dashboard : Destination("dashboard")
    data object Register : Destination("register")
}

@Composable
fun AppNav(modifier: Modifier, navController: NavHostController) {
    val loginViewModel: LoginViewModel = viewModel()
    val studentViewModel: StudentViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Destination.Login.route,
        modifier = modifier
    ) {
        composable(Destination.Login.route) {
            LoginRoute(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate(Destination.Dashboard.route) {
                        popUpTo(Destination.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Destination.Dashboard.route) {
            DashboardRoute(viewModel = studentViewModel)
        }
        composable(Destination.Register.route) {
            RegisterRoute(
                viewModel = studentViewModel,
                onRegistered = { navController.popBackStack() }
            )
        }
    }
}