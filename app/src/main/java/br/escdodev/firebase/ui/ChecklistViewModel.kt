package br.escdodev.firebase.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.escdodev.firebase.dados.Checklist
import br.escdodev.firebase.dados.IRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ChecklistViewModel(
    private val repository: IRepository
) : ViewModel() {
    private val _checklists = MutableStateFlow<List<Checklist>>(emptyList())
    val checklists: StateFlow<List<Checklist>> get() = _checklists

    init {
        viewModelScope.launch {
            repository.listarChecklists().collect { listaDeChecklists ->
                _checklists.value = listaDeChecklists
            }
        }
    }

    fun excluirChecklist(item: Checklist) {
        viewModelScope.launch {
            repository.excluirChecklist(item)
        }
    }

    fun gravarChecklist(checklist: Checklist) {
        viewModelScope.launch {
            repository.gravarChecklist(checklist)
        }
    }


    // Novo método para buscar checklist por ID
    fun buscarChecklistPorId(id: Int): Checklist? {
        return runBlocking {
            repository.buscarChecklistPorId(id)
        }
    }
}
