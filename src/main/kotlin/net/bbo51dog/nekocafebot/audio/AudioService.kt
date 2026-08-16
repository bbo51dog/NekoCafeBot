package net.bbo51dog.nekocafebot.audio

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import kotlinx.coroutines.*
import net.bbo51dog.nekocafebot.tts.MessageFormatter
import net.bbo51dog.nekocafebot.audio.player.GuildAudioPlayer
import net.bbo51dog.nekocafebot.audio.player.TrackData
import net.bbo51dog.nekocafebot.tts.TtsClient
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import kotlin.coroutines.EmptyCoroutineContext

class AudioService {

    private val playerManager = DefaultAudioPlayerManager()
    private val players: MutableMap<String, GuildAudioPlayer> = HashMap()

    init {
        AudioSourceManagers.registerLocalSource(playerManager)
    }

    fun speak(str: String, guild: Guild) {
        val scope = CoroutineScope(EmptyCoroutineContext)
        scope.launch(Dispatchers.Default) {
            val deffer = async(Dispatchers.IO) {
                TtsClient.saveVoiceToCache(MessageFormatter.format(str))
            }
            val trackData = TrackData(guild, deffer.await())
            loadAndPlay(trackData)
        }
    }

    fun speak(message: Message) {
        speak(message.contentDisplay, message.guild)
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
        println(trackData.file.path.toString())
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