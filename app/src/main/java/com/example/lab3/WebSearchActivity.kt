package com.example.lab3

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Button
import android.widget.Toast

class WebSearchActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var startBackgroundServiceButton: Button
    private lateinit var startForegroundServiceButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_search)


        webView = findViewById(R.id.webView)
        startBackgroundServiceButton = findViewById(R.id.startBackgroundService_button)
        startForegroundServiceButton = findViewById(R.id.startForegroundService_button)


        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        webView.webViewClient = MyCustomWebViewClient()
        webView.loadUrl("https://www.google.com/search?q=cat&tbm=isch&source=lnms&sa=X")



        val clipboard: ClipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager


        startForegroundServiceButton.setOnClickListener {
            val abc: ClipData = clipboard.primaryClip!!
            val item: ClipData.Item = abc.getItemAt(0)
            val url: String = item.text.toString()

            if (!url.contains("https://images.app.goo.gl/")) {
                Toast.makeText(this, "URL not valid. Try another one.", Toast.LENGTH_SHORT).show()

            } else {
                val intent = Intent(this, ImageServiceIntent::class.java)
                intent.putExtra(EXTRA_URL, url)
                startService(intent)
            }

            startBackgroundServiceButton.setOnClickListener {
                val abc: ClipData = clipboard.primaryClip!!
                val item = abc.getItemAt(0)
                val url = item.text.toString()

                if (!url.contains("https://images.app.goo.gl/")) {
                    Toast.makeText(this, "URL not valid. Try another one.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Log.d("startBackgroundServiceButton", "ok")
                    val intent = Intent(this, ForegroundImageService::class.java)
                    intent.putExtra(EXTRA_URL, url)
                    intent.action = ForegroundImageService.STARTFOREGROUND_ACTION
                    startService(intent)
                }
            }

        }
    }

    companion object {
        val EXTRA_URL = "EXTRA_URL"
    }
}