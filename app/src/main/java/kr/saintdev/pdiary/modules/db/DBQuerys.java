package kr.saintdev.pdiary.modules.db;

public interface DBQuerys {
    String CREATE_DIARY_TABLE = "CREATE TABLE pdiary_diary(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "question TEXT NOT NULL," +
            "content TEXT NULL," +
            "created TEXT NOT NULL)";
}
