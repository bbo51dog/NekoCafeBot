package net.bbo51dog.nekocafebot

import net.bbo51dog.nekocafebot.di.appModule
import org.koin.core.context.startKoin

const val TOKEN_ENV = "BOT_TOKEN"

fun main(){
    val koinApplication = startKoin {
        modules(appModule)
    }
    val client = koinApplication.koin.get<BotClient>()
    Runtime.getRuntime().addShutdownHook(
        Thread {
            client.shutdown()
            koinApplication.close()
        }
    )
    try {
        client.run(
            requireNotNull(System.getenv(TOKEN_ENV))
        )
    } catch (e: Exception) {
        System.err.println("Bot startup failed: ${e.message}")
        throw e
    }
}