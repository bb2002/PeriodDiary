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

import kr.saintdev.pdiary.R;
import kr.saintdev.pdiary.views.activitys.ReadDiaryActivity;
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
        yearAdapter.addAll(DateFunction.getAllYears())
        monthAdapter.addAll(DateFunction.getAllMonths())
        this.yearSelector.adapter = yearAdapter
        this.monthSelector.adapter = monthAdapter

        // init listener
        diaryWrite.setOnClickListener { startActivity(Intent(context!!, WriteDiaryActivity::class.java)) }
        this.getAllDiaries.setOnClickListener { refreshDiaries() }

        return this.v
    }

    override fun onResume() {
        super.onResume()

        // 오늘을 기본값으로 설정
        refreshDiariesToday()
    }

    private fun refreshDiaries() {
        // DB 에서 일기 목록을 가져온다.
        val items = DiaryDBM.getAllDiaries(DBM.getDB(context!!))
        this.adapter.refreshItems(items)
        this.adapter.notifyDataSetChanged()
    }

    private fun refreshDiariesToday() {
        // DB 에서 오늘 일기를 가져온다.
        val cal = Calendar.getInstance()
        this.yearSelector.setSelection(cal.get(Calendar.YEAR) - 1970)
        this.monthSelector.setSelection(cal.get(Calendar.MONTH))

        val year = yearAdapter.getItem(yearSelector.selectedItemPosition)
        val month = monthAdapter.getItem(monthSelector.selectedItemPosition)

        val items = DiaryDBM.getDayOfDiaries(DBM.getDB(context!!), year, month)
        adapter.refreshItems(items)
        adapter.notifyDataSetChanged()
    }

    /**
     * 날짜가 변경되었을 경우
     */
    private val dateChangeListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val year = yearAdapter.getItem(yearSelector.selectedItemPosition)
            val month = monthAdapter.getItem(monthSelector.selectedItemPosition)

            if(year != null && month != null) {
                val items = DiaryDBM.getDayOfDiaries(DBM.getDB(context!!), year, month)
                adapter.refreshItems(items)
                adapter.notifyDataSetChanged()
            }
        }
    }

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
