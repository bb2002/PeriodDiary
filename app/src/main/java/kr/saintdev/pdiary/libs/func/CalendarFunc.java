package kr.saintdev.pdiary.libs.func;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarFunc {
    public static String makeMMDD(Calendar cal) {
        return (cal.get(Calendar.MONTH) + 1) + " / " + cal.get(Calendar.DAY_OF_MONTH);
    }

    public static Calendar makeSQLDate(String data) {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN);
            cal.setTime(sdf.parse(data));
            return cal;
        } catch (Exception ex) {  return null; }
    }
}
