package net.bbo51dog.nekocafebot.command

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData

abstract class Command {

    abstract val name: String
    abstract val description: String

    val commandData: SlashCommandData
        get() =  Commands.slash(name, description).addOptions(options.map {
            it.optionData
        })

    private val options: MutableList<CommandOption> = mutableListOf()

    abstract fun handleExecute(event: SlashCommandInteractionEvent)

    fun addOption(option: CommandOption) {
        options.add(option)
    }
}