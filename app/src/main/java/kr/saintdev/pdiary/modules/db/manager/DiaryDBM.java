package kr.saintdev.pdiary.modules.db.manager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import kr.saintdev.pdiary.libs.data.DiaryObject;
import kr.saintdev.pdiary.libs.data.DiaryObjectKt;
import kr.saintdev.pdiary.modules.db.DBM;

import java.sql.SQLDataException;
import java.util.ArrayList;

/**
 * Copyright (c) 2015-2019 Saint software All rights reserved.
 */
public class DiaryDBM {
    // 새 일기를 씁니다.
    public static void writeDiary(DiaryObject diary, DBM dbm) {
        SQLiteDatabase db = dbm.write();
        SQLiteStatement stmt = db.compileStatement("INSERT INTO pdiary_diary (question, content, created) VALUES(?, ?, date('now'))");
        stmt.bindString(1, diary.getQuestion());
        stmt.bindString(2, diary.getContent());
        stmt.executeInsert();
    }

    public static ArrayList<DiaryObject> getDiary(int id, DBM dbm) {
        SQLiteDatabase db = dbm.read();
        Cursor csr = db.rawQuery("SELECT * FROM pdiary_diary WHERE _id = ?", new String[]{ id+"" });

        ArrayList<DiaryObject> diarys = new ArrayList<>();
        while(csr.moveToNext()) {
            diarys.add(new DiaryObject(
                    csr.getInt(0),
                    csr.getString(1),
                    csr.getString(2),
                    DiaryObjectKt.makeSQLDate(csr.getString(3))
            ));
        }

        return diarys;
    }
}
