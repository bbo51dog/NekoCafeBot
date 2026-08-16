package net.bbo51dog.nekocafebot.tts


object ReadingChannelList {

    private val channelIds = mutableMapOf<Long, Long>()

    fun update(guildId: Long, textChannelId: Long) = channelIds.put(guildId, textChannelId)

    fun get(guildId: Long) = channelIds[guildId]

    fun remove(guildId: Long) = channelIds.remove(guildId)
}