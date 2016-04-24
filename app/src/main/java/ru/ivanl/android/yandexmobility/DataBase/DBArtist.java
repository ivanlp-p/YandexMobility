package ru.ivanl.android.yandexmobility.DataBase;

import android.provider.BaseColumns;

/**
 * Created by Ivan on 23.04.2016.
 */
public class DBArtist {

    private DBArtist() {
    }

    public static final String DB_NAME = "yandexmobility.db";
    public static final int DB_VERSION = 1;


    public static final class ArtistTable implements BaseColumns {

        public static final String TABLE_NAME = "artist";

        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_TRACKS = "tracks";
        public static final String COLUMN_NAME_ALBUMS = "albums";
        public static final String COLUMN_NAME_LINK = "link";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_SMALL_COVER_URL = "small_cover_url";
        public static final String COLUMN_NAME_BIG_COVER_URL = "big_cover_url";

        static final String SQL_QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
                _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME_NAME + " TEXT UNIQUE ON CONFLICT REPLACE NOT NULL ON CONFLICT ROLLBACK, " +
                COLUMN_NAME_TRACKS + " TEXT NOT NULL ON CONFLICT ROLLBACK, " +
                COLUMN_NAME_ALBUMS + " TEXT NOT NULL ON CONFLICT ROLLBACK, " +
                COLUMN_NAME_LINK + " TEXT, " +
                COLUMN_NAME_DESCRIPTION + " TEXT, " +
                COLUMN_NAME_SMALL_COVER_URL + " TEXT, " +
                COLUMN_NAME_BIG_COVER_URL + " TEXT " +
                ")";

        static final String SQL_QUERY_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }


    public static final class ArtistGenreTable {

        public static final String TABLE_NAME = "artist_genre";

        public static final String COLUMN_NAME_ARTIST_ID = "artist_id";
        public static final String COLUMN_NAME_GENRE_ID = "genre_id";

        static final String SQL_QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
                COLUMN_NAME_ARTIST_ID + " INTEGER NOT NULL ON CONFLICT ROLLBACK, " +
                COLUMN_NAME_GENRE_ID + " INTEGER NOT NULL ON CONFLICT ROLLBACK, " +
                " FOREIGN KEY (" + COLUMN_NAME_ARTIST_ID + ") REFERENCES " +
                ArtistTable.TABLE_NAME + " (" + ArtistTable._ID + "), " +
                " FOREIGN KEY (" + COLUMN_NAME_ARTIST_ID + ") REFERENCES " +
                GenreTable.TABLE_NAME + " (" + GenreTable._ID + "), " +
                " PRIMARY KEY (" + COLUMN_NAME_ARTIST_ID + ", " + COLUMN_NAME_GENRE_ID + ") " +
                ")";

        static final String SQL_QUERY_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }


    public static final class GenreTable implements BaseColumns {

        public static final String TABLE_NAME = "genre";

        public static final String COLUMN_NAME_NAME = "name";

        static final String SQL_QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME_NAME + " INTEGER UNIQUE ON CONFLICT IGNORE NOT NULL ON CONFLICT ROLLBACK " +
                ")";

        static final String SQL_QUERY_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

}

