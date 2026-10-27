package net.bbo51dog.nekocafebot.tts.command

import net.bbo51dog.nekocafebot.command.Command
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import java.awt.Color

class LeaveCommand() : Command() {

    override val name: String = "leave"
    override val description: String = "ボイスチャットから退出します"

    override fun handleExecute(event: SlashCommandInteractionEvent) {
        event.guild?.let { guild ->
            guild.audioManager.closeAudioConnection()
            val embed = EmbedBuilder()
                .setColor(Color.GREEN)
                .setTitle("VC接続")
                .setDescription("ボイスチャンネルから退出しました")
                .build()
            event.replyEmbeds(embed).queue()
        }
    }
}