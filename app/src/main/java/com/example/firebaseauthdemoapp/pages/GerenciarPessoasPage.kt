package com.example.firebaseauthdemoapp.pages

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.firebaseauthdemoapp.Pessoa
import com.example.firebaseauthdemoapp.PessoaViewModel
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import com.example.firebaseauthdemoapp.R

@Composable
fun GerenciarPessoasPage(navController: NavController, pessoaViewModel: PessoaViewModel) {
    var nome by remember { mutableStateOf("") }
    var idade by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pessoas by remember { mutableStateOf(listOf<Pessoa>()) }
    var pessoaSelecionada by remember { mutableStateOf<Pessoa?>(null) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        pessoas = pessoaViewModel.listarPessoas()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF2EA0DC), Color(0xFF2EA0DC))
                )
            )
            .padding(24.dp),
    ) {

        TextButton(onClick = {
            navController.navigate("home")
        }) {
            Text(text = "< Voltar", color = Color.White, fontSize = 18.sp, modifier = Modifier.padding(bottom = 5.dp))
        }
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center,
        ) {
            Text(
                text = "Gerenciar +Pharmei",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 2.dp)
            )
        }
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            Text(
                text = "Gerencie os funcionários da empresa",
                color = Color(0xFFF5F5F5),
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 15.dp)
            )
        }

        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome", color = Color.White) }, // cor do label
            textStyle = TextStyle(color = Color.White),   // cor do texto digitado
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = idade,
            onValueChange = { idade = it },
            label = { Text("Idade", color = Color.White) }, // cor do label
            textStyle = TextStyle(color = Color.White),   // cor do texto digitado
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = Color.White) }, // cor do label
            textStyle = TextStyle(color = Color.White),   // cor do texto digitado
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (nome.isNotBlank() && idade.isNotBlank() && email.isNotBlank()) {
                    val idadeInt = idade.toIntOrNull() ?: 0
                    if (pessoaSelecionada == null) {
                        // Adiciona nova pessoa
                        val pessoa = Pessoa(nome = nome, idade = idadeInt, email = email)
                        pessoaViewModel.adicionarPessoa(pessoa) { sucesso ->
                            Toast.makeText(
                                context,
                                if (sucesso) "Pessoa adicionada!" else "Erro!",
                                Toast.LENGTH_SHORT
                            ).show()
                            scope.launch { pessoas = pessoaViewModel.listarPessoas() }
                            nome = ""; idade = ""; email = ""
                        }
                    } else {
                        // Atualiza pessoa existente
                        val pessoaAtualizada =
                            pessoaSelecionada!!.copy(nome = nome, idade = idadeInt, email = email)
                        pessoaViewModel.atualizarPessoa(pessoaAtualizada) { sucesso ->
                            Toast.makeText(
                                context,
                                if (sucesso) "Atualizado!" else "Erro!",
                                Toast.LENGTH_SHORT
                            ).show()
                            scope.launch { pessoas = pessoaViewModel.listarPessoas() }
                            nome = ""; idade = ""; email = ""
                            pessoaSelecionada = null
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(0xFF358CBE)
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
        ) {
            Text(if (pessoaSelecionada == null) "Adicionar funcionário" else "Salvar alterações", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(19.dp))

        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center,
        ) {
            Text(
                text = "Lista de funcionários",
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 5.dp)
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(pessoas) { pessoa ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2E2E2E)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("${pessoa.nome}", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("Idade: ${pessoa.idade}", color = Color.LightGray, fontSize = 14.sp)
                        Text("Email: ${pessoa.email}", color = Color.LightGray, fontSize = 14.sp)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(onClick = {
                                nome = pessoa.nome
                                idade = pessoa.idade.toString()
                                email = pessoa.email
                                pessoaViewModel.atualizarPessoa(
                                    pessoa.copy(nome = nome, idade = idade.toInt(), email = email)
                                ) { sucesso ->
                                    Toast.makeText(
                                        context,
                                        if (sucesso) "Atualizado!" else "Erro!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    scope.launch { pessoas = pessoaViewModel.listarPessoas() }
                                }
                            }) {                                TextButton(onClick = {
                                    nome = pessoa.nome
                                    idade = pessoa.idade.toString()
                                    email = pessoa.email
                                    pessoaSelecionada = pessoa // marca como selecionada
                                }) {
                                    Text("Editar", color = Color(0xFF2EA0DC))
                                }

                            TextButton(onClick = {
                                pessoaViewModel.excluirPessoa(pessoa.id) { sucesso ->
                                    Toast.makeText(
                                        context,
                                        if (sucesso) "Excluído!" else "Erro!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    scope.launch { pessoas = pessoaViewModel.listarPessoas() }
                                }
                            }) {
                                Text("Excluir", color = Color(0xFFFF6B6B))
                            }
                        }
                    }
                }
            }
        }
    }
}
}