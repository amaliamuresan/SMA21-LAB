package com.example.lab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast

class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        val myApplication : MyApplication = applicationContext as MyApplication
        if (myApplication.getBitmap() == null) {
            Toast.makeText(this, "Error transmitting URL", Toast.LENGTH_SHORT).show()
            finish()
        }
        else
        {
            val imageView : ImageView = findViewById(R.id.imageView)
            imageView.setImageBitmap(myApplication.getBitmap())
        }

    }
}