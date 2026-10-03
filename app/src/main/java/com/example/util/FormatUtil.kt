package com.example.util

import java.util.Locale
import kotlin.math.ln
import kotlin.math.pow

object FormatUtil {
    fun formatNumber(value: Double): String {
        if (value.isNaN() || value.isInfinite()) return "0"
        if (value < 1000.0) {
            return if (value == value.toLong().toDouble()) {
                value.toLong().toString()
            } else {
                String.format(Locale.US, "%.1f", value)
            }
        }
        val exp = (ln(value) / ln(1000.0)).toInt()
        val suffix = arrayOf("K", "M", "B", "T", "Qa", "Qi", "Sx", "Sp", "Oc", "No", "Dc")
        val index = (exp - 1).coerceIn(0, suffix.size - 1)
        val formatted = value / 1000.0.pow((index + 1).toDouble())
        return String.format(Locale.US, "%.2f %s", formatted, suffix[index])
    }

    fun formatNumberLong(value: Long): String {
        return formatNumber(value.toDouble())
    }
}
