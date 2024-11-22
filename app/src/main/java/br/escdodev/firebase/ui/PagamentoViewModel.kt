package br.escdodev.firebase.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.escdodev.firebase.dados.IRepository
import br.escdodev.firebase.dados.Pagamento
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PagamentoViewModel(private val repository: IRepository) : ViewModel() {

    private val _pagamentos = MutableStateFlow<List<Pagamento>>(emptyList())
    val pagamentos: StateFlow<List<Pagamento>> = _pagamentos

    init {
        carregarPagamentos()
    }

    private fun carregarPagamentos() {
        viewModelScope.launch {
            repository.listarPagamentos().collect { lista ->
                _pagamentos.value = lista
            }
        }
    }

    suspend fun gravarPagamento(pagamento: Pagamento) {
        repository.gravarPagamento(pagamento)
    }

    suspend fun excluirPagamento(pagamento: Pagamento) {
        repository.excluirPagamento(pagamento)
    }

    suspend fun buscarPagamentoPorId(id: Int): Pagamento? {
        return repository.buscarPagamentoPorId(id)
    }
}
