package net.bbo51dog.nekocafebot.command

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData

class CommandExecutor {

    private val commands = mutableMapOf<String, Command>()

    val commandMap: Map<String, Command>
        get() = commands

    fun registerCommands(jda: JDA, vararg commands: Command) {
        val commandDataList = mutableListOf<CommandData>()
        commands.forEach {
            this.commands[it.name] = it
            commandDataList.add(it.commandData)
        }
        jda.updateCommands().queue()
        jda.updateCommands().addCommands(commandDataList).queue()
    }

    fun handleCommand(event: SlashCommandInteractionEvent) {
        commands[event.name]?.handleExecute(event)
    }
}