package ru.ivanl.android.yandexmobility;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import javax.inject.Inject;

import ru.ivanl.android.yandexmobility.DataBase.ArtistHelper;
import ru.ivanl.android.yandexmobility.JSONData.YandexServ;
import ru.ivanl.android.yandexmobility.UI.Adapter.ArtistAdapter;

public class MainActivity extends AppCompatActivity {

    @Inject
    YandexServ yandexServ;

    @Inject
    ArtistHelper artistHelper;

    public static final int PREFERRED_CARD_WIDTH = 350;
    public static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recycleView;
    private ArtistAdapter artistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((ForYandexAplication) getApplication()).getAppComponent().inject(this);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float dispWidth = metrics.widthPixels / metrics.density;
        int columns = Math.round(dispWidth / PREFERRED_CARD_WIDTH);

        Cursor cursor = artistHelper.query();
        artistAdapter = new ArtistAdapter(this,cursor);
        recycleView = (RecyclerView) findViewById(R.id.artist_recyclerview);
        recycleView.setLayoutManager(new GridLayoutManager(this,columns));
        recycleView.setAdapter(artistAdapter);
    }
}
