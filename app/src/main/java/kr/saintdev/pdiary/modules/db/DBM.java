package kr.saintdev.pdiary.modules.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Copyright (c) 2015-2018 Saint software All rights reserved.
 * @Date 11.15 2018
 * SQLite DB 와 연결을 제어 한다.
 */
public class DBM extends SQLiteOpenHelper {
    private static DBM instance = null;

    public static DBM getDB(Context context) {
        if(instance == null) {
            DBM.instance = new DBM(context, "pdiary", null, 1);
        }

        return instance;
    }

    public DBM(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Init Database
        db.execSQL(DBQuerys.CREATE_DIARY_TABLE);        // 일기장 저장 DB 생성
        db.execSQL(DBQuerys.CREATE_MEMO_TABLE);         // 메모 저장 DB 생성
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public SQLiteDatabase read() {
        return getReadableDatabase();
    }

    public SQLiteDatabase write() {
        return getWritableDatabase();
    }
}
