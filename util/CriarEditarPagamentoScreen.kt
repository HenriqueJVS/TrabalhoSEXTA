package br.escdodev.firebase.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.escdodev.firebase.dados.Pagamento

@Composable
fun CriarEditarPagamentoScreen(
    pagamentoId: Int,
    voltarAoSalvar: () -> Unit,
    navController: NavController,
    viewModel: PagamentoViewModel
) {
    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }

    LaunchedEffect(pagamentoId) {
        val pagamento = viewModel.buscarPagamentoPorId(pagamentoId)
        pagamento?.let {
            titulo = it.titulo
            descricao = it.descricao
            valor = it.valor.toString()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val valorDouble = valor.toDoubleOrNull() ?: 0.0
                viewModel.gravarPagamento(
                    Pagamento(
                        id = pagamentoId,
                        titulo = titulo,
                        descricao = descricao,
                        valor = valorDouble
                    )
                )
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
            Text("Editar Pagamento", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = valor,
                onValueChange = { valor = it },
                label = { Text("Valor (R$)") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
