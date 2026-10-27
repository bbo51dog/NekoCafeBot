package net.bbo51dog.nekocafebot.command

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData

abstract class SubCommand {

    abstract val name: String
    abstract val description: String

    val subcommandData: SubcommandData
        get() =  SubcommandData(name, description)
            .addOptions(options.map {
                it.optionData
            })

    private val options: MutableList<CommandOption> = mutableListOf()

    abstract fun handleExecute(event: SlashCommandInteractionEvent)

    fun addOption(option: CommandOption) {
        options.add(option)
    }

    fun addOption(type: CommandOption.Type, name: String, description: String, isRequired: Boolean = false, isAutoComplete: Boolean = false) {
        addOption(CommandOption(type, name, description, isRequired, isAutoComplete))
    }
}