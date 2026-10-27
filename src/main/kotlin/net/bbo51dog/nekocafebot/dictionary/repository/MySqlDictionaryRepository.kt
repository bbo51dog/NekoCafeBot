package net.bbo51dog.nekocafebot.dictionary.repository

import net.bbo51dog.nekocafebot.database.Database

class MySqlDictionaryRepository : DictionaryRepository {

    override fun find(guildId: Long, word: String): DictionaryEntry? {
        val sql = """
            SELECT guild_id, word, reading
            FROM tts_dictionary
            WHERE guild_id = ? AND word = ?
        """.trimIndent()

        Database.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setLong(1, guildId)
                statement.setString(2, word)

                statement.executeQuery().use { result ->
                    if (!result.next()) return null
                    return DictionaryEntry(
                        guildId = result.getLong("guild_id"),
                        word = result.getString("word"),
                        reading = result.getString("reading"),
                    )
                }
            }
        }
    }

    override fun findAll(guildId: Long): List<DictionaryEntry> {
        val sql = """
            SELECT guild_id, word, reading
            FROM tts_dictionary
            WHERE guild_id = ?
        """.trimIndent()

        Database.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setLong(1, guildId)

                statement.executeQuery().use { result ->
                    return buildList {
                        while (result.next()) {
                            add(
                                DictionaryEntry(
                                    guildId = result.getLong("guild_id"),
                                    word = result.getString("word"),
                                    reading = result.getString("reading"),
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    override fun save(entry: DictionaryEntry) {
        val sql = """
            INSERT INTO tts_dictionary
                (guild_id, word, reading)
            VALUES (?, ?, ?)
            ON DUPLICATE KEY UPDATE reading = ?
        """.trimIndent()

        Database.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setLong(1, entry.guildId)
                statement.setString(2, entry.word)
                statement.setString(3, entry.reading)
                statement.setString(4, entry.reading)
                statement.executeUpdate()
            }
        }
    }

    override fun delete(guildId: Long, word: String): Boolean {
        val sql = """
            DELETE FROM tts_dictionary
            WHERE guild_id = ? AND word = ?
        """.trimIndent()

        Database.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setLong(1, guildId)
                statement.setString(2, word)
                return statement.executeUpdate() > 0
            }
        }
    }
}