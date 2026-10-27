package net.bbo51dog.nekocafebot.dictionary

import net.bbo51dog.nekocafebot.repository.Repository

interface DictionaryRepository : Repository {

    fun find(guildId: Long, word: String): DictionaryEntry?

    fun findAll(guildId: Long): List<DictionaryEntry>

    fun save(entry: DictionaryEntry)

    fun delete(guildId: Long, word: String): Boolean
}