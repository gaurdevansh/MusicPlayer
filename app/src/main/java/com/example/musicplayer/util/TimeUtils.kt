package com.example.musicplayer.util

object TimeUtils {

    fun getFormattedTime(time: Long): String {
        val timeInSec: Int = (time / 1000).toInt()
        val min: Int = timeInSec / 60
        val sec: Int = timeInSec % 60
        var minSt = ""
        var secSt = ""
        if (timeInSec == 0) {
            return "00:00"
        }
        minSt = if (min == 0) {
            "00"
        } else if (min < 10) {
            "0$min"
        } else {
            "$min"
        }
        secSt = if (sec == 0) {
            "00"
        } else if (sec < 10) {
            "0$sec"
        } else {
            "$sec"
        }
        return "$minSt:$secSt"
    }
}