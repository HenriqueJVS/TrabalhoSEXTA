package br.escdodev.firebase.dados

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import br.escdodev.firebase.dados.Filme

@Dao
interface FilmeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun gravarFilme(filme: Filme)

    @Delete
    suspend fun excluirFilme(filme: Filme)

    @Query("SELECT * FROM Filme WHERE id = :id")
    suspend fun buscarFilmePorId(id: Int): Filme?

    @Query("SELECT * FROM Filme")
    fun listarFilmes(): Flow<List<Filme>>
}
