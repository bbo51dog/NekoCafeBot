package net.bbo51dog.nekocafebot.dictionary.repository

data class DictionaryEntry(
    val guildId: Long,
    val word: String,
    val reading: String,
) {
}