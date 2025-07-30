package com.atul.apodretrofit.data.offline

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.IOException

object ImageStorageManager {

    private val client = OkHttpClient()

    //Downloading and returning image bite or null if failed
    fun downloadImageBytes(url: String): ByteArray? {
        val request = Request.Builder().url(url).build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return null
            return response.body?.bytes()
        }
    }

    // Saving image to files directory with file name as item.date
    fun saveImage(context: Context, fileName: String, bytes: ByteArray): Boolean {
        return try {
            val file = File(context.filesDir, fileName)
            file.writeBytes(bytes)
            true
        } catch (e: IOException) {
            false
        }
    }

    // deleting a file from files directory
    fun deleteImage(context: Context, fileName: String): Boolean {
        val file = File(context.filesDir, fileName)
        return file.exists() && file.delete()
    }

    // Checking if file exists in files directory
    fun imageExists(context: Context, fileName: String): Boolean {
        val file = File(context.filesDir, fileName)
        return file.exists()
    }
}
