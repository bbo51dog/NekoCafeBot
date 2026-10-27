package net.bbo51dog.nekocafebot.dictionary.command

import net.bbo51dog.nekocafebot.command.Command
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class DictionaryCommand(
    private val addCommand: DictionaryAddCommand,
    private val removeCommand: DictionaryRemoveCommand,
) : Command() {

    override val name: String = "dictionary"
    override val description: String = "辞書コマンド"

    init {
        registerSubCommands(
            addCommand,
            removeCommand,
        )
    }

    override fun handleExecute(event: SlashCommandInteractionEvent) {}
}