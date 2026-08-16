package net.bbo51dog.nekocafebot.listener

import net.bbo51dog.nekocafebot.audio.AudioService
import net.bbo51dog.nekocafebot.tts.ReadingChannelList
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.EventListener


class CommonListener(private val audioService: AudioService) : EventListener {

    override fun onEvent(event: GenericEvent) {
        when(event) {
            is MessageReceivedEvent -> {
                if (ReadingChannelList.get(event.guild.idLong) != event.channel.idLong) return
                if (event.guild.audioManager.isConnected) {
                    audioService.speak(event.message)
                }
            }
            is GuildVoiceUpdateEvent -> {
                if (event.getMember().idLong == event.jda.selfUser.idLong) {
                    if (event.channelLeft != null && event.channelJoined == null) {
                        ReadingChannelList.remove(event.guild.idLong)
                        return
                    }
                }
                if (!event.guild.audioManager.isConnected) return
                val botChannel = event.guild.audioManager.connectedChannel ?: return
                if (!botChannel.members.stream()
                        .anyMatch { member: Member? -> !member!!.user.isBot }) {
                    event.guild.audioManager.closeAudioConnection()
                    ReadingChannelList.remove(event.guild.idLong)
                }
            }
        }
    }
}