package br.escdodev.firebase.dados

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Checklist(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val titulo: String,
    val descricao: String = "",
    val concluido: Boolean = false
)
