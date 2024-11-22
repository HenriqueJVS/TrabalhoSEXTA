package br.escdodev.firebase.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun FilmesNavHost(
    filmeViewModel: FilmeViewModel,
    checklistViewModel: ChecklistViewModel,
    pagamentoViewModel: PagamentoViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "listarFilmes"
    ) {
        // Rotas para filmes
        composable("listarFilmes") {
            ListarFilmesScreen(
                viewModel = filmeViewModel,
                navController = navController
            )
        }
        composable("incluirFilmes") {
            IncluirEditarFilmeScreen(
                viewModel = filmeViewModel,
                navController = navController
            )
        }
        composable("editarFilme/{filmeId}") { navRequest ->
            val filmeId = navRequest.arguments?.getString("filmeId")?.toIntOrNull()
            filmeId?.let {
                IncluirEditarFilmeScreen(
                    filmeId = it,
                    viewModel = filmeViewModel,
                    navController = navController
                )
            } ?: navController.popBackStack()
        }
        composable("detalhesFilme/{filmeId}") { navBackStack ->
            val filmeId = navBackStack.arguments?.getString("filmeId")?.toIntOrNull()
            filmeId?.let {
                val filme = filmeViewModel.buscarPorId(it)
                filme?.let {
                    DetalhesFilmeScreen(
                        filme = it,
                        navController = navController
                    )
                }
            } ?: navController.popBackStack()
        }

        // Rotas para checklist
        composable("listagemChecklist") {
            ListarChecklistScreen(
                viewModel = checklistViewModel,
                navController = navController
            )
        }
        composable("incluirChecklist") {
            IncluirEditarChecklistScreen(
                viewModel = checklistViewModel,
                navController = navController
            )
        }
        composable("editarChecklist/{checklistId}") { navRequest ->
            val checklistId = navRequest.arguments?.getString("checklistId")?.toIntOrNull()
            checklistId?.let {
                IncluirEditarChecklistScreen(
                    checklistId = it,
                    viewModel = checklistViewModel,
                    navController = navController
                )
            } ?: navController.popBackStack()
        }

        // Rotas para pagamentos
        composable("pagamento") {
            PagamentoScreen(
                viewModel = pagamentoViewModel,
                navController = navController
            )
        }
        composable("incluirPagamento") {
            IncluirEditarPagamentoScreen(
                pagamentoId = null,
                viewModel = pagamentoViewModel,
                navController = navController
            )
        }
        composable("editarPagamento/{pagamentoId}") { navRequest ->
            val pagamentoId = navRequest.arguments?.getString("pagamentoId")?.toIntOrNull()
            pagamentoId?.let {
                IncluirEditarPagamentoScreen(
                    pagamentoId = it,
                    viewModel = pagamentoViewModel,
                    navController = navController
                )
            } ?: navController.popBackStack()
        }
    }
}
