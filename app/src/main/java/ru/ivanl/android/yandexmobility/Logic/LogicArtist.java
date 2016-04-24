package ru.ivanl.android.yandexmobility.Logic;


import android.content.Context;
import android.database.Cursor;

import ru.ivanl.android.yandexmobility.DataBase.ArtistGenreHelper;
import ru.ivanl.android.yandexmobility.DataBase.ArtistHelper;

public class LogicArtist {

    private static final String DELIMITER_STRING = ", ";
    private static final int THUMB_WIDTH = 300;
    private final Context context;

    private ArtistHelper artistHelper;
    private ArtistGenreHelper artistGenreHelper;

    public LogicArtist(Context context, ArtistHelper artistHelper, ArtistGenreHelper artistGenreHelper) {
        this.context = context;
        this.artistHelper = artistHelper;
        this.artistGenreHelper = artistGenreHelper;
    }

    //Создаём строку жанров
    public String buildGenreString(Cursor cursor) {
        StringBuilder builder = new StringBuilder();

        Long artistId = artistHelper.getId(cursor);
    }
}
