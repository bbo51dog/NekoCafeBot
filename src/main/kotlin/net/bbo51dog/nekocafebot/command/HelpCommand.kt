package net.bbo51dog.nekocafebot.command

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class HelpCommand(private val executor: CommandExecutor) : Command() {

    override val name: String = "help"

    override val description: String = "コマンド一覧"

    override fun handleExecute(event: SlashCommandInteractionEvent) {
        var str = "```"
        executor.commandMap.forEach { (_, command) ->
            str += "\n${command.name} : ${command.description}"
        }
        str += "```"
        event.reply(str).queue()
    }
}