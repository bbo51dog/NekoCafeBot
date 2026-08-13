package net.bbo51dog.nekocafebot

import club.minnced.discord.jdave.interop.JDaveSessionFactory
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.audio.AudioModuleConfig
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.MemberCachePolicy
import net.dv8tion.jda.api.utils.cache.CacheFlag

class BotClient {

    private lateinit var jda: JDA

    fun run(token: String) {

        val intents = listOf(
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_MEMBERS,
            GatewayIntent.GUILD_PRESENCES,
            GatewayIntent.GUILD_VOICE_STATES,
            GatewayIntent.MESSAGE_CONTENT,
        )
        val cacheFlags = listOf(
            CacheFlag.VOICE_STATE,
        )

        jda = JDABuilder.createLight(token, intents)
            .enableCache(cacheFlags)
            .setRawEventsEnabled(true)
            .setStatus(OnlineStatus.ONLINE)
            .setActivity(Activity.playing("ねこかふぇぼっと"))
            .setMemberCachePolicy(MemberCachePolicy.ALL)
            .setAudioModuleConfig(AudioModuleConfig().withDaveSessionFactory(JDaveSessionFactory()))
            .build()
        jda.awaitReady()
    }
}