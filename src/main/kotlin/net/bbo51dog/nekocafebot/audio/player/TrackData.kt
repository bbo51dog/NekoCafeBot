package net.bbo51dog.nekocafebot.audio.player

import net.dv8tion.jda.api.entities.Guild
import java.io.File


data class TrackData(
    val guild: Guild,
    val file: File
) {}