package net.bbo51dog.nekocafebot

import club.minnced.discord.jdave.interop.JDaveSessionFactory
import net.bbo51dog.nekocafebot.audio.AudioService
import net.bbo51dog.nekocafebot.command.CommandExecutor
import net.bbo51dog.nekocafebot.command.HelpCommand
import net.bbo51dog.nekocafebot.command.JoinCommand
import net.bbo51dog.nekocafebot.listener.CommandListener
import net.bbo51dog.nekocafebot.listener.CommonListener
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

        val commandExecutor = CommandExecutor()
        val audioService = AudioService()

        jda = JDABuilder.createLight(token, intents)
            .enableCache(cacheFlags)
            .setRawEventsEnabled(true)
            .addEventListeners(
                CommonListener(audioService),
                CommandListener(commandExecutor),
            )
            .setStatus(OnlineStatus.ONLINE)
            .setActivity(Activity.playing("ねこかふぇぼっと"))
            .setMemberCachePolicy(MemberCachePolicy.ALL)
            .setAudioModuleConfig(AudioModuleConfig().withDaveSessionFactory(JDaveSessionFactory()))
            .build()

        commandExecutor.registerCommands(
            jda,
            JoinCommand(audioService),
            HelpCommand(commandExecutor),
        )

        jda.awaitReady()
    }
}