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

public class ArtistHelper extends DBHelper {

    @Inject
    DBOpenHelper dbOpenHelper;
    @Inject
    ArtistGenreHelper artistGenreHelper;

    public ArtistHelper(Context context) {
        super(context);
        ((ForYandexAplication) context).getAppComponent().inject(this);
    }

    public ContentValues getContentValues(JSONToArtistObject o) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBArtist.ArtistTable._ID, o.getId());
        contentValues.put(DBArtist.ArtistTable.COLUMN_NAME_NAME, o.getName());
        contentValues.put(DBArtist.ArtistTable.COLUMN_NAME_TRACKS, o.getTracks());
        contentValues.put(DBArtist.ArtistTable.COLUMN_NAME_ALBUMS, o.getAlbums());
        contentValues.put(DBArtist.ArtistTable.COLUMN_NAME_LINK, o.getLink());
        contentValues.put(DBArtist.ArtistTable.COLUMN_NAME_DESCRIPTION, o.getDescription());
        contentValues.put(DBArtist.ArtistTable.COLUMN_NAME_SMALL_COVER_URL, o.getSmallCover());
        contentValues.put(DBArtist.ArtistTable.COLUMN_NAME_BIG_COVER_URL, o.getBigCover());
        return contentValues;
    }

//  ==============================================================================================
//  Artist table methods
//  ==============================================================================================

    public long insert(@NonNull List<JSONToArtistObject> jsonDataObjectList) {
        long result = DB_OP_ERROR;
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        try {
            db.beginTransactionNonExclusive();

            // Proceed every JSONDataObject
            for (JSONToArtistObject o : jsonDataObjectList) {
                baseInsert(db, DBArtist.ArtistTable.TABLE_NAME, getContentValues(o));
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return result;
    }

    // Get given artists
    public Cursor query(Long artistId) {
        Cursor result = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        try {
            db.beginTransactionNonExclusive();

            // Query artist
            result = baseQuery(db,
                    DBArtist.ArtistTable.TABLE_NAME,
                    null,
                    DBArtist.ArtistTable._ID + " = ?",
                    artistId.toString());

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        result.moveToFirst();
        return result;
    }

    // Get all artists
    public Cursor query() {
        Cursor result = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        try {
            db.beginTransactionNonExclusive();

            // Query all artists
            result = baseQuery(db, DBArtist.ArtistTable.TABLE_NAME);

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
        return getLong(cursor, DBArtist.ArtistTable._ID);
    }

    // Get Name from cursor
    @Nullable
    public String getName(@NonNull Cursor cursor) {
        return getString(cursor, DBArtist.ArtistTable.COLUMN_NAME_NAME);
    }

    // Get Tracks from cursor
    @Nullable
    public Integer getTracks(@NonNull Cursor cursor) {
        return getInteger(cursor, DBArtist.ArtistTable.COLUMN_NAME_TRACKS);
    }

    // Get Albums from cursor
    @Nullable
    public Integer getAlbums(@NonNull Cursor cursor) {
        return getInteger(cursor, DBArtist.ArtistTable.COLUMN_NAME_ALBUMS);
    }

    // Get Link from cursor
    @Nullable
    public String getLink(@NonNull Cursor cursor) {
        return getString(cursor, DBArtist.ArtistTable.COLUMN_NAME_LINK);
    }

    // Get Description from cursor
    @Nullable
    public String getDescription(@NonNull Cursor cursor) {
        return getString(cursor, DBArtist.ArtistTable.COLUMN_NAME_DESCRIPTION);
    }

    // Get urlCoverSmall from cursor
    @Nullable
    public String getSmallCover(@NonNull Cursor cursor) {
        return getString(cursor, DBArtist.ArtistTable.COLUMN_NAME_SMALL_COVER_URL);
    }

    // Get urlCoverBig from cursor
    @Nullable
    public String getBigCover(@NonNull Cursor cursor) {
        return getString(cursor, DBArtist.ArtistTable.COLUMN_NAME_BIG_COVER_URL);
    }

    // Get genres names for artist cursor
    @Nullable
    public List<String> getGenreList(@NonNull Cursor cursor) {
        List<String> result = new ArrayList<>();

        long artistId = getId(cursor);
        result = artistGenreHelper.getGenreNameByArtist(artistId);

        return result;
    }
}
