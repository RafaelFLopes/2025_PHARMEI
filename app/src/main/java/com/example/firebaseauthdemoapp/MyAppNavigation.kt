package com.example.firebaseauthdemoapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebaseauthdemoapp.pages.HomePage
import com.example.firebaseauthdemoapp.pages.LoginPage
import com.example.firebaseauthdemoapp.pages.SignupPage
import com.example.firebaseauthdemoapp.pages.GerenciarPessoasPage
import androidx.compose.runtime.remember

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginPage(modifier, navController, authViewModel)
        }
        composable("signup") {
            SignupPage(modifier, navController, authViewModel)
        }
        composable("home") {
            HomePage(modifier, navController, authViewModel )
        }
        composable("pessoas") {
            val pessoaViewModel = remember { PessoaViewModel() }
            GerenciarPessoasPage(navController, pessoaViewModel)
        }
    }
}
