package ru.ivanl.android.yandexmobility.DependencyInjection;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.ivanl.android.yandexmobility.DataBase.ArtistGenreHelper;
import ru.ivanl.android.yandexmobility.DataBase.ArtistHelper;
import ru.ivanl.android.yandexmobility.Logic.LogicArtist;


@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    Context provideContext(){
        return context;
    }

    @Singleton
    @Provides
    LogicArtist provideLogicArtist(Context context, ArtistHelper artistHelper, ArtistGenreHelper artistGenreHelper) {
        return new LogicArtist(context,artistHelper,artistGenreHelper);
    }
}
