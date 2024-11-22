package br.escdodev.firebase.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.escdodev.firebase.dados.Filme

@Composable
fun CriarEditarFilmeScreen(
  filme: Filme,
  voltarAoSalvar: () -> Unit,
  navController: NavController,
  viewModel: FilmeViewModel
) {
  var titulo by remember { mutableStateOf(filme.titulo) }
  var descricao by remember { mutableStateOf(filme.descricao) }

  Scaffold(
    floatingActionButton = {
      FloatingActionButton(onClick = {
        viewModel.gravar(Filme(id = filme.id, titulo = titulo, descricao = descricao))
        voltarAoSalvar()
      }) {
        Icon(Icons.Filled.Check, contentDescription = "Salvar")
      }
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .padding(paddingValues)
        .padding(16.dp)
        .fillMaxSize(),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      Text("Editar Filme", fontWeight = FontWeight.Bold, fontSize = 24.sp)
      BasicTextField(
        value = titulo,
        onValueChange = { titulo = it },
        modifier = Modifier.fillMaxWidth()
      )
      BasicTextField(
        value = descricao,
        onValueChange = { descricao = it },
        modifier = Modifier.fillMaxWidth()
      )
    }
  }
}
