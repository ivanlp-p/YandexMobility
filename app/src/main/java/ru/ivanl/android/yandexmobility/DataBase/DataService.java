package ru.ivanl.android.yandexmobility.DataBase;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import ru.ivanl.android.yandexmobility.ForYandexAplication;
import ru.ivanl.android.yandexmobility.JSONData.JSONToArtistObject;
import ru.ivanl.android.yandexmobility.JSONData.YandexServ;
import ru.ivanl.android.yandexmobility.Logic.LogicArtist;

/**
 * Created by Ivan on 24.04.2016.
 */
public class DataService extends IntentService {

    @Inject
    ArtistHelper artistHelper;
    @Inject
    ArtistGenreHelper artistGenreHelper;
    @Inject
    GenreHelper genreHelper;
    @Inject
    YandexServ yandexServ;
    @Inject
    DBOpenHelper dbOpenHelper;

    public DataService() {
        super("DataService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ((ForYandexAplication) getApplicationContext()).getAppComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

       /* if (isConnectedToNetwork()) {*/

            try {

                List<JSONToArtistObject> data = requestArtistData();


                if (data != null && !data.isEmpty()) {

                    SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
                    try {
                        db.beginTransaction();

                        db.execSQL(DBArtist.ArtistTable.SQL_QUERY_DROP_TABLE);
                        db.execSQL(DBArtist.GenreTable.SQL_QUERY_DROP_TABLE);
                        db.execSQL(DBArtist.ArtistGenreTable.SQL_QUERY_DROP_TABLE);

                        db.execSQL(DBArtist.ArtistTable.SQL_QUERY_CREATE_TABLE);
                        db.execSQL(DBArtist.GenreTable.SQL_QUERY_CREATE_TABLE);
                        db.execSQL(DBArtist.ArtistGenreTable.SQL_QUERY_CREATE_TABLE);

                        genreHelper.insert(db, data);
                        artistHelper.insert(db, data);
                        artistGenreHelper.insert(db, data);

                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                        notifyChanges();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        //}

    }

    // Check network connection before attempting to download new data

/*    private boolean isConnectedToNetwork() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }*/

    // Request Artist data via YandexService
    @Nullable
    private List<JSONToArtistObject> requestArtistData() throws IOException {
        return yandexServ.getArtistsData()
                .execute()
                .body();
    }

    // Notify List Activity about DB changes
    private void notifyChanges() {
        Intent localIntent = new Intent(LogicArtist.DATA_UPDATED_ACTION);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }


}
