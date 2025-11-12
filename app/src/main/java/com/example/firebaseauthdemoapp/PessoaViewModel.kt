package com.example.firebaseauthdemoapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class Pessoa(
    var id: String = "",
    var nome: String = "",
    var idade: Int = 0,
    var email: String = ""
)

class PessoaViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val pessoasCollection = db.collection("pessoas")

    fun adicionarPessoa(pessoa: Pessoa, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val docRef = pessoasCollection.add(pessoa).await()
                pessoasCollection.document(docRef.id).update("id", docRef.id)
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun atualizarPessoa(pessoa: Pessoa, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                pessoasCollection.document(pessoa.id).set(pessoa).await()
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun excluirPessoa(id: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                pessoasCollection.document(id).delete().await()
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    suspend fun listarPessoas(): List<Pessoa> {
        val snapshot = pessoasCollection.get().await()
        return snapshot.toObjects(Pessoa::class.java)
    }
}
