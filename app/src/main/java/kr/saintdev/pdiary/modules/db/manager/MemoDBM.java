package kr.saintdev.pdiary.modules.db.manager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import kr.saintdev.pdiary.libs.data.MemoObject;
import kr.saintdev.pdiary.modules.db.DBM;

public class MemoDBM {
    public static void writeMemo(MemoObject memoObj, DBM dbm) {
        SQLiteDatabase db = dbm.write();

        String sql;
        MemoObject readMemo = readMemo(memoObj.getDate(), dbm);
        if(readMemo == null) {
            sql = "INSERT INTO pdiary_memo (memo, date) VALUES(?,?)";
        } else {
            sql = "UPDATE pdiary_memo SET memo = ? WHERE date = ?";
        }
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, memoObj.getMemo());
        stmt.bindString(2, memoObj.getDate());
        stmt.execute();
    }

    public static MemoObject readMemo(String date, DBM dbm) {
        SQLiteDatabase db = dbm.read();
        Cursor cs = db.rawQuery("SELECT * FROM pdiary_memo WHERE date = ?", new String[]{ date });

        MemoObject memo = null;
        if(cs.moveToNext()) {
            memo = new MemoObject(
                    cs.getString(0),
                    cs.getString(1));
        }

        return memo;
    }
}
