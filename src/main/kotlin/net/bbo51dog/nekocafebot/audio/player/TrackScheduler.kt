package net.bbo51dog.nekocafebot.audio.player

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import kotlin.coroutines.EmptyCoroutineContext

class TrackScheduler(
    private val audioPlayer: AudioPlayer
) : AudioEventAdapter() {

    private val tracks: BlockingQueue<AudioTrack> = LinkedBlockingQueue()

    private fun nextTrack(){
        audioPlayer.startTrack(tracks.poll(), false)
    }

    override fun onTrackEnd(player: AudioPlayer?, track: AudioTrack?, endReason: AudioTrackEndReason?) {
        if (endReason != null) {
            if (endReason.mayStartNext) nextTrack()
        }
        val trackData = track?.userData
        if (trackData is TrackData) {
            val scope = CoroutineScope(EmptyCoroutineContext)
            scope.launch(Dispatchers.IO) {
                trackData.file.delete()
            }
        }
    }

    fun queue(track: AudioTrack) {
        if (!audioPlayer.startTrack(track, true)) tracks.offer(track)
    }
}