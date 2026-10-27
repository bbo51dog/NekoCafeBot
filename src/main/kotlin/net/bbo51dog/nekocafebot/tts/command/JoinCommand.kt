package net.bbo51dog.nekocafebot.tts.command

import net.bbo51dog.nekocafebot.command.Command
import net.bbo51dog.nekocafebot.tts.TtsService
import net.bbo51dog.nekocafebot.tts.ReadingChannelList
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class JoinCommand(private val ttsService: TtsService) : Command() {

    override val name: String = "join"
    override val description: String = "vcに接続"

    override fun handleExecute(event: SlashCommandInteractionEvent) {
        event.guild?.let { guild ->
            val voiceChannel = event.member?.voiceState?.channel
            voiceChannel?.let {
                guild.audioManager.openAudioConnection(voiceChannel)
                ReadingChannelList.update(guild.idLong, event.channel.idLong)
                event.reply("ボイスチャンネルに接続しました").setEphemeral(false).queue()
                ttsService.speak("接続しました", guild)
            } ?: event.reply("ボイスチャンネルがみつかりません").queue()
        }
    }
}