package br.escdodev.firebase.dados

import kotlinx.coroutines.flow.Flow

class MinimalAPIRepository : IRepository {

    // Métodos para filmes
    override fun listarFilmes(): Flow<List<Filme>> {
        TODO("Not yet implemented")
    }

    override suspend fun buscarFilmePorId(idx: Int): Filme {
        TODO("Not yet implemented")
    }

    override suspend fun gravarFilme(filme: Filme) {
        TODO("Not yet implemented")
    }

    override suspend fun excluirFilme(filme: Filme) {
        TODO("Not yet implemented")
    }

    // Métodos para checklist
    override fun listarChecklists(): Flow<List<Checklist>> {
        TODO("Not yet implemented")
    }

    override suspend fun buscarChecklistPorId(id: Int): Checklist {
        TODO("Not yet implemented")
    }

    override suspend fun gravarChecklist(checklist: Checklist) {
        TODO("Not yet implemented")
    }

    override suspend fun excluirChecklist(checklist: Checklist) {
        TODO("Not yet implemented")
    }

    override fun listarPagamentos(): Flow<List<Pagamento>> {
        TODO("Not yet implemented")
    }

    override suspend fun gravarPagamento(pagamento: Pagamento) {
        TODO("Not yet implemented")
    }

    override suspend fun excluirPagamento(pagamento: Pagamento) {
        TODO("Not yet implemented")
    }

    override suspend fun buscarPagamentoPorId(id: Int?): Pagamento? {
        TODO("Not yet implemented")
    }
}
