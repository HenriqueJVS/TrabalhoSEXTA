package br.escdodev.firebase.dados

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ChecklistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // Substituir em caso de conflito
    suspend fun gravarChecklist(checklist: Checklist)

    @Delete
    suspend fun excluirChecklist(checklist: Checklist)

    @Query("SELECT * FROM Checklist WHERE id = :id")
    suspend fun buscarChecklistPorId(id: Int): Checklist?

    @Query("SELECT * FROM Checklist")
    fun listarChecklists(): Flow<List<Checklist>>
}
