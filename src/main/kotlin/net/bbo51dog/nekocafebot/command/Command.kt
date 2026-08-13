package net.bbo51dog.nekocafebot.command

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.Command
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData

abstract class Command {

    abstract val name: String
    abstract val description: String

    val subCommandMap = mutableMapOf<String, SubCommand>()

    val subcommandDataList
        get() = subCommandMap.map { (_, it) -> it.subcommandData }

    val commandData: SlashCommandData
        get() =  Commands.slash(name, description)
            .addOptions(options.map {
                it.optionData
            })
            .addSubcommands(subcommandDataList)

    private val options: MutableList<CommandOption> = mutableListOf()

    abstract fun handleExecute(event: SlashCommandInteractionEvent)

    fun addOption(option: CommandOption) {
        options.add(option)
    }

    fun registerSubCommands(vararg subCommands: SubCommand) {
        subCommands.forEach {
            subCommandMap[it.name] = it
        }
    }
}