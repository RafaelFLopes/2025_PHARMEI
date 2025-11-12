package com.example.firebaseauthdemoapp.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.firebaseauthdemoapp.AuthState
import com.example.firebaseauthdemoapp.AuthViewModel
import com.example.firebaseauthdemoapp.Pessoa
import com.example.firebaseauthdemoapp.PessoaViewModel
import com.example.firebaseauthdemoapp.R
import kotlinx.coroutines.launch

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val authState = authViewModel.authState.observeAsState()
    val pessoaViewModel = remember { PessoaViewModel() }
    var pessoas by remember { mutableStateOf(listOf<Pessoa>()) }
    val scope = rememberCoroutineScope()

    // Buscar lista de pessoas
    LaunchedEffect(Unit) {
        pessoas = pessoaViewModel.listarPessoas()
    }

    LaunchedEffect(authState.value) {
        if (authState.value is AuthState.Unauthenticated) {
            navController.navigate("login")
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
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(15.dp))

        Image(
            painter = painterResource(id = R.drawable.logodrogariaazul),
            contentDescription = "Logo",
            modifier = Modifier.height(60.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Bem-vindo ao gerenciador de funcionários da +Pharmei",
            fontSize = 20.sp,
            color = Color(0xFF3D3D3D),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // CARD - Gerenciar Pessoas
            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .height(130.dp)
                    .clickable { navController.navigate("pessoas") },
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2EA0DC)),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(R.drawable.engrenagem),
                        contentDescription = "Gerenciar Pessoas",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Gerenciar\nFuncionários",
                        color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // CARD - Sair
            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .height(130.dp)
                    .clickable { authViewModel.signout() },
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFF6B6B)),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(R.drawable.sair),
                        contentDescription = "Sair da conta",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Sair\nda Conta",
                        color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // --- Lista de Funcionários ---
        Text(
            text = "Funcionários cadastrados",
            color = Color(0xFF3B3B3B),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(15.dp))


        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(pessoas) { pessoa ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFD3D3D3)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("${pessoa.nome}", color = Color(0xFF181818), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("Idade: ${pessoa.idade}", color = Color(0xFF181818), fontSize = 14.sp)
                        Text("Email: ${pessoa.email}", color = Color(0xFF181818), fontSize = 14.sp)
                    }
                }
            }
        }
    }
}
