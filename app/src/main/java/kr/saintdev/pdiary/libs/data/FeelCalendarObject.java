package kr.saintdev.pdiary.libs.data;

public class FeelCalendarObject {
    private String date = null;
    private int feel = 0;

    public FeelCalendarObject(String date, int feel) {
        this.date = date;
        this.feel = feel;
    }

    public String getDate() {
        return date;
    }

    public int getFeel() {
        return feel;
    }
}
