package net.bbo51dog.nekocafebot.dictionary

import net.bbo51dog.nekocafebot.dictionary.repository.DictionaryEntry
import net.bbo51dog.nekocafebot.dictionary.repository.DictionaryRepository
import java.util.concurrent.ConcurrentHashMap

class DictionaryService(
    private val repository: DictionaryRepository,
) {

    private val cache = ConcurrentHashMap<Long, Map<String, String>>()

    fun reload(guildId: Long) {
        cache[guildId] = repository.findAll(guildId).associate { it.word to it.reading }
    }

    fun add(guildId: Long, word: String, reading: String) {
        require(word.isNotBlank()) { "単語は空にできません" }
        require(reading.isNotBlank()) { "読み方は空にできません" }

        repository.save(DictionaryEntry(guildId, word, reading))
        reload(guildId)
    }

    fun remove(guildId: Long, word: String): Boolean {
        val deleted = repository.delete(guildId, word)
        if (deleted) reload(guildId)
        return deleted
    }

    fun replace(guildId: Long, text: String): String {
        val dictionary = cache[guildId] ?: return text

        return dictionary.entries
            .sortedByDescending { it.key.length }
            .fold(text) { result, (word, reading) ->
                result.replace(word, reading)
            }
    }
}