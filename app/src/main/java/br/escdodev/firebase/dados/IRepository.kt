package br.escdodev.firebase.dados

import kotlinx.coroutines.flow.Flow

interface IRepository {
    // Métodos para filmes
    fun listarFilmes(): Flow<List<Filme>>
    suspend fun buscarFilmePorId(id: Int): Filme?
    suspend fun gravarFilme(filme: Filme)
    suspend fun excluirFilme(filme: Filme)

    // Métodos para checklist
    fun listarChecklists(): Flow<List<Checklist>>
    suspend fun buscarChecklistPorId(id: Int): Checklist?
    suspend fun gravarChecklist(checklist: Checklist)
    suspend fun excluirChecklist(checklist: Checklist)

    // Métodos para pagamento
    fun listarPagamentos(): Flow<List<Pagamento>>
    suspend fun gravarPagamento(pagamento: Pagamento)
    suspend fun excluirPagamento(pagamento: Pagamento)
    suspend fun buscarPagamentoPorId(id: Int?): Pagamento?
}
