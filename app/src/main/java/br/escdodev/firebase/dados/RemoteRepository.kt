package br.escdodev.firebase.dados

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class RemoteRepository : IRepository {

    private val db = FirebaseFirestore.getInstance()
    private val filmeCollection = db.collection("filmes")
    private val checklistCollection = db.collection("checklists")
    private val pagamentoCollection = db.collection("pagamentos") // Adicionado para pagamentos

    // Função reativa para listar filmes
    override fun listarFilmes(): Flow<List<Filme>> = callbackFlow {
        val listener = filmeCollection.addSnapshotListener { dados, erros ->
            if (erros != null) {
                close(erros)
                return@addSnapshotListener
            }
            if (dados != null) {
                val filmes = dados.documents.mapNotNull { dado ->
                    dado.toObject(Filme::class.java)
                }
                trySend(filmes).isSuccess
            }
        }
        awaitClose { listener.remove() }
    }

    // Função reativa para listar checklists
    override fun listarChecklists(): Flow<List<Checklist>> = callbackFlow {
        val listener = checklistCollection.addSnapshotListener { dados, erros ->
            if (erros != null) {
                close(erros)
                return@addSnapshotListener
            }
            if (dados != null) {
                val checklists = dados.documents.mapNotNull { dado ->
                    dado.toObject(Checklist::class.java)
                }
                trySend(checklists).isSuccess
            }
        }
        awaitClose { listener.remove() }
    }

    // Função reativa para listar pagamentos
    override fun listarPagamentos(): Flow<List<Pagamento>> = callbackFlow {
        val listener = pagamentoCollection.addSnapshotListener { dados, erros ->
            if (erros != null) {
                close(erros)
                return@addSnapshotListener
            }
            if (dados != null) {
                val pagamentos = dados.documents.mapNotNull { dado ->
                    dado.toObject(Pagamento::class.java)
                }
                trySend(pagamentos).isSuccess
            }
        }
        awaitClose { listener.remove() }
    }

    // Obter o próximo ID para filmes
    private suspend fun getFilmeId(): Int {
        val dados = filmeCollection.get().await()
        val maxId = dados.documents.mapNotNull {
            it.getLong("id")?.toInt()
        }.maxOrNull() ?: 0
        return maxId + 1
    }

    // Obter o próximo ID para checklists
    private suspend fun getChecklistId(): Int {
        val dados = checklistCollection.get().await()
        val maxId = dados.documents.mapNotNull {
            it.getLong("id")?.toInt()
        }.maxOrNull() ?: 0
        return maxId + 1
    }

    // Obter o próximo ID para pagamentos
    private suspend fun getPagamentoId(): Int {
        val dados = pagamentoCollection.get().await()
        val maxId = dados.documents.mapNotNull {
            it.getLong("id")?.toInt()
        }.maxOrNull() ?: 0
        return maxId + 1
    }

    // Gravar filme
    override suspend fun gravarFilme(filme: Filme) {
        val document: DocumentReference
        if (filme.id == null) {
            filme.id = getFilmeId()
            document = filmeCollection.document(filme.id.toString())
        } else {
            document = filmeCollection.document(filme.id.toString())
        }
        document.set(filme).await()
    }

    // Gravar checklist
    override suspend fun gravarChecklist(checklist: Checklist) {
        val document: DocumentReference
        if (checklist.id == null) {
            document = checklistCollection.document() // Firebase gera um ID único automaticamente
            checklist.id = document.id.hashCode() // Use um hash do ID gerado como valor único
        } else {
            document = checklistCollection.document(checklist.id.toString())
        }
        document.set(checklist).await()
    }

    // Gravar pagamento
    override suspend fun gravarPagamento(pagamento: Pagamento) {
        val document: DocumentReference
        if (pagamento.id == 0) { // Para um novo pagamento
            pagamento.id = getPagamentoId()
            document = pagamentoCollection.document(pagamento.id.toString())
        } else {
            document = pagamentoCollection.document(pagamento.id.toString())
        }
        document.set(pagamento).await()
    }

    // Buscar filme por ID
    override suspend fun buscarFilmePorId(idx: Int): Filme? {
        val dados = filmeCollection.document(idx.toString()).get().await()
        return dados.toObject(Filme::class.java)
    }

    // Buscar checklist por ID
    override suspend fun buscarChecklistPorId(id: Int): Checklist? {
        val dados = checklistCollection.document(id.toString()).get().await()
        return dados.toObject(Checklist::class.java)
    }

    // Excluir filme
    override suspend fun excluirFilme(filme: Filme) {
        filmeCollection.document(filme.id.toString()).delete().await()
    }

    // Excluir checklist
    override suspend fun excluirChecklist(checklist: Checklist) {
        checklistCollection.document(checklist.id.toString()).delete().await()
    }

    // Excluir pagamento
    override suspend fun excluirPagamento(pagamento: Pagamento) {
        pagamentoCollection.document(pagamento.id.toString()).delete().await()
    }

    override suspend fun buscarPagamentoPorId(id: Int?): Pagamento? {

        return TODO("Provide the return value")
    }
}
