package br.escdodev.firebase.dados

import kotlinx.coroutines.flow.Flow

class LocalRepository(
    private val filmeDao: FilmeDao,
    private val checklistDao: ChecklistDao,
    private val pagamentoDao: PagamentoDao // Adicionado para Pagamentos
) : IRepository {
    // Métodos para filmes
    override fun listarFilmes(): Flow<List<Filme>> = filmeDao.listarFilmes()
    override suspend fun buscarFilmePorId(id: Int): Filme? = filmeDao.buscarFilmePorId(id)
    override suspend fun gravarFilme(filme: Filme) {
        if (filme.id == null) {
            filmeDao.gravarFilme(filme)
        } else {
            val existente = filmeDao.buscarFilmePorId(filme.id!!)
            if (existente == null) {
                filmeDao.gravarFilme(filme)
            } else {
                filmeDao.gravarFilme(filme)
            }
        }
    }

    override suspend fun excluirFilme(filme: Filme) = filmeDao.excluirFilme(filme)

    // Métodos para checklist
    override fun listarChecklists(): Flow<List<Checklist>> = checklistDao.listarChecklists()
    override suspend fun buscarChecklistPorId(id: Int): Checklist? = checklistDao.buscarChecklistPorId(id)
    override suspend fun gravarChecklist(checklist: Checklist) {
        if (checklist.id == null) {
            checklistDao.gravarChecklist(checklist)
        } else {
            val existente = checklistDao.buscarChecklistPorId(checklist.id!!)
            if (existente != null) {
                checklistDao.gravarChecklist(checklist)
            }
        }
    }

    override suspend fun excluirChecklist(checklist: Checklist) = checklistDao.excluirChecklist(checklist)

    // Métodos para pagamento
    override fun listarPagamentos(): Flow<List<Pagamento>> = pagamentoDao.listarPagamentos()

    override suspend fun buscarPagamentoPorId(id: Int?): Pagamento? {
        return id?.let { pagamentoDao.buscarPagamentoPorId(it) }
    }

    override suspend fun gravarPagamento(pagamento: Pagamento) {
        if (pagamento.id == 0) { // Se id for 0, cria um novo registro
            pagamentoDao.gravarPagamento(pagamento)
        } else {
            val existente = pagamentoDao.buscarPagamentoPorId(pagamento.id)
            if (existente == null) {
                pagamentoDao.gravarPagamento(pagamento)
            } else {
                pagamentoDao.gravarPagamento(pagamento)
            }
        }
    }

    override suspend fun excluirPagamento(pagamento: Pagamento) = pagamentoDao.excluirPagamento(pagamento)
}
