package net.bbo51dog.nekocafebot.tts.command

import net.bbo51dog.nekocafebot.command.Command
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class LeaveCommand() : Command() {

    override val name: String = "leave"
    override val description: String = "vcから退出します"

    override fun handleExecute(event: SlashCommandInteractionEvent) {
        event.guild?.let { guild ->
            guild.audioManager.closeAudioConnection()
            event.reply("退出しました").setEphemeral(false).queue()
        }
    }
}