package kr.saintdev.pdiary.views.fragments.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;

import kr.saintdev.pdiary.R;
import kr.saintdev.pdiary.libs.data.DiaryObject;
import kr.saintdev.pdiary.libs.func.DataFunction;
import kr.saintdev.pdiary.modules.db.DBM;
import kr.saintdev.pdiary.modules.db.manager.DiaryDBM;
import kr.saintdev.pdiary.views.activitys.ReadDiaryActivity;
import kr.saintdev.pdiary.views.activitys.WriteDiaryActivity;
import kr.saintdev.pdiary.views.adapter.DiaryAdapter;

public class DiaryFragment extends Fragment {
    private View v = null;

    private ListView listView = null;
    private Spinner yearSelector = null;
    private Spinner monthSelector = null;
    private FloatingActionButton diaryWrite = null;
    private Button getAllDiaries = null;

    private DiaryAdapter adapter = null;
    private ArrayAdapter<Integer> yearAdapter = null;
    private ArrayAdapter<Integer> monthAdapter = null;

    public DiaryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.fragmn_main_diary, container, false);

        // find view
        this.listView = this.v.findViewById(R.id.diary_list);
        this.yearSelector = this.v.findViewById(R.id.diary_selector_year);
        this.monthSelector = this.v.findViewById(R.id.diary_selector_month);
        this.diaryWrite = this.v.findViewById(R.id.diary_create_new);
        this.getAllDiaries = this.v.findViewById(R.id.diary_selector_all);

        // inti adapter
        this.adapter = new DiaryAdapter();
        this.listView.setAdapter(this.adapter);

        this.yearSelector.setOnItemSelectedListener(dateChangeListener);
        this.monthSelector.setOnItemSelectedListener(dateChangeListener);
        this.listView.setOnItemClickListener(onDiarySelectedListener);

        // init spinner
        yearAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item);
        monthAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item);
        yearAdapter.addAll(DataFunction.getAllYears());
        monthAdapter.addAll(DataFunction.getAllMonths());
        this.yearSelector.setAdapter(yearAdapter);
        this.monthSelector.setAdapter(monthAdapter);

        // init listener
        diaryWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), WriteDiaryActivity.class));
            }
        });


        this.getAllDiaries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshDiaries();
            }
        });

        return this.v;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshDiariesToday();
    }

    private void refreshDiaries() {
        // DB 에서 일기 목록을 가져온다.
        ArrayList<DiaryObject> items = DiaryDBM.getAllDiaries(DBM.getDB(getContext()));
        this.adapter.refreshItems(items);
        this.adapter.notifyDataSetChanged();
    }

    private void refreshDiariesToday() {
        // DB 에서 오늘 일기를 가져온다.
        Calendar cal = Calendar.getInstance();
        this.yearSelector.setSelection(cal.get(Calendar.YEAR) - 1970);
        this.monthSelector.setSelection(cal.get(Calendar.MONTH));

        int year = yearAdapter.getItem(yearSelector.getSelectedItemPosition());
        int month = monthAdapter.getItem(monthSelector.getSelectedItemPosition());

        ArrayList<DiaryObject> items = DiaryDBM.getDayOfDiaries(DBM.getDB(getContext()), year, month);
        adapter.refreshItems(items);
        adapter.notifyDataSetChanged();
    }

    /**
     * 날짜가 변경되었을 경우
     */
    private AdapterView.OnItemSelectedListener dateChangeListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            int year = yearAdapter.getItem(yearSelector.getSelectedItemPosition());
            int month = monthAdapter.getItem(monthSelector.getSelectedItemPosition());

            ArrayList<DiaryObject> items = DiaryDBM.getDayOfDiaries(DBM.getDB(getContext()), year, month);
            adapter.refreshItems(items);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    /**
     * 글이 선택되었을 경우
     */
    private AdapterView.OnItemClickListener onDiarySelectedListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            int uniqid = adapter.getItem(i).getUniqid();
            Intent intent = new Intent(getContext(), ReadDiaryActivity.class);
            intent.putExtra("uniqid", uniqid);       // 글 고유 번호를 추가 한다.

            startActivity(intent);
        }
    };
}
