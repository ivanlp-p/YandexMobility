package ru.ivanl.android.yandexmobility;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import ru.ivanl.android.yandexmobility.DataBase.ArtistHelper;
import ru.ivanl.android.yandexmobility.JSONData.YandexServ;

public class MainActivity extends AppCompatActivity {

    @Inject
    YandexServ yandexServ;

    @Inject
    ArtistHelper artistHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((ForYandexAplication) getApplication()).getAppComponent().inject(this);


    }
}
