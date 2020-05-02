package com.example.myapplicationexample

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        Handler().postDelayed({
            val mainActivity = Intent(this@StartActivity, MainActivity::class.java)
            startActivity(mainActivity)
            finish()
        }, SPLASH_TIMEOUT)
    }
    companion object {
        private const val SPLASH_TIMEOUT: Long = 2000
    }
}