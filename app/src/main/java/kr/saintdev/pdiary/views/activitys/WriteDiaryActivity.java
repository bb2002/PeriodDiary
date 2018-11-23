package kr.saintdev.pdiary.views.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import kr.saintdev.pdiary.R;
import kr.saintdev.pdiary.libs.data.DiaryObject;
import kr.saintdev.pdiary.libs.data.QuestionArray;
import kr.saintdev.pdiary.modules.db.DBM;
import kr.saintdev.pdiary.modules.db.manager.DiaryDBM;
import kr.saintdev.pdiary.views.window.DatePickDialog;
import kr.saintdev.pdiary.views.window.OnDatePickListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class WriteDiaryActivity extends AppCompatActivity {
    private TextView dateView = null;
    private Calendar selectedDate = Calendar.getInstance();
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
        reSelectQuestion();
    }

    // 저장 버튼 클릭 리스너
    private View.OnClickListener saveClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String questionText = questionEdit.getText().toString();
            String diaryText = contentEdit.getText().toString();

            if(questionText.isEmpty() || diaryText.isEmpty() || selectedDate == null) {
                Toast.makeText(getApplicationContext(), "입력하지 않은 내용이 있습니다.", Toast.LENGTH_SHORT).show();
            } else {
                DiaryDBM.writeDiary(
                        new DiaryObject(0, questionText, diaryText, selectedDate), DBM.getDB(getApplicationContext()));
                Toast.makeText(getApplicationContext(), "입력되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    };

    // 새로고침 버튼 클릭 리스너
    private View.OnClickListener refreshClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            reSelectQuestion();
        }
    };

    // 날짜 선택 리스너
    private View.OnClickListener calenderSelectListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickDialog dialog = new DatePickDialog(WriteDiaryActivity.this, new OnDatePickListener() {
                @Override
                public void onPick(Calendar picked) {
                    selectedDate = picked;
                    dateView.setText(makeDateToString(picked));
                }
            });
            dialog.open();
        }
    };

    private String makeDateToString(Calendar cal) {
        return (cal.get(Calendar.YEAR) + " . " + (cal.get(Calendar.MONTH)+1) + " . " + cal.get(Calendar.DATE));
    }

    private void reSelectQuestion() {
        Random rd = new Random();
        int idx = rd.nextInt(QuestionArray.CONTENT.length);
        questionEdit.setText(QuestionArray.CONTENT[idx]);
    }
}
