package br.escdodev.firebase.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.escdodev.firebase.dados.Checklist
import kotlinx.coroutines.launch

@Composable
fun ChecklistScreen(
    checklists: List<Checklist>,
    checklistTopBar: @Composable () -> Unit,
    checklistNavBar: @Composable () -> Unit,
    navController: NavController,
    viewModel: ChecklistViewModel
) {
    val currentBackStack = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.value?.destination?.route ?: "listagemChecklist"

    Scaffold(
        topBar = { checklistTopBar() },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(130.dp))
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(checklists) { checklist ->
                        ChecklistItem(
                            checklist = checklist,
                            onChecklistCheck = { completed ->
                                viewModel.atualizarChecklist(checklist, completed)
                            },
                            onChecklistClick = {
                                navController.navigate("editarChecklist/${checklist.id}")
                            },
                            onDelete = {
                                viewModel.excluirChecklist(checklist)
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(130.dp))
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                onClick = { navController.navigate("incluirChecklist") }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Adicionar Checklist")
            }
        },
        bottomBar = {
            if (currentRoute == "listagemChecklist") {
                checklistNavBar()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChecklistItem(
    checklist: Checklist,
    onChecklistCheck: (Boolean) -> Unit,
    onChecklistClick: (Checklist) -> Unit,
    onDelete: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val deslocPx = with(LocalDensity.current) { (-50).dp.toPx() }
    val swipeState = rememberSwipeableState(0)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onChecklistClick(checklist) }
            .swipeable(
                state = swipeState,
                anchors = mapOf(0f to 0, -1000f to 1),
                orientation = Orientation.Horizontal
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (swipeState.offset.value > deslocPx) {
                Checkbox(
                    checked = checklist.concluido,
                    onCheckedChange = { onChecklistCheck(it) },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.height(50.dp)
                )
            } else {
                IconButton(
                    onClick = {
                        onDelete()
                        scope.launch { swipeState.snapTo(0) }
                    },
                    modifier = Modifier.height(50.dp)
                ) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Excluir",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = checklist.titulo,
                fontSize = 20.sp
            )
        }
    }
}
