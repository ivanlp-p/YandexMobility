package ru.ivanl.android.yandexmobility.DependencyInjection;


import android.content.Context;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

import ru.ivanl.android.yandexmobility.DataBase.ArtistGenreHelper;
import ru.ivanl.android.yandexmobility.DataBase.ArtistHelper;
import ru.ivanl.android.yandexmobility.DataBase.DBHelper;
import ru.ivanl.android.yandexmobility.DataBase.DBOpenHelper;
import ru.ivanl.android.yandexmobility.DataBase.GenreHelper;

@Module
public class DBModule {

    @Singleton
    @Provides
    DBOpenHelper provideDBOpenHelper (Context context){
        return new DBOpenHelper(context);
    }

    @Singleton
    @Provides
    DBHelper provideDBHelper(Context context) {
        return new DBHelper(context);
    }

    @Singleton
    @Provides
    ArtistHelper provideArtistHelper(Context context) {
        return new ArtistHelper(context);
    }

    @Singleton
    @Provides
    ArtistGenreHelper provideArtistGenreHelper(Context context) {
        return new ArtistGenreHelper(context);
    }

    @Singleton
    @Provides
    GenreHelper provideGenreHelper(Context context) {
        return new GenreHelper(context);
    }
}
