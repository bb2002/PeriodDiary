package kr.saintdev.pdiary.views.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import kr.saintdev.pdiary.R;

public class FeelSelectActivity extends AppCompatActivity {
    private FloatingActionButton[] feelButtons = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feel_select);

        this.feelButtons = new FloatingActionButton[] {
                findViewById(R.id.feel_button_verygood),
                findViewById(R.id.feel_button_good),
                findViewById(R.id.feel_button_normal),
                findViewById(R.id.feel_button_bad),
                findViewById(R.id.feel_button_verybad)
        };

        OnFeelClickListener listener = new OnFeelClickListener();
        for(FloatingActionButton b : this.feelButtons) {
            b.setOnClickListener(listener);
        }

        Intent intent = getIntent();
        String date = intent.getIntExtra("year", 2019) + "년 "
                + intent.getIntExtra("month", 11) + "월 "
                + intent.getIntExtra("day", 1) + "일";
        ((TextView) findViewById(R.id.feel_title_date)).setText(date);
    }

    class OnFeelClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int feel = 5;

            switch (v.getId()) {
                case R.id.feel_button_verygood: feel = 5; break;
                case R.id.feel_button_good:     feel = 4; break;
                case R.id.feel_button_normal:   feel = 3; break;
                case R.id.feel_button_bad:      feel = 2; break;
                case R.id.feel_button_verybad:  feel = 1; break;
            }

            Intent intent = new Intent();
            intent.putExtra("feel", feel);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
