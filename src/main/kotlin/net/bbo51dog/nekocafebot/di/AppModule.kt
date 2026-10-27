package net.bbo51dog.nekocafebot.di

import net.bbo51dog.nekocafebot.BotClient
import net.bbo51dog.nekocafebot.tts.TtsService
import net.bbo51dog.nekocafebot.command.Command
import net.bbo51dog.nekocafebot.command.CommandExecutor
import net.bbo51dog.nekocafebot.command.HelpCommand
import net.bbo51dog.nekocafebot.tts.command.JoinCommand
import net.bbo51dog.nekocafebot.tts.command.LeaveCommand
import net.bbo51dog.nekocafebot.dictionary.repository.DictionaryRepository
import net.bbo51dog.nekocafebot.dictionary.DictionaryService
import net.bbo51dog.nekocafebot.dictionary.repository.MySqlDictionaryRepository
import net.bbo51dog.nekocafebot.command.CommandListener
import net.bbo51dog.nekocafebot.dictionary.command.DictionaryAddCommand
import net.bbo51dog.nekocafebot.dictionary.command.DictionaryCommand
import net.bbo51dog.nekocafebot.dictionary.command.DictionaryRemoveCommand
import net.bbo51dog.nekocafebot.listener.CommonListener
import net.dv8tion.jda.api.hooks.EventListener
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    single {
        BotClient(get(), getAll(), getAll())
    }

    single {
        TtsService(get())
    }

    single {
        DictionaryService(get())
    }

    single {
        MySqlDictionaryRepository()
    } bind DictionaryRepository::class

    factory {
        CommonListener(get())
    } bind EventListener::class

    factory {
        CommandListener(get())
    } bind EventListener::class

    single {
        CommandExecutor()
    }

    factory {
        HelpCommand(get())
    } bind Command::class

    factory {
        JoinCommand(get())
    } bind Command::class

    factory {
        LeaveCommand()
    } bind Command::class

    factory {
        DictionaryCommand(get(), get())
    } bind Command::class

    factory {
        DictionaryAddCommand(get())
    }

    factory {
        DictionaryRemoveCommand(get())
    }
}