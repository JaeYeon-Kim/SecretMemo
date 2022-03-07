package com.kjy.secretmemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        showSpalshScreen()
    }

    private fun showSpalshScreen() {
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000         // 스플래시 액티비티를 3000밀리초 띄워주고 MainActivity로 이동함.
        )
    }
}