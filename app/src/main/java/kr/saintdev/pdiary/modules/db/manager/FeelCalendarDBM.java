package kr.saintdev.pdiary.modules.db.manager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import kr.saintdev.pdiary.libs.data.FeelCalendarObject;
import kr.saintdev.pdiary.modules.db.DBM;

import java.util.ArrayList;

public class FeelCalendarDBM {
    public static FeelCalendarObject readDateOfFeel(String date, DBM dbm) {
        SQLiteDatabase db = dbm.read();
        Cursor cs = db.rawQuery("SELECT * FROM pdiary_feel_calendar WHERE date = ?", new String[]{ date });

        if(cs.moveToNext()) {
            FeelCalendarObject obj = new FeelCalendarObject(
                    cs.getString(0),
                    cs.getInt(1)
            );
            cs.close();
            return obj;
        } else {
            cs.close();
            return null;
        }
    }

    public static void writeDateOfFeel(FeelCalendarObject obj, DBM dbm) {
        SQLiteDatabase db = dbm.write();

        String sql;
        if(readDateOfFeel(obj.getDate(), dbm) == null) {
            // 새로 작성, Insert
            sql = "INSERT INTO pdiary_feel_calendar (feel, date) VALUES(?,?)";
        } else {
            // Update
            sql = "UPDATE pdiary_feel_calendar SET feel = ? WHERE date = ?";
        }

        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(2, obj.getDate());
        stmt.bindLong(1, obj.getFeel());
        stmt.executeInsert();
    }

    public static ArrayList<FeelCalendarObject> getAllFeels(int year, int month, DBM dbm) {
        SQLiteDatabase db = dbm.read();
        Cursor cs = db.rawQuery("SELECT * FROM pdiary_feel_calendar", new String[]{ });

        ArrayList<FeelCalendarObject> days = new ArrayList<>();
        while(cs.moveToNext()) {
            String[] date = cs.getString(0).split("-");

            if(Integer.parseInt(date[0]) == year && Integer.parseInt(date[1]) == month) {
                days.add(
                        new FeelCalendarObject(cs.getString(0), cs.getInt(1)));
            }
        }

        return days;
    }
}
