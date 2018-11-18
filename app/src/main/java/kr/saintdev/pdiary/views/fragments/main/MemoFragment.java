package kr.saintdev.pdiary.views.fragments.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;
import kr.saintdev.pdiary.R;
import kr.saintdev.pdiary.libs.data.MemoObject;
import kr.saintdev.pdiary.modules.db.DBM;
import kr.saintdev.pdiary.modules.db.manager.MemoDBM;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MemoFragment extends Fragment {
    private View v = null;

    private CalendarView calView = null;
    private EditText memoEditor = null;
    private String selectedDate = null;
    private MemoTextChangeListener textListener = new MemoTextChangeListener();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.fragmn_main_memo, container, false);
        this.calView = this.v.findViewById(R.id.memo_calendar);
        this.memoEditor = this.v.findViewById(R.id.memo_editor);

        this.calView.setOnDateChangeListener(new OnDateChangeListener());
        this.memoEditor.addTextChangedListener(textListener);
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.memoEditor.removeTextChangedListener(this.textListener);
    }

    class OnDateChangeListener implements CalendarView.OnDateChangeListener {
        @Override
        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
            StringBuilder sb = new StringBuilder();
            sb.append(year); sb.append(month + 1); sb.append(dayOfMonth);
            selectedDate = sb.toString();
            memoEditor.setVisibility(View.VISIBLE);        // 날짜 선택시 작동

            MemoObject memo = MemoDBM.readMemo(sb.toString(), DBM.getDB(getActivity()));

            memoEditor.removeTextChangedListener(textListener);
            memoEditor.setText("");
            if(memo != null) {
                memoEditor.setText(memo.getMemo());
            }
            memoEditor.addTextChangedListener(textListener);
        }
    }

    class MemoTextChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(selectedDate == null) {
                Toast.makeText(getActivity(), "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                MemoObject memo = new MemoObject(selectedDate,
                        memoEditor.getText().toString());
                MemoDBM.writeMemo(memo, DBM.getDB(getActivity()));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
