package br.escdodev.firebase.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.escdodev.firebase.dados.Checklist
import br.escdodev.firebase.dados.Filme
import br.escdodev.firebase.dados.IRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FilmeViewModel(
    private val repository: IRepository
) : ViewModel() {
    private val _filmes = MutableStateFlow<List<Filme>>(emptyList())
    val filmes: StateFlow<List<Filme>> get() = _filmes

    init {
        viewModelScope.launch {
            repository.listarFilmes().collect { listaDeFilmes ->
                _filmes.value = listaDeFilmes
            }
        }
    }

    fun excluir(filme: Filme) {
        viewModelScope.launch {
            repository.excluirFilme(filme)
        }
    }

    fun gravar(filme: Filme) {
        viewModelScope.launch {
            repository.gravarFilme(filme)
        }
    }

    // Novo método para buscar filme por ID
    fun buscarPorId(id: Int): Filme? {
        return runBlocking {
            repository.buscarFilmePorId(id)
        }
    }
}