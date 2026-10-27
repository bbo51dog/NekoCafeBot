package net.bbo51dog.nekocafebot.dictionary.repository

import net.bbo51dog.nekocafebot.repository.Repository

interface DictionaryRepository : Repository {

    fun find(guildId: Long, word: String): DictionaryEntry?

    fun findByGuild(guildId: Long): List<DictionaryEntry>

    fun findAll(): List<DictionaryEntry>

    fun save(entry: DictionaryEntry)

    fun delete(guildId: Long, word: String): Boolean
}