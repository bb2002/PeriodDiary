package kr.saintdev.pdiary.modules.db;

public interface DBQuerys {
    String CREATE_DIARY_TABLE = "CREATE TABLE pdiary_diary(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "question TEXT NOT NULL," +
            "content TEXT NULL," +
            "created TEXT NOT NULL)";

    String CREATE_MEMO_TABLE = "CREATE TABLE pdiary_memo (" +
            "date TEXT PRIMARY KEY," +
            "memo TEXT NOT NULL)";

    String CREATE_FEEL_TABLE = "CREATE TABLE pdiary_feel_calendar (" +
            "date TEXT PRIMARY KEY," +
            "feel INT NOT NULL)";
}
