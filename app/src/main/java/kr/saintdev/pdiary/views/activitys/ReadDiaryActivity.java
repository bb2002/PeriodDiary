package kr.saintdev.pdiary.views.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;
import kr.saintdev.pdiary.R;
import kr.saintdev.pdiary.libs.data.DiaryObject;
import kr.saintdev.pdiary.libs.data.DiaryObjectKt;
import kr.saintdev.pdiary.modules.db.DBM;
import kr.saintdev.pdiary.modules.db.manager.DiaryDBM;

public class ReadDiaryActivity extends AppCompatActivity {
    private DiaryObject diary = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_read);
        Intent intent = getIntent();

        int uniqid = intent.getIntExtra("uniqid", 0);
        this.diary = DiaryDBM.getDiary(uniqid, DBM.getDB(this));

        if(this.diary == null) {
            Toast.makeText(this, "글이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            setTitle(DiaryObjectKt.makeMMDD(this.diary.getDate()));
            ((TextView)findViewById(R.id.diary_read_title)).setText(this.diary.getQuestion());
            ((TextView)findViewById(R.id.diary_read_content)).setText(this.diary.getContent());
        }
    }
}
