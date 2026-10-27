package net.bbo51dog.nekocafebot.dictionary.command

import net.bbo51dog.nekocafebot.command.CommandOption
import net.bbo51dog.nekocafebot.command.SubCommand
import net.bbo51dog.nekocafebot.dictionary.DictionaryService
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import java.awt.Color

class DictionaryRemoveCommand(
    private val dictionaryService: DictionaryService,
) : SubCommand() {

    override val name: String = "remove"
    override val description: String = "辞書から単語を削除します"

    init {
        addOption(CommandOption.Type.STRING, "word", "削除する単語", true)
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
        val removed = try {
            dictionaryService.remove(guildId, word)
        } catch (e: Exception) {
            val embed = EmbedBuilder()
                .setTitle("読み上げ辞書")
                .setDescription("削除に失敗しました")
                .addField("単語", word, true)
                .setColor(Color.RED)
                .build()
            event.replyEmbeds(embed).queue()
            return
        }

        val embed = if (removed) {
            EmbedBuilder()
                .setTitle("読み上げ辞書")
                .setDescription("削除に成功しました")
                .addField("単語", word, true)
                .setColor(Color.GREEN)
                .build()
        } else {
            EmbedBuilder()
                .setTitle("読み上げ辞書")
                .setDescription("該当する単語が見つかりません")
                .addField("単語", word, true)
                .setColor(Color.RED)
                .build()
        }
        event.replyEmbeds(embed).queue()
    }
}