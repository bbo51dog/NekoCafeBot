package net.bbo51dog.nekocafebot.tts

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import net.bbo51dog.nekocafebot.audio.player.GuildAudioPlayer
import net.bbo51dog.nekocafebot.audio.player.TrackData
import net.bbo51dog.nekocafebot.dictionary.DictionaryService
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import kotlin.coroutines.EmptyCoroutineContext

class TtsService(
    private val dictionaryService: DictionaryService,
) {

    private val playerManager = DefaultAudioPlayerManager()
    private val players: MutableMap<String, GuildAudioPlayer> = HashMap()

    companion object {
        private const val MAX_LENGTH = 50
    }

    init {
        AudioSourceManagers.registerLocalSource(playerManager)
    }

    fun speak(str: String, guild: Guild) {
        val scope = CoroutineScope(EmptyCoroutineContext)
        scope.launch(Dispatchers.Default) {
            val deffer = async(Dispatchers.IO) {
                TtsClient.saveVoiceToCache(format(guild, str))
            }
            val trackData = TrackData(guild, deffer.await())
            loadAndPlay(trackData)
        }
    }

    fun speak(message: Message) {
        speak(message.contentDisplay, message.guild)
    }

    private fun format(guild: Guild, original: String): String {
        val urlRegex = """http(s)?://([\w-]+.)+[\w-]+(/[\w- ./?%&=]*)?""".toRegex()
        var str = dictionaryService.replace(guild.idLong, original).replace(urlRegex, "URL省略")
        if (str.length > MAX_LENGTH) {
            str = str.dropLast(str.length - MAX_LENGTH)
            str += "省略"
        }
        return str
    }

    @Synchronized
    private fun getPlayer(guild: Guild): GuildAudioPlayer {
        var player = players[guild.id]
        if (player == null) {
            player = GuildAudioPlayer(playerManager)
            guild.audioManager.sendingHandler = player.getSendHandler()
            players[guild.id] = player
        }
        return player
    }

    fun loadAndPlay(trackData: TrackData) {
        val player = getPlayer(trackData.guild)
        playerManager.loadItemOrdered(player, trackData.file.path.toString(), object : AudioLoadResultHandler {
            override fun trackLoaded(track: AudioTrack) {
                track.userData = trackData
                player.scheduler.queue(track)
            }

            override fun playlistLoaded(playlist: AudioPlaylist) {}
            override fun noMatches() {}
            override fun loadFailed(e: FriendlyException) {
                e.printStackTrace()
                println("loadFailed on AudioManager")
            }
        })
    }
}