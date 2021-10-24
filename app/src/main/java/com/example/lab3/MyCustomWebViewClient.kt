package com.example.lab3

import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient

class MyCustomWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if(Uri.parse(url).toString().startsWith("https://www.google.com/search?q=")
            && Uri.parse(url).toString().contains("tbm-isch")) {
            return false
        }

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        return true
    }
}