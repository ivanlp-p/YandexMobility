package ru.ivanl.android.yandexmobility.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(Context context) {
        super(context, DBArtist.DB_NAME, null, DBArtist.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBArtist.ArtistTable.SQL_QUERY_CREATE_TABLE);
        db.execSQL(DBArtist.GenreTable.SQL_QUERY_CREATE_TABLE);
        db.execSQL(DBArtist.ArtistGenreTable.SQL_QUERY_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBArtist.ArtistTable.SQL_QUERY_DROP_TABLE);
        db.execSQL(DBArtist.GenreTable.SQL_QUERY_DROP_TABLE);
        db.execSQL(DBArtist.ArtistGenreTable.SQL_QUERY_DROP_TABLE);
    }
}
