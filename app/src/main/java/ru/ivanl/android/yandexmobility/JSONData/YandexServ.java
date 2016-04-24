package ru.ivanl.android.yandexmobility.JSONData;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface YandexServ {

    String URL_BASE = "http://download.cdn.yandex.net/";
    String URL_PATH = "mobilization-2016/artists.json";

    int CONNECT_TIMEOUT = 15;
    int WRITE_TIMEOUT = 60;
    int TIMEOUT = 60;

    @GET(URL_PATH)
    Call<List<JSONToArtistObject>> getArtistsData();
}
