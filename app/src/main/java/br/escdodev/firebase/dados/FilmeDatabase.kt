package br.escdodev.firebase.dados

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Filme::class, Checklist::class, Pagamento::class], // Inclua todas as entidades
    version = 5, // Atualize a versão do banco
    exportSchema = true // Permite exportação do esquema (opcional)
)
abstract class FilmeDatabase : RoomDatabase() {
    abstract fun filmeDao(): FilmeDao
    abstract fun checkListDao(): ChecklistDao
    abstract fun pagamentoDao(): PagamentoDao

    companion object {
        @Volatile
        private var INSTANCE: FilmeDatabase? = null

        fun abrirBancoDeDados(context: Context): FilmeDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FilmeDatabase::class.java,
                    "filme_database"
                )
                    .addMigrations(MIGRATION_2_3, MIGRATION_3_4) // Adicione as migrações necessárias
                    .fallbackToDestructiveMigration() // Use fallback destrutivo para limpar o banco se necessário
                    .build()
                    .also { INSTANCE = it }
            }
        }

        // Migração da versão 2 para a versão 3 (criando a tabela Pagamento)
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS Pagamento (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        titulo TEXT NOT NULL,
                        data TEXT NOT NULL,
                        valor REAL NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }

        // Migração da versão 3 para a versão 4 (alteração no esquema, como renomear ou adicionar campos)
        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Exemplo: Alterar o campo 'descricao' para 'data' (caso seja necessário)
                database.execSQL(
                    """
                    ALTER TABLE Pagamento RENAME COLUMN descricao TO data
                    """)}}}}

