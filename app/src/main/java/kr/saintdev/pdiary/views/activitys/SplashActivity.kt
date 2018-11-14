package kr.saintdev.pdiary.views.activitys

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import kr.saintdev.pdiary.MainActivity
import kr.saintdev.pdiary.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
            true
        }
        handler.sendEmptyMessageDelayed(0, 3000)
    }
}