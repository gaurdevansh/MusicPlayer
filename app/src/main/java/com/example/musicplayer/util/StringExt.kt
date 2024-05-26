package com.example.musicplayer.util

fun String.modifyTitle() : String {
    var i = 0
    for (ch in this) {
        if (ch != '(') {
            i++
        } else {
            break
        }
    }
    return this.subSequence(0, i).toString()
}