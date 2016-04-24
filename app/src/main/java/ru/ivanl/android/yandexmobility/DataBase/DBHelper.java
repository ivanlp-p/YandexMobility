package ru.ivanl.android.yandexmobility.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.ivanl.android.yandexmobility.ForYandexAplication;


public class DBHelper {

    // Default error result for SqlDataBase CRUD methods
    static final long DB_OP_ERROR = -1;

    final Context context;

    public DBHelper(Context context) {
        ((ForYandexAplication) context).getAppComponent().inject(this);
        this.context = context;
    }

    // Delete DB
    public void deleteDB() {
        context.deleteDatabase(DBArtist.DB_NAME);
    }



//  ==============================================================================================
//  Basic cursor query methods
//  ==============================================================================================


    Cursor baseQuery(@NonNull SQLiteDatabase db, @NonNull String table) {
        return baseQuery(db, table, null, null, (String[]) null);
    }

    Cursor baseQuery(@NonNull SQLiteDatabase db, @NonNull String table, String[] columns, String selection, String selectionArg) {
        return baseQuery(db, table, columns, selection, new String[]{selectionArg});
    }

    // Base query method
    Cursor baseQuery(@NonNull SQLiteDatabase db, @NonNull String table, String[] columns, String selection, String[] selectionArgs) {
        Cursor result = null;

        if (!db.isReadOnly() && db.inTransaction() &&
                !table.isEmpty()) {
            result = db.query(
                    table,
                    columns,
                    selection,
                    selectionArgs,
                    null, null, null
            );
        }

        return result;
    }

    // Base insert method
    long baseInsert(@NonNull SQLiteDatabase db, @NonNull String table, @NonNull ContentValues values) {
        long result = DB_OP_ERROR;

        if (!db.isReadOnly() && db.inTransaction() &&
                !table.isEmpty() &&
                values.size() > 0) {
            result = db.insert(
                    table,
                    null,
                    values
            );
        }

        return result;
    }






    // Get Integer column value from cursor
    @Nullable
    Integer getInteger(@NonNull Cursor cursor, String columnName) {
        int columnNo = cursor.getColumnIndex(columnName);
        if (columnNo >= 0)
            return cursor.getInt(columnNo);
        else
            return null;
    }

    // Get Long column value from cursor
    @Nullable
    Long getLong(@NonNull Cursor cursor, String columnName) {
        int columnNo = cursor.getColumnIndex(columnName);
        if (columnNo >= 0)
            return cursor.getLong(columnNo);
        else
            return null;
    }

    // Get String column value from cursor
    @Nullable
    String getString(@NonNull Cursor cursor, String columnName) {
        int columnNo = cursor.getColumnIndex(columnName);
        if (columnNo >= 0)
            return cursor.getString(columnNo);
        else
            return null;
    }

}
