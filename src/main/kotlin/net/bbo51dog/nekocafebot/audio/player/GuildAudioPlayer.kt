package net.bbo51dog.nekocafebot.audio.player

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager


class GuildAudioPlayer(manager: AudioPlayerManager) {

    val player = manager.createPlayer()

    val scheduler = TrackScheduler(player)

    init {
        player.addListener(scheduler)
    }

    fun getSendHandler(): AudioSendHandlerImpl {
        return AudioSendHandlerImpl(player)
    }
}