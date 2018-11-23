package kr.saintdev.pdiary.libs.data;

import java.util.Calendar;

public class DiaryObject {
    private int uniqid = 0;
    private String question = null;
    private String content = null;
    private Calendar date = null;

    public DiaryObject(int uniqid, String question, String content, Calendar date) {
        this.uniqid = uniqid;
        this.question = question;
        this.content = content;
        this.date = date;
    }

    public int getUniqid() {
        return uniqid;
    }

    public String getQuestion() {
        return question;
    }

    public String getContent() {
        return content;
    }

    public Calendar getDate() {
        return date;
    }
}
