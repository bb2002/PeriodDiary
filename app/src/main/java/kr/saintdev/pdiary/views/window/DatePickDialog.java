package kr.saintdev.pdiary.views.window;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickDialog implements DatePickerDialog.OnDateSetListener {
    private DatePickerDialog dialog = null;
    private OnDatePickListener listener = null;

    public DatePickDialog(AppCompatActivity activity, OnDatePickListener listener) {
        Calendar now = Calendar.getInstance();
        this.dialog = new DatePickerDialog(activity, this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        this.listener = listener;
    }

    public void open() {
        this.dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, dayOfMonth);
        this.listener.onPick(cal);
    }
}
