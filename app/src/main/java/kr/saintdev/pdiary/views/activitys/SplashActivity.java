package kr.saintdev.pdiary.views.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import kr.saintdev.pdiary.MainActivity;
import kr.saintdev.pdiary.R;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return true;
            }
        };

        handler.sendEmptyMessageDelayed(0, 3000);
    }
}
