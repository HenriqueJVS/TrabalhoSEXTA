package br.escdodev.planner.ui.screens.tarefas.afazares

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.escdodev.planner.data.Filme
import br.escdodev.planner.ui.theme.corVerdeLeve

@Composable
fun VisualizarFilmeScreen(
  filme: Filme,
  filmesTopBar: @Composable () -> Unit,
  navController: NavController
) {

  Scaffold(
    topBar = { filmesTopBar() },
    content = { innerPadding ->
      Column(
        Modifier
          .padding(innerPadding)
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        DisplayTextField(
          text = filme.titulo,
          label = "Título"
        )
        Spacer(modifier = Modifier.height(20.dp))
        DisplayTextField(
          text = filme.descricao,
          label = "Descrição"
        )

      }
    },
    floatingActionButton = {
      FloatingActionButton(
        shape = CircleShape,
        containerColor = corVerdeLeve,
        contentColor = Color.White,
        onClick = {
          navController.navigate("${Filmes.EDITAR_AFAZER_ROUTE}/${filme.id}")
        }
      ) {
        Icon(Icons.Filled.Edit, "Editar")
      }
    }
  )
}

@Composable
fun DisplayTextField(
  text: String,
  label: String,
  modifier: Modifier = Modifier,
) {
  val fontSize = 20.sp

  Column(modifier = modifier.fillMaxWidth()) {
    Text(
      text = label, fontSize = 15.sp, fontWeight = FontWeight(500),
      modifier = Modifier.padding(0.dp, 8.dp)
    )
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, Color.Gray)
        .padding(16.dp)
    ) {
      Text(
        text = text,
        fontSize = fontSize,
        color = Color.Black
      )
    }
  }
}