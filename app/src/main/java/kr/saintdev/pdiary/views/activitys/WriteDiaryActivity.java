package kr.saintdev.pdiary.views.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import kr.saintdev.pdiary.R;
import kr.saintdev.pdiary.views.window.DatePickDialog;
import kr.saintdev.pdiary.views.window.OnDatePickListener;

import java.util.Calendar;
import java.util.Date;

public class WriteDiaryActivity extends AppCompatActivity {
    private TextView dateView = null;
    private EditText questionEdit = null;
    private EditText contentEdit = null;
    private Button refreshButton = null;
    private Button saveButton = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_write);

        this.dateView = findViewById(R.id.diary_write_date);
        this.questionEdit = findViewById(R.id.diary_write_question);
        this.contentEdit = findViewById(R.id.diary_write_content);
        this.refreshButton = findViewById(R.id.diary_question_refresh);
        this.saveButton = findViewById(R.id.diary_save_diary);

        this.refreshButton.setOnClickListener(refreshClickListener);
        this.saveButton.setOnClickListener(saveClickListener);
        this.dateView.setOnClickListener(calenderSelectListener);

        // init textview
        Calendar cal = Calendar.getInstance();
        dateView.setText(makeDateToString(cal));
    }

    // 저장 버튼 클릭 리스너
    private View.OnClickListener saveClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    // 새로고침 버튼 클릭 리스너
    private View.OnClickListener refreshClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    // 날짜 선택 리스너
    private View.OnClickListener calenderSelectListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickDialog dialog = new DatePickDialog(WriteDiaryActivity.this, new OnDatePickListener() {
                @Override
                public void onPick(Calendar picked) {
                    dateView.setText(makeDateToString(picked));
                }
            });
            dialog.open();
        }
    };

    private String makeDateToString(Calendar cal) {
        return (cal.get(Calendar.YEAR) + " . " + (cal.get(Calendar.MONTH)+1) + " . " + cal.get(Calendar.DATE));
    }
}
