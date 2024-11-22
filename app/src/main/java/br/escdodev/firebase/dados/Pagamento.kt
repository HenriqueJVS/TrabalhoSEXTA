package br.escdodev.firebase.dados

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pagamento(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null, // Tornar opcional para suportar inclusão
    val titulo: String, // Adicionado, conforme o formulário
    val data: String,
    val valor: Double
)
