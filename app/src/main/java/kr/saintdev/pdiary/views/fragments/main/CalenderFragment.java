package kr.saintdev.pdiary.views.fragments.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import kr.saintdev.pdiary.R;
import kr.saintdev.pdiary.views.activitys.FeelSelectActivity;

import static android.app.Activity.RESULT_OK;

public class CalenderFragment extends Fragment {
    private View v = null;

    private MaterialCalendarView calendar = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.fragmn_main_calender, container, false);
        this.calendar = this.v.findViewById(R.id.main_feel_calender);

        this.calendar.setOnDateChangedListener(new OnDateChangeHandler());

        return this.v;
    }

    class OnDateChangeHandler implements OnDateSelectedListener {
        @Override
        public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
            Intent intent = new Intent(getContext(), FeelSelectActivity.class);
            intent.putExtra("year", date.getYear());
            intent.putExtra("month", date.getMonth());
            intent.putExtra("day", date.getDay());

            startActivityForResult(intent, 0x33);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0x33 && resultCode == RESULT_OK) {
            Log.d("DIARY", "feel" + data.getIntExtra("feel", -1));
        }
    }
}
