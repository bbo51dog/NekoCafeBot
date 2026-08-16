package net.bbo51dog.nekocafebot.tts

import net.dv8tion.jda.api.entities.Message

object MessageFormatter {

    private const val MAX_LENGTH = 50

    private val urlRegex = """http(s)?://([\w-]+.)+[\w-]+(/[\w- ./?%&=]*)?""".toRegex()

    fun format(message: Message): String {
        return format(message.contentDisplay)
    }

    fun format(messageStr: String): String {
        var str = messageStr.replace(urlRegex, "URL省略")
        if (str.length > MAX_LENGTH) {
            str = str.dropLast(str.length - MAX_LENGTH)
            str += "省略"
        }
        return str
    }
}