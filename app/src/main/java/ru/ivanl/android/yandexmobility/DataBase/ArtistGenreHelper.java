package ru.ivanl.android.yandexmobility.DataBase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.ivanl.android.yandexmobility.ForYandexAplication;
import ru.ivanl.android.yandexmobility.JSONData.JSONToArtistObject;

public class ArtistGenreHelper extends DBHelper{

    @Inject
    DBOpenHelper dbOpenHelper;
    @Inject
    GenreHelper genreHelper;

    public ArtistGenreHelper(Context context) {
        super(context);
        ((ForYandexAplication) context).getAppComponent().inject(this);
    }

    public static ContentValues getContentValues(long artistId, long genreId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBArtist.ArtistGenreTable.COLUMN_NAME_ARTIST_ID, artistId);
        contentValues.put(DBArtist.ArtistGenreTable.COLUMN_NAME_GENRE_ID, genreId);
        return contentValues;
    }
//  ==============================================================================================
//  ArtistGenre table methods
//  ==============================================================================================

    public void insert(SQLiteDatabase db, @NonNull List<JSONToArtistObject> jsonDataObjectList) {
        // Proceed every JSONDataObject
        for (JSONToArtistObject o : jsonDataObjectList) {

            // Proceed every genre from JSONDataObject
            long artistId = o.getId();
            for (String genreName : o.getGenres()) {

                // Get genre id
                Cursor cursor = genreHelper.query(genreName);
                Long genreId = genreHelper.getId(cursor);

                baseInsert(db, DBArtist.ArtistGenreTable.TABLE_NAME, getContentValues(artistId, genreId));
            }

        }

    }

    public Cursor query() {
        Cursor result = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        try {
            db.beginTransactionNonExclusive();

            // Query all artists genres
            result = baseQuery(db,
                    DBArtist.ArtistGenreTable.TABLE_NAME
            );

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        result.moveToFirst();
        return result;
    }

    // Get genre name id list by artist id
    public List<String> getGenreNameByArtist(@NonNull Long artistId) {
        List<String> result = new ArrayList<>();
        List<Long> genreIdList = getGenreIdByArtist(artistId);

        for (Long id : genreIdList) {
            Cursor cursor = genreHelper.query(id);
            cursor.moveToFirst();
            String genre = genreHelper.getName(cursor);
            result.add(genre);
        }

        return result;
    }

    // Get genre id list by artist id
    public List<Long> getGenreIdByArtist(@NonNull Long artistId) {
        List<Long> result = new ArrayList<>();
        Cursor cursor = queryGenreByArtist(artistId);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            long genreId = getGenreId(cursor);
            result.add(genreId);
            cursor.moveToNext();
        }

        return result;
    }

    // Get genres corresponding to specific artist
    public Cursor queryGenreByArtist(Long artistId) {
        Cursor result = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        try {
            db.beginTransactionNonExclusive();

            // Query all artists genres
            result = baseQuery(db,
                    DBArtist.ArtistGenreTable.TABLE_NAME,
                    new String[]{DBArtist.ArtistGenreTable.COLUMN_NAME_ARTIST_ID, DBArtist.ArtistGenreTable.COLUMN_NAME_GENRE_ID},
                    DBArtist.ArtistGenreTable.COLUMN_NAME_ARTIST_ID + " = ?",
                    artistId.toString()
            );

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


    // Get Artist id from cursor
    @Nullable
    public Long getArtistId(@NonNull Cursor cursor) {
        return getLong(cursor, DBArtist.ArtistGenreTable.COLUMN_NAME_ARTIST_ID);
    }

    // Get Genre id from cursor
    @Nullable
    public  Long getGenreId(@NonNull Cursor cursor) {
        return getLong(cursor, DBArtist.ArtistGenreTable.COLUMN_NAME_GENRE_ID);
    }
}
