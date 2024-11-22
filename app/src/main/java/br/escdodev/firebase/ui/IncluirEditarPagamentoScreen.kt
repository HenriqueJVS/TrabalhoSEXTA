package br.escdodev.firebase.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import br.escdodev.firebase.dados.Pagamento
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncluirEditarPagamentoScreen(
    pagamentoId: Int? = null, // ID para edição ou nulo para inclusão
    viewModel: PagamentoViewModel,
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()

    // Estados para os campos
    var titulo by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var dataPagamento by remember { mutableStateOf("") }
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    // Carregar os dados se for edição
    LaunchedEffect(pagamentoId) {
        if (pagamentoId != null) {
            val pagamento = viewModel.buscarPagamentoPorId(pagamentoId)
            pagamento?.let {
                titulo = it.titulo
                valor = it.valor.toString()
                dataPagamento = it.data
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (pagamentoId == null) "Adicionar Pagamento" else "Editar Pagamento",
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = valor,
                    onValueChange = { valor = it },
                    label = { Text("Valor") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = dataPagamento,
                    onValueChange = { dataPagamento = it },
                    label = { Text("Data de Pagamento (dd/MM/yyyy)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
            Button(
                onClick = {
                    coroutineScope.launch {
                        val pagamento = Pagamento(
                            id = pagamentoId ?: 0,
                            titulo = titulo,
                            valor = valor.toDoubleOrNull() ?: 0.0,
                            data = dataPagamento
                        )
                        viewModel.gravarPagamento(pagamento)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (pagamentoId == null) "Adicionar" else "Salvar")
            }
        }
    }
}
