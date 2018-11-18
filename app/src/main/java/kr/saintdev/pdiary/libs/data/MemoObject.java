package kr.saintdev.pdiary.libs.data;

public class MemoObject {
    private String date = null;
    private String memo = null;

    public MemoObject(String date, String memo) {
        this.date = date;
        this.memo = memo;
    }

    public String getDate() {
        return date;
    }

    public String getMemo() {
        return memo;
    }
}
