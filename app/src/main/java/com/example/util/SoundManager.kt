package com.example.util

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.sin

object SoundManager {
    private val scope = CoroutineScope(Dispatchers.Default)

    fun playMogPunch() {
        scope.launch {
            generateTone(
                startFreq = 160f,
                endFreq = 50f,
                durationMs = 90,
                volume = 0.8f
            )
        }
    }

    fun playBonesmashCrack() {
        scope.launch {
            // High metallic impact followed by low crunch
            generateTone(startFreq = 520f, endFreq = 220f, durationMs = 120, volume = 0.9f)
        }
    }

    fun playLevelUp() {
        scope.launch {
            generateTone(startFreq = 440f, endFreq = 660f, durationMs = 140, volume = 0.7f)
        }
    }

    fun playKochModeRoar() {
        scope.launch {
            // Rising aggressive powerup
            generateTone(startFreq = 80f, endFreq = 340f, durationMs = 450, volume = 1.0f)
        }
    }

    fun playEvolutionFanfare() {
        scope.launch {
            generateTone(startFreq = 523.25f, endFreq = 783.99f, durationMs = 250, volume = 0.8f)
            generateTone(startFreq = 783.99f, endFreq = 1046.50f, durationMs = 350, volume = 0.9f)
        }
    }

    private fun generateTone(
        startFreq: Float,
        endFreq: Float,
        durationMs: Int,
        volume: Float
    ) {
        try {
            val sampleRate = 22050
            val numSamples = (sampleRate * (durationMs / 1000.0)).toInt()
            if (numSamples <= 0) return

            val buffer = ShortArray(numSamples)
            var currentPhase = 0.0

            for (i in 0 until numSamples) {
                val progress = i.toDouble() / numSamples
                val currentFreq = startFreq + (endFreq - startFreq) * progress
                val phaseIncrement = 2.0 * Math.PI * currentFreq / sampleRate
                currentPhase += phaseIncrement

                // Amplitude envelope (decay)
                val envelope = (1.0 - progress) * volume
                val sampleValue = (sin(currentPhase) * envelope * Short.MAX_VALUE).toInt()
                buffer[i] = sampleValue.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
            }

            val audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(buffer.size * 2)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            audioTrack.write(buffer, 0, buffer.size)
            audioTrack.play()

            // Release after playback
            scope.launch {
                kotlinx.coroutines.delay(durationMs + 100L)
                try {
                    audioTrack.stop()
                    audioTrack.release()
                } catch (_: Exception) {}
            }
        } catch (_: Exception) {
            // Gracefully ignore audio errors on headless devices
        }
    }
}
