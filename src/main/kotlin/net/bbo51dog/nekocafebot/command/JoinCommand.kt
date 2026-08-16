package net.bbo51dog.nekocafebot.command

import net.bbo51dog.nekocafebot.audio.AudioService
import net.bbo51dog.nekocafebot.tts.ReadingChannelList
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class JoinCommand(private val audioService: AudioService) : Command() {

    override val name: String = "join"
    override val description: String = "vcに接続"

    override fun handleExecute(event: SlashCommandInteractionEvent) {
        event.guild?.let { guild ->
            val voiceChannel = event.member?.voiceState?.channel
            voiceChannel?.let {
                guild.audioManager.openAudioConnection(voiceChannel)
                ReadingChannelList.update(guild.idLong, event.channel.idLong)
                event.reply("ボイスチャンネルに接続しました").setEphemeral(false).queue()
                audioService.speak("接続しました", guild)
            } ?: event.reply("ボイスチャンネルがみつかりません").queue()
        }
    }
}