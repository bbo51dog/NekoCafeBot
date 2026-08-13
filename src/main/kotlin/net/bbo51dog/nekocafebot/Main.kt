package net.bbo51dog.nekocafebot

const val TOKEN_ENV = "BOT_TOKEN"

fun main(){
    BotClient().run(System.getenv(TOKEN_ENV))
}