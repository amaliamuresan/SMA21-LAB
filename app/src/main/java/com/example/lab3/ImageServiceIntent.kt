package com.example.lab3

import android.app.IntentService
import android.content.Intent
import androidx.core.app.JobIntentService
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.URL


class ImageServiceIntent : IntentService(ImageServiceIntent::class.simpleName) {
    override fun onHandleIntent(intent: Intent?) {
        if(intent != null) {
            val param :String = intent.getStringExtra(WebSearchActivity.EXTRA_URL)!!
            handleDownloadAction(param)

        }
        else
        {
            Log.d("ImageServiceIntent", "Intent NULL")
        }
    }

    private fun handleDownloadAction(url: String) {
        Log.d("ImageServiceIntent", "handleDownloadAction")
        // start task on separate thread
        //new DownloadImageTask().execute(url);
        //.execute("https://news.nationalgeographic.com/content/dam/news/2018/05/17/you-can-train-your-cat/02-cat-training-NationalGeographic_1484324.ngsversion.1526587209178.adapt.1900.1.jpg");
        try {
            val longURL: String = URLTools.getLongUrl(url)
            var bmp: Bitmap? = null
            try {
                val `in`: InputStream = URL(longURL).openStream()
                bmp = BitmapFactory.decodeStream(`in`)
            } catch (e: Exception) {
                Log.e("Error Message", e.message.toString())
                e.printStackTrace()
            }

            Thread.sleep(5000)
            (applicationContext as MyApplication).setBitmap(bmp)
            // start second activity to show result

            val intent = Intent(applicationContext, ImageActivity::class.java)
            startActivity(intent)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}