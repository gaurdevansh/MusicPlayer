package com.example.musicplayer.data.repository

import android.content.ContentResolver
import android.content.Context
import android.provider.MediaStore
import com.example.musicplayer.data.model.MusicItem
import com.example.musicplayer.util.modifyTitle

class MediaRepository(private val context: Context) {

    fun loadMedia(): List<MusicItem> {
        val musicItemList = mutableListOf<MusicItem>()
        val contentResolver: ContentResolver = context.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA,
        )
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

        contentResolver.query(uri, projection, selection, null, sortOrder).use { cursor ->
            val idColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val dataColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn!!)
                    val title = cursor.getString(titleColumn!!)
                    val artist = cursor.getString(artistColumn!!)
                    val path = cursor.getString(dataColumn!!)

                    musicItemList.add(MusicItem(id, title.modifyTitle(), artist.modifyTitle(), path))
                }
            }
        }

        for (mediaItem in musicItemList) {
            println("Media Item: ${mediaItem.title} by ${mediaItem.path}")
        }
        return musicItemList
    }
}