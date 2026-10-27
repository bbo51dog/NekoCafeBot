package net.bbo51dog.nekocafebot.tts.command

import net.bbo51dog.nekocafebot.command.Command
import net.bbo51dog.nekocafebot.tts.TtsService
import net.bbo51dog.nekocafebot.tts.ReadingChannelList
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import java.awt.Color

class JoinCommand(private val ttsService: TtsService) : Command() {

    override val name: String = "join"
    override val description: String = "ボイスチャットに接続します"

    override fun handleExecute(event: SlashCommandInteractionEvent) {
        event.guild?.let { guild ->
            val voiceChannel = event.member?.voiceState?.channel
            val embed = if (voiceChannel != null) {
                guild.audioManager.openAudioConnection(voiceChannel)
                ReadingChannelList.update(guild.idLong, event.channel.idLong)
                ttsService.speak("接続しました", guild)
                EmbedBuilder()
                    .setColor(Color.GREEN)
                    .setTitle("VC接続")
                    .setDescription("ボイスチャンネルに接続しました")
                    .addField("読み上げ対象ch", event.channel.asMention, true)
                    .addField("接続ch", voiceChannel.asMention, true)
            } else {
                EmbedBuilder()
                    .setColor(Color.RED)
                    .setTitle("VC接続")
                    .setDescription("ボイスチャンネルがみつかりません")
            }
            event.replyEmbeds(embed.build()).queue()
        }
    }
}