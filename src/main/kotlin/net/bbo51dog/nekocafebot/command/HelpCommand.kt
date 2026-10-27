package net.bbo51dog.nekocafebot.command

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import java.awt.Color

class HelpCommand(private val executor: CommandExecutor) : Command() {

    override val name: String = "help"

    override val description: String = "コマンド一覧を表示します"

    override fun handleExecute(event: SlashCommandInteractionEvent) {
        val embedBuilder = EmbedBuilder()
            .setTitle("ヘルプ")
            .setDescription("ねこかふぇぼっとの使い方\uD83D\uDC3E")
            .setColor(Color.BLUE)
        executor.commandMap.forEach { (_, command) ->
            if (command.subCommandMap.isEmpty()) {
                embedBuilder.addField("/${command.name}", command.description, true)
            } else {
                command.subCommandMap.forEach { (_, subCommand) ->
                    embedBuilder.addField(
                        "/${command.name} ${subCommand.name}",
                        subCommand.description,
                        true
                    )
                }
            }
        }
        event.replyEmbeds(embedBuilder.build()).queue()
    }
}