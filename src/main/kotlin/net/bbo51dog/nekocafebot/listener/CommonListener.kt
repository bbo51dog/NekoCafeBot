package net.bbo51dog.nekocafebot.listener

import net.bbo51dog.nekocafebot.audio.AudioService
import net.bbo51dog.nekocafebot.tts.ReadingChannelList
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class CommonListener(private val audioService: AudioService) : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (ReadingChannelList.get(event.guild.idLong) != event.channel.idLong) return
        if (event.author.isBot) return
        if (event.guild.audioManager.isConnected) {
            audioService.speak(event.message)
        }
    }

    override fun onGuildVoiceUpdate(event: GuildVoiceUpdateEvent) {
        if (event.getMember().idLong == event.jda.selfUser.idLong) {
            if (event.channelLeft != null && event.channelJoined == null) {
                ReadingChannelList.remove(event.guild.idLong)
                return
            }
        }
        if (!event.guild.audioManager.isConnected) return
        val botChannel = event.guild.audioManager.connectedChannel ?: return
        event.channelJoined
            ?.takeIf { it.idLong == botChannel.idLong }
            ?.let {
                if (!event.member.user.isBot) {
                    audioService.speak("${event.member.effectiveName}さんが接続しました", event.guild)
                    return
                }
            }
        event.channelLeft
            ?.takeIf { it.idLong == botChannel.idLong }
            ?.let {
                if (!event.member.user.isBot) {
                    audioService.speak("${event.member.effectiveName}さんが退出しました", event.guild)
                    return
                }
            }
        if (!botChannel.members.stream()
                .anyMatch { member: Member? -> !member!!.user.isBot }) {
            event.guild.audioManager.closeAudioConnection()
            ReadingChannelList.remove(event.guild.idLong)
        }
    }
}