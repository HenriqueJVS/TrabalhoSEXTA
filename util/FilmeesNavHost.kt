package br.escdodev.planner.ui.screens.tarefas.afazares

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.escdodev.planner.R
import br.escdodev.planner.data.Filme
import br.escdodev.planner.ui.util.PlannerTopAppBar

// Telas e Rotas
object Filmes {
  val LISTAR_AFAZERES_ROUTE = "listar_filmes"
  val VISUALIZAR_AFAZER_ROUTE = "visualizar_filme/filmeId"
  val EDITAR_AFAZER_ROUTE = "editar_filme"
  val CRIAR_AFAZER_ROUTE = "criar_filme"
}

@Composable
fun FilmesNavHost(
  openDrawer: () -> Unit,
  tarefasNavBar: @Composable () -> Unit,
  viewModel: FilmesViewModel = hiltViewModel()
) {
  val navController = rememberNavController()
  val filmes = viewModel.filmes.collectAsStateWithLifecycle()

  NavHost(navController, startDestination = Filmes.LISTAR_AFAZERES_ROUTE) {
    composable(Filmes.LISTAR_AFAZERES_ROUTE) {
      FilmesScreen(
        filmes.value,
        {
          PlannerTopAppBar(
            Icons.Default.Menu,
            stringResource(id = R.string.filmes),
            openDrawer
          )
        },
        tarefasNavBar,
        navController
      )
    }
    composable("${Filmes.VISUALIZAR_AFAZER_ROUTE}/{filmeId}") { backStackEntry ->
      val filmeId = backStackEntry.arguments?.getString("filmeId")?.toInt()
      val filme = filmes.value.find { it.id == filmeId }!! //Força não nulo
      VisualizarFilmeScreen(
        filme,
        {
          PlannerTopAppBar(
            Icons.AutoMirrored.Filled.ArrowBack,
            stringResource(id = R.string.detalhes_filme)
          ) {
            navController.popBackStack()
          }
        },
        navController = navController
      )
    }
    composable(Filmes.CRIAR_AFAZER_ROUTE) {
      CriarEditarFilmeScreen(
        Filme(),
        voltarAoSalvar = {
          navController.popBackStack()
        },
        filmesTopBar = {
          PlannerTopAppBar(
            Icons.AutoMirrored.Filled.ArrowBack,
            stringResource(id = R.string.novo_filme)
          ) {
            navController.popBackStack()
          }
        }
      )
    }
    composable("${Filmes.EDITAR_AFAZER_ROUTE}/{filmeId}") { backStackEntry ->
      val filmeId = backStackEntry.arguments?.getString("filmeId")?.toInt()
      val filme = filmes.value.firstOrNull { it.id == filmeId }!!
      CriarEditarFilmeScreen(
        filme,
        voltarAoSalvar = {
          navController.popBackStack()
        },
        filmesTopBar = {
          PlannerTopAppBar(
            Icons.AutoMirrored.Filled.ArrowBack,
            stringResource(id = R.string.editar_filme)
          ) {
            navController.popBackStack()
          }
        }
      )
    }
  }
}