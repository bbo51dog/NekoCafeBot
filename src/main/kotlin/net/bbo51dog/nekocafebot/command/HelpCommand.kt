package net.bbo51dog.nekocafebot.command

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class HelpCommand(private val executor: CommandExecutor) : Command() {

    override val name: String = "help"

    override val description: String = "コマンド一覧"

    override fun handleExecute(event: SlashCommandInteractionEvent) {
        var str = "```"
        executor.commandMap.forEach { (_, command) ->
            if (command.subCommandMap.isEmpty()) {
                str += "\n/${command.name} : ${command.description}"
            } else {
                command.subCommandMap.forEach { (_, subCommand) ->
                    str += "\n/${command.name} ${subCommand.name} : ${subCommand.description}"
                }
            }
        }
        str += "```"
        event.reply(str).queue()
    }
}