package com.example.themoviedb.ui.splash_screen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.themoviedb.R
import com.example.themoviedb.ui.authentication.AuthActivity

class SplashScreen : AppCompatActivity() {

    private val handler by lazy {
        Handler()
    }

    private val runnable = Runnable {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 2000)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }
}
