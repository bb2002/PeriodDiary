package kr.saintdev.pdiary.modules.db.manager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import kr.saintdev.pdiary.libs.data.DiaryObject;
import kr.saintdev.pdiary.libs.func.CalendarFunc;
import kr.saintdev.pdiary.modules.db.DBM;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Copyright (c) 2015-2019 Saint software All rights reserved.
 */
public class DiaryDBM {
    // 새 일기를 씁니다.
    public static void writeDiary(DiaryObject diary, DBM dbm) {
        SQLiteDatabase db = dbm.write();
        SQLiteStatement stmt = db.compileStatement("INSERT INTO pdiary_diary (question, content, created) VALUES(?, ?, ?)");
        stmt.bindString(1, diary.getQuestion());
        stmt.bindString(2, diary.getContent());

        Calendar d = diary.getDate();
        stmt.bindString(3, d.get(Calendar.YEAR) + "-" + (d.get(Calendar.MONTH) + 1) + "-" + d.get(Calendar.DATE));
        stmt.executeInsert();
    }

    public static DiaryObject getDiary(int id, DBM dbm) {
        SQLiteDatabase db = dbm.read();
        Cursor csr = db.rawQuery("SELECT * FROM pdiary_diary WHERE _id = ?", new String[]{ id+"" });

        if(csr.moveToNext()) {
            DiaryObject obj = new DiaryObject(
                    csr.getInt(0),
                    csr.getString(1),
                    csr.getString(2),
                    CalendarFunc.makeSQLDate(csr.getString(3))
            );

            csr.close();
            return obj;
        } else {
            csr.close();
            return null;
        }
    }

    public static ArrayList<DiaryObject> getAllDiaries(DBM dbm) {
        SQLiteDatabase db = dbm.read();
        Cursor csr = db.rawQuery("SELECT * FROM pdiary_diary ORDER BY _id DESC", new String[]{});

        ArrayList<DiaryObject> diaries = new ArrayList<>();
        while(csr.moveToNext()) {
            diaries.add(new DiaryObject(
                    csr.getInt(0),
                    csr.getString(1),
                    csr.getString(2),
                    CalendarFunc.makeSQLDate(csr.getString(3))
            ));
        }

        return diaries;
    }

    public static ArrayList<DiaryObject> getDayOfDiaries(DBM dbm, int year, int month) {
        ArrayList<DiaryObject> items = getAllDiaries(dbm);
        ArrayList<DiaryObject> diaries = new ArrayList<>();

        for(DiaryObject diary : items) {
            Calendar c = diary.getDate();
            if(c.get(Calendar.YEAR) == year && (c.get(Calendar.MONTH) + 1) == month) {
                diaries.add(diary);
            }
        }

        return diaries;
    }

    public static void removeDiary(DBM dbm, int uniqid) {
        SQLiteDatabase db = dbm.write();
        db.execSQL("DELETE FROM pdiary_diary WHERE _id = '"+uniqid+"'");
    }
}
