package net.bbo51dog.nekocafebot.command

import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData

class CommandOption(
    val type: Type,
    val name: String,
    val description: String,
    val isRequired: Boolean = false,
    val isAutoComplete: Boolean = false,
) {

    enum class Type(
        val optionType: OptionType
    ) {
        STRING(OptionType.STRING),
        INTEGER(OptionType.INTEGER),
        BOOLEAN(OptionType.BOOLEAN),
        USER(OptionType.USER),
        CHANNEL(OptionType.CHANNEL),
        ROLE(OptionType.ROLE),
        MENTIONABLE(OptionType.MENTIONABLE),
        NUMBER(OptionType.NUMBER),
        ATTACHMENT(OptionType.ATTACHMENT),
    }

    val optionData: OptionData
        get() = OptionData(type.optionType, name, description, isRequired, isAutoComplete)

}