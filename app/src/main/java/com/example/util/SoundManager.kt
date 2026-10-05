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

    fun playBanyaSteam() {
        scope.launch {
            // White-noise sizzle + bubbling steam
            generateNoise(durationMs = 600, volume = 0.75f)
            generateTone(startFreq = 200f, endFreq = 800f, durationMs = 400, volume = 0.5f)
        }
    }

    fun playVenikHit() {
        scope.launch {
            // Sharp slap + rustic woody thud
            generateTone(startFreq = 380f, endFreq = 90f, durationMs = 130, volume = 0.85f)
        }
    }

    fun playBoxOpen() {
        scope.launch {
            // Brawl-Stars style ascending celebratory arpeggio
            generateTone(startFreq = 330f, endFreq = 440f, durationMs = 120, volume = 0.8f)
            kotlinx.coroutines.delay(100)
            generateTone(startFreq = 440f, endFreq = 660f, durationMs = 120, volume = 0.85f)
            kotlinx.coroutines.delay(100)
            generateTone(startFreq = 660f, endFreq = 990f, durationMs = 260, volume = 0.95f)
        }
    }

    fun playJackpotFanfare() {
        scope.launch {
            generateTone(startFreq = 587f, endFreq = 880f, durationMs = 150, volume = 0.8f)
            kotlinx.coroutines.delay(120)
            generateTone(startFreq = 880f, endFreq = 1174f, durationMs = 300, volume = 1.0f)
        }
    }

    fun playVoiceReaction(voiceType: String = "bratan") {
        scope.launch {
            when (voiceType) {
                "sueta" -> {
                    // Fast double chirps ("Су-е-та!")
                    generateTone(440f, 660f, 90, 0.9f)
                    kotlinx.coroutines.delay(80)
                    generateTone(660f, 880f, 150, 0.9f)
                }
                "banya" -> {
                    // Deep steam tone
                    generateTone(150f, 320f, 250, 0.8f)
                }
                else -> {
                    // Friendly "Братан!" chord
                    generateTone(330f, 440f, 140, 0.85f)
                    kotlinx.coroutines.delay(100)
                    generateTone(440f, 550f, 180, 0.9f)
                }
            }
        }
    }

    private fun generateNoise(durationMs: Int, volume: Float) {
        try {
            val sampleRate = 22050
            val numSamples = (sampleRate * (durationMs / 1000.0)).toInt()
            if (numSamples <= 0) return
            val buffer = ShortArray(numSamples)
            val random = java.util.Random()
            for (i in 0 until numSamples) {
                val progress = i.toDouble() / numSamples
                val envelope = (1.0 - progress) * volume
                val sampleValue = ((random.nextFloat() * 2f - 1f) * envelope * Short.MAX_VALUE * 0.4f).toInt()
                buffer[i] = sampleValue.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
            }
            playPcmBuffer(buffer, sampleRate, durationMs)
        } catch (_: Exception) {}
    }

    private fun playPcmBuffer(buffer: ShortArray, sampleRate: Int, durationMs: Int) {
        try {
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
            scope.launch {
                kotlinx.coroutines.delay(durationMs + 100L)
                try {
                    audioTrack.stop()
                    audioTrack.release()
                } catch (_: Exception) {}
            }
        } catch (_: Exception) {}
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
