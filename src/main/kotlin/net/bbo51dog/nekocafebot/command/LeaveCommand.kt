package net.bbo51dog.nekocafebot.command

import net.bbo51dog.nekocafebot.audio.AudioService
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class LeaveCommand(private val audioService: AudioService) : Command() {

    override val name: String = "leave"
    override val description: String = "vcから退出します"

    override fun handleExecute(event: SlashCommandInteractionEvent) {
        event.guild?.let { guild ->
            guild.audioManager.closeAudioConnection()
            event.reply("退出しました").setEphemeral(false).queue()
        }
    }
}