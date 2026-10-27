package net.bbo51dog.nekocafebot.dictionary.command

import net.bbo51dog.nekocafebot.command.CommandOption
import net.bbo51dog.nekocafebot.command.SubCommand
import net.bbo51dog.nekocafebot.dictionary.DictionaryService
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import java.awt.Color

class DictionaryAddCommand(
    private val dictionaryService: DictionaryService,
) : SubCommand() {

    override val name: String = "add"
    override val description: String = "辞書に単語を追加"

    init {
        addOption(CommandOption.Type.STRING, "word", "置換前の単語", true)
        addOption(CommandOption.Type.STRING, "reading", "単語の読み", true)
    }

    override fun handleExecute(event: SlashCommandInteractionEvent) {
        val guildId = event.guild?.idLong
        if (guildId == null) {
            event.reply("サーバー内で実行してください")
                .setEphemeral(true)
                .queue()
            return
        }

        val word = requireNotNull(event.getOption("word")).asString
        val reading = requireNotNull(event.getOption("reading")).asString
        try {
            dictionaryService.add(guildId, word, reading)
        } catch (e: Exception) {
            val embed = EmbedBuilder()
                .setTitle("読み上げ辞書")
                .setDescription("登録に失敗しました")
                .setColor(Color.RED)
                .build()
            event.replyEmbeds(embed).queue()
            return
        }

        val embed = EmbedBuilder()
            .setTitle("読み上げ辞書")
            .setDescription("登録に成功しました")
            .addField("単語", word, true)
            .addField("読み", reading, true)
            .setColor(Color.GREEN)
            .build()
        event.replyEmbeds(embed).queue()

    }
}