package br.escdodev.planner.ui.util

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlannerTopAppBar(
  icon: ImageVector,
  titulo: String,
  onClickAction: () -> Unit
) {
  TopAppBar(
    title = {
      Text(
        text = titulo,
        fontWeight = FontWeight(600),
        color = Color.White
      )
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = Color.DarkGray
    ),
    navigationIcon = {
      IconButton(onClick = { onClickAction() }) {
        Icon(
          imageVector = icon,
          contentDescription = "Menu",
          tint = Color.White
        )
      }
    }
  )
}