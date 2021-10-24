package com.example.lab3

import android.content.Context
import android.graphics.Bitmap

import android.widget.Toast

import android.os.AsyncTask


class DownloadImageTask(context: Context) :
    AsyncTask<String?, Void?, Bitmap?>() {
    private val context: Context
    override fun doInBackground(vararg params: String?): Bitmap? {
        return null as Bitmap?
    }

    override fun onPostExecute(result: Bitmap?) {
        // save bitmap result in application class

        // send intent to stop foreground service
    }

    init {
        this.context = context
        Toast.makeText(context, "Please wait, it may take a few seconds.", Toast.LENGTH_SHORT)
            .show()
    }
}