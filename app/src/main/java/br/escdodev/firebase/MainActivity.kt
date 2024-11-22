package br.escdodev.firebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.escdodev.firebase.dados.FilmeDatabase
import br.escdodev.firebase.dados.LocalRepository
import br.escdodev.firebase.dados.MinimalAPIRepository
import br.escdodev.firebase.dados.RemoteRepository
import br.escdodev.firebase.ui.FilmesNavHost
import br.escdodev.firebase.ui.ChecklistViewModel
import br.escdodev.firebase.ui.FilmeViewModel
import br.escdodev.firebase.ui.PagamentoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val isLocal = true // Alterne entre local e remoto

        // Inicialize o banco de dados
        val db = FilmeDatabase.abrirBancoDeDados(this)
        val localRepository = LocalRepository(
            filmeDao = db.filmeDao(),
            checklistDao = db.checkListDao(), // Passe o checklistDao
            pagamentoDao = db.pagamentoDao() // Certifique-se de usar o método correto
        )
        val remoteRepository = RemoteRepository()
        val minimalAPIRepository = MinimalAPIRepository()

        // Inicialize os ViewModels
        val filmeViewModel: FilmeViewModel
        val checklistViewModel: ChecklistViewModel
        val pagamentoViewModel: PagamentoViewModel

        if (isLocal) {
            filmeViewModel = FilmeViewModel(localRepository)
            checklistViewModel = ChecklistViewModel(localRepository)
            pagamentoViewModel = PagamentoViewModel(localRepository) // Adicionado
        } else {
            filmeViewModel = FilmeViewModel(remoteRepository)
            checklistViewModel = ChecklistViewModel(remoteRepository)
            pagamentoViewModel = PagamentoViewModel(remoteRepository) // Adicionado
        }

        setContent {
            FilmesNavHost(
                filmeViewModel = filmeViewModel,
                checklistViewModel = checklistViewModel, // Passe o ChecklistViewModel
                pagamentoViewModel = pagamentoViewModel // Certifique-se de passar o PagamentoViewModel
            )
        }
    }
}
