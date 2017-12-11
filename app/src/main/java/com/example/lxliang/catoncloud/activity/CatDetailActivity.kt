package com.example.lxliang.catoncloud.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.example.lxliang.catoncloud.R

/**
 * Created by lxliang on 04/12/2017.
 */
class CatDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat_detail)

        val imageView = findViewById<ImageView>(R.id.avatar)
        ViewCompat.setTransitionName(imageView, "avatar_transition")

        val bmp = intent.getParcelableExtra<Bitmap>("avatarBitmap")
        imageView.setImageBitmap(bmp)
    }
}