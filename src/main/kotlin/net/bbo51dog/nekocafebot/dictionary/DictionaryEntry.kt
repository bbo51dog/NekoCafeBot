package net.bbo51dog.nekocafebot.dictionary

data class DictionaryEntry(
    val guildId: Long,
    val word: String,
    val reading: String,
) {
}