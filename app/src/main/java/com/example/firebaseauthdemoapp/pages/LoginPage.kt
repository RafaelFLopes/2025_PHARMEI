package com.example.firebaseauthdemoapp.pages

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.firebaseauthdemoapp.AuthState
import com.example.firebaseauthdemoapp.AuthViewModel
import com.example.firebaseauthdemoapp.R

@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate("home")
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFF2F2F2), Color(0xFFF2F2F2))
                )
            )
            .padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        // Logo
        Spacer(modifier = Modifier.height(15.dp))

        Image(
            painter = painterResource(id = R.drawable.logodrogariaazul),
            contentDescription = "Logo",
            modifier = Modifier.height(100.dp)
        )


        Text(
            text = "Faça login para continuar",
            fontSize = 18.sp,
            color = Color(0xFF606060),
        )

        Spacer(modifier = Modifier.height(13.dp))

        // Campo Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = Color(0xFF2EA0DC)) }, // cor do label
            textStyle = TextStyle(color = Color(0xFF2EA0DC)),   // cor do texto digitado
            modifier = Modifier.fillMaxWidth() )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha", color = Color(0xFF2EA0DC)) }, // cor do label
            textStyle = TextStyle(color = Color(0xFF2EA0DC)),   // cor do texto digitado
            modifier = Modifier.fillMaxWidth() )

        Spacer(modifier = Modifier.height(20.dp))

        // Botão Login
        Button(
            onClick = { authViewModel.login(email, password) },
            enabled = authState.value != AuthState.Loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2EA0DC),
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ) {
            Text(text = "Entrar", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Link para cadastro
        TextButton(onClick = { navController.navigate("signup") }) {
            Text(
                text = "Não possui uma conta? Cadastre-se",
                color = Color(0xFF2EA0DC),
                fontSize = 16.sp
            )
        }
    }
}