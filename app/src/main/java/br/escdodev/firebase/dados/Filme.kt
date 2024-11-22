package br.escdodev.firebase.dados

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Filme(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null, // ID gerado automaticamente
    val titulo: String,
    val descricao: String,
    val nota: String = "",
    val concluido: Boolean = false
)
