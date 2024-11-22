package br.escdodev.firebase.dados

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PagamentoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // Substituir em caso de conflito
    suspend fun gravarPagamento(pagamento: Pagamento)

    @Delete
    suspend fun excluirPagamento(pagamento: Pagamento)

    @Query("SELECT * FROM Pagamento WHERE id = :id")
    suspend fun buscarPagamentoPorId(id: Int?): Pagamento?

    @Query("SELECT * FROM Pagamento")
    fun listarPagamentos(): Flow<List<Pagamento>>
}
