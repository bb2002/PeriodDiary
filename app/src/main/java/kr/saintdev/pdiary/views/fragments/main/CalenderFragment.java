package kr.saintdev.pdiary.views.fragments.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.*;

import com.prolificinteractive.materialcalendarview.spans.DotSpan;
import kr.saintdev.pdiary.R;
import kr.saintdev.pdiary.libs.data.FeelCalendarObject;
import kr.saintdev.pdiary.modules.db.DBM;
import kr.saintdev.pdiary.modules.db.manager.FeelCalendarDBM;
import kr.saintdev.pdiary.views.activitys.FeelSelectActivity;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class CalenderFragment extends Fragment {
    private View v = null;

    private MaterialCalendarView calendar = null;
    private String selectedDate = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.fragmn_main_calender, container, false);
        this.calendar = this.v.findViewById(R.id.main_feel_calender);

        this.calendar.setOnDateChangedListener(new OnDateChangeHandler());
        this.calendar.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                updateCalender();
            }
        });
        updateCalender();

        return this.v;
    }

    private void updateCalender() {
        calendar.removeDecorators();

        CalendarDay day = calendar.getCurrentDate();
        ArrayList<FeelCalendarObject> items = FeelCalendarDBM.getAllFeels(day.getYear(), day.getMonth() + 1, DBM.getDB(getContext()));
        for(FeelCalendarObject o : items) {
            calendar.addDecorator(new FeelDecorator(o));
        }
    }

    class OnDateChangeHandler implements OnDateSelectedListener {
        @Override
        public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
            Intent intent = new Intent(getContext(), FeelSelectActivity.class);
            intent.putExtra("year", date.getYear());
            intent.putExtra("month", date.getMonth() + 1);
            intent.putExtra("day", date.getDay());

            selectedDate = date.getYear() + "-" + (date.getMonth()+1) + "-" + date.getDay();
            startActivityForResult(intent, 0x33);
        }
    }

    class FeelDecorator implements DayViewDecorator {
        private FeelCalendarObject feel = null;

        public FeelDecorator(FeelCalendarObject feel) {
            this.feel = feel;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return Integer.parseInt(feel.getDate().split("-")[2]) == day.getDay();
        }

        @Override
        public void decorate(DayViewFacade view) {
            int color = 0;
            switch(feel.getFeel()) {
                case 5: color = Color.BLUE; break;
                case 4: color = Color.CYAN; break;
                case 3: color = Color.GREEN; break;
                case 2: color = Color.rgb(226,26,255); break;
                case 1: color = Color.RED; break;
            }

            view.addSpan(new DotSpan(15, color));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0x33 && resultCode == RESULT_OK) {
            int feel = data.getIntExtra("feel", 5);
            FeelCalendarDBM.writeDateOfFeel(new FeelCalendarObject(selectedDate, feel), DBM.getDB(getContext()));
            updateCalender();
        }
    }
}
