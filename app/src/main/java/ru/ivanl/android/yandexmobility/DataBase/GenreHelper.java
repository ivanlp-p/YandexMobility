package ru.ivanl.android.yandexmobility.DataBase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import javax.inject.Inject;

import ru.ivanl.android.yandexmobility.ForYandexAplication;
import ru.ivanl.android.yandexmobility.JSONData.JSONToArtistObject;

public class GenreHelper extends  DBHelper {

    @Inject
    DBOpenHelper dbOpenHelper;

    public GenreHelper(Context context) {
        super(context);
        ((ForYandexAplication) context).getAppComponent().inject(this);
    }

    public static ContentValues getContentValues(String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBArtist.GenreTable.COLUMN_NAME_NAME, name);
        return contentValues;
    }

//  ==============================================================================================
//  Genre table methods
//  ==============================================================================================

    // Insert data from JSONDataObject oblects
    public long insert(@NonNull List<JSONToArtistObject> jsonDataObjectList) {

        long result = DB_OP_ERROR;
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        try {
            db.beginTransactionNonExclusive();

            // Proceed every JSONDataObject
            for (JSONToArtistObject o : jsonDataObjectList) {

                // Proceed every Genre name
                for (String genreName : o.getGenres()) {
                    result = baseInsert(db, DBArtist.GenreTable.TABLE_NAME, getContentValues(genreName));
                }
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return result;
    }

    // Get particular genre by genre name
    public Cursor query(String genreName) {
        Cursor result = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        try {
            db.beginTransactionNonExclusive();

            // Query genre by name
            result = baseQuery(db,
                    DBArtist.GenreTable.TABLE_NAME,
                    null,
                    DBArtist.GenreTable.COLUMN_NAME_NAME+ " = ?",
                    genreName
            );

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        result.moveToFirst();
        return result;
    }


    // Get particular genre by genre id
    public Cursor query(Long genreId) {
        Cursor result = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        try {
            db.beginTransactionNonExclusive();

            // Query genre by id
            result = baseQuery(db,
                    DBArtist.GenreTable.TABLE_NAME,
                    null,
                    DBArtist.GenreTable._ID + " = ?",
                    genreId.toString()
            );

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        result.moveToFirst();
        return result;
    }

    // Get all genres
    public Cursor query() {
        Cursor result = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        try {
            db.beginTransactionNonExclusive();

            // Query all genres
            result = baseQuery(db, DBArtist.GenreTable.TABLE_NAME);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        result.moveToFirst();
        return result;
    }



//  ==============================================================================================
//  Methods for gathering cursor data
//  ==============================================================================================


    // Get _id from cursor
    @Nullable
    public Long getId(@NonNull Cursor cursor) {
        return getLong(cursor, DBArtist.GenreTable._ID);
    }

    // Get Name from cursor
    @Nullable
    public String getName(@NonNull Cursor cursor) {
        return getString(cursor, DBArtist.GenreTable.COLUMN_NAME_NAME);
    }


}
